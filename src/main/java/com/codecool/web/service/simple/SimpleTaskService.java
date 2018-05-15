package com.codecool.web.service.simple;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class SimpleTaskService implements TaskService {
    private final TaskDao taskDao;

    public SimpleTaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public Task addNewTask(String name, String content, int user_id) throws SQLException, ServiceException {
        try {
            return taskDao.addNewTask(name, content, user_id);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void updateTask(String name, String content, int task_id) throws SQLException, ServiceException {
        try {
            taskDao.updateTask(name, content, task_id);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Task findById(int id) throws SQLException, ServiceException {
        try {
            return taskDao.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<Task> getAllTasks() throws SQLException, ServiceException {
        try {
            return taskDao.getAllTasks();
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<Task> getUsersTask(int user_id) throws SQLException, ServiceException {
        try {
            return taskDao.getUsersTask(user_id);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void deleteTask(int id) throws SQLException, ServiceException {
        try {
            taskDao.deleteTask(id);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}
