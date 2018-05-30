package com.codecool.web.service.simple;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class SimpleTaskService implements TaskService {
    private final TaskDao taskDao;
    private static final Logger logger = LoggerFactory.getLogger(SimpleTaskService.class);


    public SimpleTaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public Task addNewTask(String name, String content, int user_id) throws SQLException, ServiceException {
        try {
            logger.debug(String.format("Task %s has been added", name));
            return taskDao.addNewTask(name, content, user_id);
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught:" + ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void updateTask(String name, String content, int task_id) throws SQLException, ServiceException {
        try {
            taskDao.updateTask(name, content, task_id);
            logger.debug(String.format("Task %d has been updated", task_id));
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught: " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Task findById(int id) throws SQLException, ServiceException {
        try {
            logger.debug(String.format("Task %d has been accessed", id));
            return taskDao.findById(id);
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<Task> getAllTasks() throws SQLException, ServiceException {
        try {
            if (taskDao.getAllTasks().isEmpty()) {
                throw new ServiceException("There are no tasks yet!");
            } else {
                logger.debug("All tasks have been accessed");
                return taskDao.getAllTasks();
            }
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<Task> getUsersTask(int user_id) throws SQLException, ServiceException {
        try {
            if (taskDao.getUsersTask(user_id).isEmpty()) {
                throw new ServiceException("There are no tasks yet!");
            } else {
                logger.debug(String.format("User %d tasks have been accessed", user_id));
                return taskDao.getUsersTask(user_id);
            }
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void deleteTask(int id) throws SQLException {
        taskDao.deleteTask(id);
        logger.debug(String.format("Task %d has been deleted", id));
    }

    @Override
    public List<Task> getTaskByDayId(int day_id) throws SQLException, ServiceException {
        try {
            if (taskDao.getTaskByDayId(day_id).isEmpty()) {
                throw new ServiceException("There aren't any task for this day yet!");
            } else {
                logger.debug("Tasks have been accessed for day + ");
                return taskDao.getTaskByDayId(day_id);
            }
        }catch (IllegalArgumentException ex) {
            logger.debug("Exception has been caught " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
