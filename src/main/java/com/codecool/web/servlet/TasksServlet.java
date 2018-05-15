package com.codecool.web.servlet;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleTaskService;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/tasks")
public class TasksServlet extends AbstractServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletContext scx = req.getServletContext();

        try (Connection connection = getConnection(scx)) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);

            User actualUser = (User) req.getSession().getAttribute("user");
            List<Task> tasks;

            if (actualUser.isAdmin()) {
                tasks = taskService.getAllTasks();
            } else {
                tasks = taskService.getUsersTask(actualUser.getId());
            }

            sendMessage(resp, HttpServletResponse.SC_OK, tasks);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        } catch (ServiceException e) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }
}
