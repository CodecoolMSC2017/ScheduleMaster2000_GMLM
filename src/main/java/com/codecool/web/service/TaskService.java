package com.codecool.web.service;

import com.codecool.web.model.Task;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface TaskService {

    Task addNewTask(String name, String content, int user_id) throws SQLException, ServiceException;
    void updateTask(String name, String content, int task_id, int user_id) throws SQLException, ServiceException;
    Task findById(int id) throws SQLException, ServiceException;
    List<Task> getAllTasks() throws SQLException, ServiceException;
    List<Task> getUsersTask(int user_id) throws SQLException, ServiceException;
    void deleteTask(int id) throws SQLException;
    List<Task> getTaskByDayId (int day_id) throws SQLException, ServiceException;
}
