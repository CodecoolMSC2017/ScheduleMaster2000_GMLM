package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/protected/schedules")
public class SchedulesServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(SchedulesServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        ServletContext scx = req.getServletContext();

        try (Connection connection = getConnection(scx)) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            User actualUser = (User) req.getSession().getAttribute("user");
            List<Schedule> schedules = null;

            if (actualUser.isAdmin()) {
                schedules = scheduleService.getAllSchedules();
            } else {
                schedules = scheduleService.getUsersSchedules(actualUser.getId());
            }

            sendMessage(resp, HttpServletResponse.SC_OK, schedules);
            logger.debug("The schedules are listed by " + user.getEmail() + ".");
        } catch (SQLException e) {
            handleSqlError(resp, e);
            logger.debug("Exception has been caught: " + e.getMessage());
        } catch (ServiceException e) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            logger.debug("Exception has been caught: " + e.getMessage());
        }


    }
}
