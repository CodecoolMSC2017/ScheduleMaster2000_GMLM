package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.model.User;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleScheduleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet("/protected/publish")
public class SchedulePublishServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServlet.class);

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            User user = (User) req.getSession().getAttribute("user");

            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            System.out.println("schedule_id get: " + req.getParameter("id"));
            String scheduleId = req.getParameter("id");

            scheduleService.updateSchedulePublicity(Integer.parseInt(scheduleId));

            sendMessage(resp, 200, "Success!");
            logger.debug("Schedule " + scheduleId + " set by " + user.getId());
        } catch (SQLException e) {
            handleSqlError(resp, e);
        } catch (ServiceException e) {
            sendMessage(resp, 401, e.getMessage());
        }
    }
}
