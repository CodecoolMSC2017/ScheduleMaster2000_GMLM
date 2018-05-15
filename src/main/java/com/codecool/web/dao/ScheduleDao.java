package com.codecool.web.dao;

import com.codecool.web.model.Schedule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface ScheduleDao {

    List<Schedule> getAllSchedules() throws SQLException;

    List<Schedule> getUsersSchedules(int userId) throws SQLException;

    Schedule getScheduleById(int id) throws SQLException;

    void addNewSchedule(int userId, String name) throws SQLException;

    void removeSchedule(int id) throws SQLException;
}
