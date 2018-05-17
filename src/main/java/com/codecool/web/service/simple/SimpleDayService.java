package com.codecool.web.service.simple;

import com.codecool.web.dao.DayDao;
import com.codecool.web.model.Day;
import com.codecool.web.service.DayService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class SimpleDayService implements DayService {
    private final DayDao dayDao;


    public SimpleDayService(DayDao dayDao) {
        this.dayDao = dayDao;
    }

    @Override
    public Day addNewDay(int schedule_id, String title) throws SQLException, ServiceException {
        try {
            return dayDao.addNewDay(schedule_id, title);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void updateDayName(String title, int id) throws SQLException, ServiceException {
        try {
            dayDao.updateDayName(title, id);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    //Laci thinks this method might not be needed at all, so somebody should delete it if it turns out to be true
    @Override
    public void assignDayToSchedule(int schedule_id, int day_id) throws SQLException, ServiceException {
        try {
            dayDao.assignDayToSchedule(schedule_id, day_id);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void deleteDay(int id) throws SQLException, ServiceException {
        try {
            dayDao.deleteDay(id);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<Day> getAllDays() throws SQLException, ServiceException {
        try {
            if (dayDao.getAllDays().isEmpty()) {
                throw new ServiceException("There are no days made to this task yet");
            } else {
                return dayDao.getAllDays();
            }
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Day findById(int id) throws SQLException, ServiceException {
        try {
            if (dayDao.findById(id) == null) {
                throw new ServiceException("Could not find any day by this Id.");
            } else {
                return dayDao.findById(id);
            }
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<Day> findDaysByScheduleId(int schedule_id) throws SQLException, ServiceException {
        try {
            if (dayDao.findDaysByScheduleId(schedule_id).isEmpty()) {
                throw new ServiceException("There are no days assigned to this schedule yet");
            } else {
                return dayDao.findDaysByScheduleId(schedule_id);
            }
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}
