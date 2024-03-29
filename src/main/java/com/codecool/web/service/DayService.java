package com.codecool.web.service;

import com.codecool.web.model.Day;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface DayService {

    Day addNewDay(int schedule_id, String title) throws ServiceException ;

    void updateDayName(String title, int id, int scheduleId) throws SQLException, ServiceException;

    void deleteDay(int id) throws SQLException, ServiceException;

    List<Day> getAllDays() throws SQLException, ServiceException;

    Day findById(int id) throws SQLException, ServiceException;

    List<Day> findDaysByScheduleId(int schedule_id) throws SQLException, ServiceException;
}
