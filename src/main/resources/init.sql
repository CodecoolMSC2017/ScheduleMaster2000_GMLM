DROP TABLE IF EXISTS slots;
DROP TABLE IF EXISTS days;
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	name TEXT UNIQUE NOT NULL,
	email TEXT UNIQUE NOT NULL,
	password TEXT NOT NULL,
	permission BOOLEAN DEFAULT FALSE
);

CREATE TABLE schedules (
	id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL,
	name TEXT,
	FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE days (
	id SERIAL PRIMARY KEY,
	schedule_id INTEGER NOT NULL,
	name TEXT,
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
	day_id INTEGER,
	task_id INTEGER,
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
	
insert into days (schedule_id,name) VALUES
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
