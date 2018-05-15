package com.codecool.web.dao;

import com.codecool.web.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao {

    Task addNewTask(String name, String content, int user_id) throws SQLException;
    void updateTask(String name, String content, int task_id) throws SQLException;
    Task findById(int id) throws SQLException;
    List<Task> getAllTasks() throws SQLException;
    List<Task> getUsersTask(int user_id) throws SQLException;
    void deleteTask(int id) throws SQLException;
    List<Task> getTaskByDayId (int day_id) throws SQLException;
}
