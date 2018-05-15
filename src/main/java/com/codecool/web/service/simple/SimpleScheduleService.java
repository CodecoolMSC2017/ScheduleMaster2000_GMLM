package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.ScheduleService;

import java.sql.SQLException;
import java.util.List;

public class SimpleScheduleService implements ScheduleService {

    private final ScheduleDao scheduleDao;

    public SimpleScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public List<Schedule> getUsersSchedules(int userId) throws SQLException {
        return scheduleDao.getUsersSchedules(userId);
    }

    public Schedule getScheduleById(int id) throws SQLException {
        return scheduleDao.getScheduleById(id);
    }

    public void addNewSchedule(int userId, String name) throws SQLException{
        scheduleDao.addNewSchedule(userId,name);
    }

    public void removeSchedule(int id) throws SQLException {
        scheduleDao.removeSchedule(id);
    }
}
