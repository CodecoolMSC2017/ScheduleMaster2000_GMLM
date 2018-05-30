package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class SimpleScheduleService implements ScheduleService {

    private final ScheduleDao scheduleDao;
    private static final Logger logger = LoggerFactory.getLogger(SimpleScheduleService.class);

    public SimpleScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public List<Schedule> getAllSchedules() throws SQLException, ServiceException {
        if(scheduleDao.getAllSchedules().isEmpty()) {
            throw new ServiceException("There are no schedules made!");
        } else {
            logger.debug("All schedules have been accessed");
            return scheduleDao.getAllSchedules();
        }
    }

    public List<Schedule> getUsersSchedules(int userId) throws SQLException, ServiceException {
        if(scheduleDao.getUsersSchedules(userId).isEmpty()) {
            throw new ServiceException("You don't have any schedule!");
        } else {
            logger.debug(String.format("User %d schedules have been accessed", userId));
            return scheduleDao.getUsersSchedules(userId);
        }
    }

    public Schedule getScheduleById(int id) throws SQLException, ServiceException {
        if(scheduleDao.getScheduleById(id) == null) {
            throw new ServiceException("Not existing schedule!");
        } else {
            logger.debug(String.format("Schedule %d has been accessed", id));
            return scheduleDao.getScheduleById(id);
        }

    }

    public void addNewSchedule(int userId, String name) throws SQLException, ServiceException {
        try {
            scheduleDao.addNewSchedule(userId,name);
            logger.debug(String.format("Schedule %s has been added", name));
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught: " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public void removeSchedule(int id) throws SQLException {
        scheduleDao.removeSchedule(id);
        logger.debug(String.format("Schedule %d has been deleted", id));
    }

    @Override
    public void updateSchedule(String name, int id) throws SQLException, ServiceException {
        try {
            scheduleDao.updateSchedule(name, id);
            logger.debug(String.format("Schedule %d name has been updated to %s", id, name));
        } catch (IllegalArgumentException e) {
            logger.debug("Exception has been caught: " + e);
            throw new ServiceException(e.getMessage());
        }
    }
}
