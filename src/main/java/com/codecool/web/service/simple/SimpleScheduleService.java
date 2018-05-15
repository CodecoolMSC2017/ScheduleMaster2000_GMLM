package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class SimpleScheduleService implements ScheduleService {

    private final ScheduleDao scheduleDao;

    public SimpleScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public List<Schedule> getAllSchedules() throws SQLException, ServiceException {
        if(scheduleDao.getAllSchedules().isEmpty()) {
            throw new ServiceException("There are no schedules made!");
        } else {
            return scheduleDao.getAllSchedules();
        }
    }

    public List<Schedule> getUsersSchedules(int userId) throws SQLException, ServiceException {
        if(scheduleDao.getUsersSchedules(userId).isEmpty()) {
            throw new ServiceException("You don't have any schedules!");
        } else {
            return scheduleDao.getUsersSchedules(userId);
        }
    }

    public Schedule getScheduleById(int id) throws SQLException, ServiceException {
        if(scheduleDao.getScheduleById(id) == null) {
            throw new ServiceException("Not existing schedule'");
        } else {
            return scheduleDao.getScheduleById(id);
        }

    }

    public void addNewSchedule(int userId, String name) throws SQLException{
        scheduleDao.addNewSchedule(userId,name);
    }

    public void removeSchedule(int id) throws SQLException {
        scheduleDao.removeSchedule(id);
    }
}
