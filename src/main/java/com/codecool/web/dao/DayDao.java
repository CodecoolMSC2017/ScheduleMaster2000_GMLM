package com.codecool.web.dao;

import com.codecool.web.model.Day;

import java.sql.SQLException;
import java.util.List;

public interface DayDao {

    Day addNewDay(int schedule_id, String title) throws SQLException;
    void updateDayName(String title, int id, int scheduleId) throws SQLException;
    void deleteDay(int id) throws SQLException;
    List<Day> getAllDays() throws SQLException;
    Day findById(int id) throws SQLException;
    List<Day> findDaysByScheduleId(int schedule_id) throws SQLException;

}
