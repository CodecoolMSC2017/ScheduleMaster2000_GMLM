package com.codecool.web.service;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleService {

    List<Schedule> getAllSchedules() throws SQLException, ServiceException;

    List<Schedule> getUsersSchedules(int userId) throws SQLException, ServiceException;

    Schedule getScheduleById(int id) throws SQLException, ServiceException;

    void addNewSchedule(int userId, String name) throws SQLException, ServiceException;

    void removeSchedule(int id) throws SQLException;

    void updateSchedule(String name, int id) throws SQLException, ServiceException;

    void updateSchedulePublicity(int id) throws SQLException, ServiceException;

    Schedule getPublishedSchedule(int id) throws SQLException, ServiceException;
}
