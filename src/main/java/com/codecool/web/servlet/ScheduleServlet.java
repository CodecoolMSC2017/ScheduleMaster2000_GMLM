package com.codecool.web.servlet;


import com.codecool.web.dao.DayDao;
import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.database.DatabaseDayDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.model.Day;
import com.codecool.web.model.User;
import com.codecool.web.service.DayService;

import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleDayService;
import com.codecool.web.service.simple.SimpleScheduleService;
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

@WebServlet("/protected/schedule")
public class ScheduleServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServlet.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            DayDao dayDao = new DatabaseDayDao(connection);
            DayService dayService = new SimpleDayService(dayDao);

            String scheduleId = req.getParameter("id");

            List<Day> days = dayService.findDaysByScheduleId(Integer.parseInt(scheduleId));
            sendMessage(resp, 200, days);
        } catch (ServiceException e) {
            sendMessage(resp, 401, e.getMessage());
            logger.debug("Exception has been caught: " + e.getMessage());
        } catch (SQLException e) {
            handleSqlError(resp, e);
            logger.debug("Exception has been caught: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            User user = (User) req.getSession().getAttribute("user");
            String name = req.getParameter("name");

            scheduleService.addNewSchedule(user.getId(), name);
            sendMessage(resp, 200, "New schedule is added!");
            logger.debug("New schedule is added by " + user.getEmail() + ".");
        } catch (SQLException e) {
            handleSqlError(resp, e);
            logger.debug("Exception has been caught: " + e.getMessage());
        } catch (ServiceException e) {
            sendMessage(resp, 401, e.getMessage());
            logger.debug("Exception has been caught: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            User user = (User) req.getSession().getAttribute("user");
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            String scheduleId = req.getParameter("id");

            scheduleService.removeSchedule(Integer.parseInt(scheduleId));
            sendMessage(resp, 200, "The given schedule is deleted!");
            logger.debug("The given schedule is removed by " + user.getEmail() + ".");
        } catch (SQLException e) {
            handleSqlError(resp, e);
            logger.debug("Exception has been caught: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            User user = (User) req.getSession().getAttribute("user");
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            String name = req.getParameter("name");
            String scheduleId = req.getParameter("id");

            scheduleService.updateSchedule(name, Integer.parseInt(scheduleId));
            sendMessage(resp, 200, "The given schedule is modified!");
            logger.debug("The given schedule is modified by " + user.getEmail() + ".");
        } catch (SQLException e) {
            handleSqlError(resp, e);
            logger.debug("Exception has been caught: " + e.getMessage());
        } catch (ServiceException e) {
            sendMessage(resp, 401, e.getMessage());
            logger.debug("Exception has been caught: " + e.getMessage());
        }
    }
}
