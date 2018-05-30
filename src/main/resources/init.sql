DROP TABLE IF EXISTS slots;
DROP TABLE IF EXISTS days;
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users;
DROP FUNCTION IF EXISTS enforce_days_count();
DROP FUNCTION IF EXISTS enforce_slots_count();
DROP FUNCTION IF EXISTS enforce_slot_hour_check();

CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	name TEXT NOT NULL,
	email TEXT UNIQUE NOT NULL,
	password TEXT NOT NULL,
	permission BOOLEAN DEFAULT FALSE
);

CREATE TABLE schedules (
	id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL,
	name TEXT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE days (
	id SERIAL PRIMARY KEY,
	schedule_id INTEGER references schedules ON DELETE CASCADE,
	title TEXT UNIQUE,
	FOREIGN KEY (schedule_id) REFERENCES schedules(id)
);

CREATE TABLE tasks (
	id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL,
	name TEXT UNIQUE,
	content TEXT,
	FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE table slots (
	hour INTEGER not null,
	day_id INTEGER REFERENCES days ON DELETE CASCADE,
	task_id INTEGER REFERENCES tasks ON DELETE SET NULL,
	FOREIGN KEY (day_id) REFERENCES days(id),
	FOREIGN KEY (task_id) REFERENCES tasks(id),
	check(hour >= 1 and hour <= 24)
);


INSERT INTO users (name, email, password, permission) VALUES
	('admin', 'user1@user1', 'admin','true'), -- 1
	('Pista Lakatos', 'pLakatos@lungodrom', 'plakatos', 'false'), -- 2
	('Ketrin Kanalas', 'kkanalas@lungodrom', 'plakatos', 'false'), -- 3
	('Szantiago Orgovan', 'sorgovan@lungodrom', 'student', 'false'); -- 4

iNSERT INTO schedules (user_id,name) VALUES
	(2,'Bugázás'), --1
	(2,'Uzsora'),  --2
	(3,'Munka'),  --3
	(4,'Segély nap'); --4

insert into days (schedule_id,title) VALUES
	(3,'szerda'), --1
	(3,'csütörtök'), --2
	(4,'Segély nap'); --3

insert into tasks (name,content,user_id) VALUES
	('cocózás', 'Pistával öccáért', 3),  --1
	('nagy aranyeső', 'Szantiagoval  20ért', 3), --2
	('Full house', 'Pistával 25 ért', 3),  --3
	('segélyéy begyűjtés', 'a hivatalba ', 4),  --4
	('Uzsora behajtás', 'a Búzán Janitól', 4),  --5
	('Italozás', 'A hatcsöcsűben', 4);  --6

insert into slots (hour,day_id,task_id) VALUES
	(8,1,1), --1
	(9,1,2),  --2
	(10,1,2),  --3
	(11,2,3),  --4
	(12,2,3),  --5
	(13,2,3),  --6
	(8,3,4);  --7

-- This funtion starts when a day created.Counts the days in a schedule and

CREATE OR REPLACE FUNCTION enforce_days_count() RETURNS trigger AS '
DECLARE
    days_count INTEGER := 0;
    must_check BOOLEAN := false;
BEGIN
    IF TG_OP = ''INSERT'' THEN
        must_check := true;
    END IF;

    IF TG_OP = ''UPDATE'' THEN
        IF (NEW.schedule_id != OLD.schedule_id) THEN
            must_check := true;
        END IF;
    END IF;

    IF must_check THEN
        -- prevent concurrent inserts from multiple transactions
        LOCK TABLE days IN EXCLUSIVE MODE;

        SELECT INTO days_count COUNT(*)
        FROM days
        WHERE schedule_id = NEW.schedule_id;

        IF days_count >= 7 THEN
            RAISE EXCEPTION ''Cannot create more than 7 days for a schedule.'';
        END IF;
    END IF;

    RETURN NEW;

END;

' LANGUAGE plpgsql;


CREATE TRIGGER enforce_days_count
    BEFORE INSERT OR UPDATE ON days
    FOR EACH ROW EXECUTE PROCEDURE enforce_days_count();


CREATE OR REPLACE FUNCTION enforce_slots_count() RETURNS trigger AS '
DECLARE
    slots_count INTEGER := 0;
    must_check BOOLEAN := false;
BEGIN
    IF TG_OP = ''INSERT'' THEN
        must_check := true;
    END IF;

    IF TG_OP = ''UPDATE'' THEN
        IF (NEW.day_id != OLD.day_id) THEN
            must_check := true;
        END IF;
    END IF;

    IF must_check THEN
        LOCK TABLE slots IN EXCLUSIVE MODE;

        SELECT INTO slots_count COUNT(*)
        FROM slots
        WHERE day_id = NEW.day_id;

        IF slots_count >= 24 THEN
            RAISE EXCEPTION ''Your day is fullfilled like a bus at noon!'';
        END IF;
    END IF;

    RETURN NEW;

END;

' LANGUAGE plpgsql;

CREATE TRIGGER enforce_slots_count
    BEFORE INSERT OR UPDATE ON slots
    FOR EACH ROW EXECUTE PROCEDURE enforce_slots_count();


CREATE OR REPLACE FUNCTION enforce_slot_hour_check()
RETURNS trigger AS '
DECLARE
    must_check BOOLEAN := false;
	result_hour INTEGER := 0;
BEGIN
    IF TG_OP = ''INSERT'' THEN
        must_check := true;
    END IF;

    IF TG_OP = ''UPDATE'' THEN
        IF (NEW.day_id != OLD.day_id) THEN
            must_check := true;
        END IF;
    END IF;

    IF must_check THEN
        LOCK TABLE slots IN EXCLUSIVE MODE;

        Select hour into result_hour  FROM slots
        	WHERE day_id = NEW.day_id;

        IF result_hour = NEW.hour THEN
            RAISE EXCEPTION ''This time you already have a task arranged!'';
        END IF;
    END IF;

    RETURN NEW;

END;

' LANGUAGE plpgsql;


CREATE TRIGGER enforce_slot_hour_check
    BEFORE INSERT OR UPDATE ON slots
    FOR EACH ROW EXECUTE PROCEDURE enforce_slot_hour_check();

