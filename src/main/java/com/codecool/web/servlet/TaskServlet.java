package com.codecool.web.servlet;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/task")
public class TaskServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(TaskServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);

            String taskId = req.getParameter("id");

            Task task = taskService.findById(Integer.parseInt(taskId));
            sendMessage(resp, 200, task);
        } catch (ServiceException e) {
            sendMessage(resp, 401, e.getMessage());
        } catch (SQLException e) {
            handleSqlError(resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);

            User user = (User) req.getSession().getAttribute("user");
            String name = req.getParameter("title");
            String content = req.getParameter("content");

            taskService.addNewTask(name, content, user.getId());

            logger.debug(String.format("User %s has been added %s task", user.getEmail(), name));

            sendMessage(resp, 200, "New task is added.");
        } catch (SQLException e) {
            handleSqlError(resp, e);
            logger.debug("Exception has been caught: " + e);
        } catch (ServiceException e) {
            sendMessage(resp, 401, e.getMessage());
            logger.debug("Exception has been caught: " + e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);

            User user = (User) req.getSession().getAttribute("user");
            int taskId = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("title");
            String content = req.getParameter("content");
            int userId = user.getId();

            taskService.updateTask(name, content, taskId, userId);
            sendMessage(resp, 200, "Task is modified!");
        } catch (SQLException e) {
            handleSqlError(resp, e);
        } catch (ServiceException e) {
            sendMessage(resp, 401, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);

            String taskId = req.getParameter("id");

            taskService.deleteTask(Integer.parseInt(taskId));
            sendMessage(resp, 200, "The given task is deleted!");
        } catch (SQLException e) {
            handleSqlError(resp, e);
        }
    }


}
