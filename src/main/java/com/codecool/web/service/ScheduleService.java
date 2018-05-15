package com.codecool.web.service;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleService {

     List<Schedule> getUsersSchedules(int userId) throws SQLException;

     Schedule getScheduleById(int id) throws SQLException;

     void addNewSchedule(int userId, String name) throws SQLException;

     void removeSchedule(int id) throws SQLException;
}
