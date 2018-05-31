package com.codecool.web.service.simple;

import com.codecool.web.dao.DayDao;
import com.codecool.web.model.Day;
import com.codecool.web.service.DayService;
import com.codecool.web.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class SimpleDayService implements DayService {
    private final DayDao dayDao;

    private static final Logger logger = LoggerFactory.getLogger(SimpleDayService.class);


    public SimpleDayService(DayDao dayDao) {
        this.dayDao = dayDao;
    }

    @Override
    public Day addNewDay(int schedule_id, String title) throws ServiceException {
        try {

            logger.debug(String.format("Added day %s to schedule %d", title, schedule_id));

            return dayDao.addNewDay(schedule_id, title);
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught: " + ex);
            throw new ServiceException(ex.getMessage());
        } catch (SQLException sql) {
            logger.debug("Exception has been caught:"+sql);
            throw new ServiceException("You can't create more than 7 days!");
        }
    }

    @Override
    public void updateDayName(String title, int id, int scheduleId) throws SQLException, ServiceException {
        try {

            dayDao.updateDayName(title, id, scheduleId);

            logger.debug(String.format("Updated day %d title to %s", id, title));
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught: " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    //Laci thinks this method might not be needed at all, so somebody should delete it if it turns out to be true
    @Override
    public void assignDayToSchedule(int schedule_id, int day_id) throws SQLException, ServiceException {
        try {
            dayDao.assignDayToSchedule(schedule_id, day_id);
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught: " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void deleteDay(int id) throws SQLException, ServiceException {
        try {
            dayDao.deleteDay(id);
            logger.debug(String.format("Day %d has been deleted", id));
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught: " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<Day> getAllDays() throws SQLException, ServiceException {
        try {
            if (dayDao.getAllDays().isEmpty()) {
                throw new ServiceException("There are no days made to this task yet");
            } else {
                logger.debug("All days have been accessed");
                return dayDao.getAllDays();
            }
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught: " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Day findById(int id) throws SQLException, ServiceException {
        try {
            if (dayDao.findById(id) == null) {
                throw new ServiceException("Could not find any day by this Id.");
            } else {
                logger.debug(String.format("Day %d has been accessed", id));
                return dayDao.findById(id);
            }
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught: " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<Day> findDaysByScheduleId(int schedule_id) throws SQLException, ServiceException {
        try {
            if (dayDao.findDaysByScheduleId(schedule_id).isEmpty()) {
                throw new ServiceException("There are no days assigned to this schedule yet");
            } else {
                logger.debug(String.format("Schedule %d has been accessed", schedule_id));
                return dayDao.findDaysByScheduleId(schedule_id);
            }
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught: " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
