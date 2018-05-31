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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/days")
public class DaysServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(DaysServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            User user = (User) req.getSession().getAttribute("user");
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);

            String dayId = req.getParameter("id");

            List<Task> tasks = taskService.getTaskByDayId(Integer.parseInt(dayId));

            sendMessage(resp, 200, tasks);
            logger.debug("The " + user.getEmail() + " user is get all tasks from his/her given day.");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.debug("Exception has been caught: " + ex.getMessage());
        } catch (ServiceException e) {
            sendMessage(resp, 401, e.getMessage());
            logger.debug("Exception has been caught: " + e.getMessage());
        }
    }
}
