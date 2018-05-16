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
            return dayDao.getAllDays();
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Day findById(int id) throws SQLException, ServiceException {
        try {
            return dayDao.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}
