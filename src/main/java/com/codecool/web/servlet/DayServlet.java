package com.codecool.web.servlet;


import com.codecool.web.dao.DayDao;
import com.codecool.web.dao.database.DatabaseDayDao;
import com.codecool.web.model.Day;
import com.codecool.web.model.User;
import com.codecool.web.service.DayService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleDayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/day")
public class DayServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(DayServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            DayDao dayDao = new DatabaseDayDao(connection);
            DayService dayService = new SimpleDayService(dayDao);

            int dayId = Integer.parseInt(req.getParameter("id"));

            Day day = dayService.findById(dayId);

            sendMessage(resp, 200, day);
        } catch (SQLException e) {
            handleSqlError(resp, e);
            logger.debug("Exception has been caught: " + e.getMessage());
        } catch (ServiceException e) {
            sendMessage(resp, 401, e.getMessage());
            logger.debug("Exception has been caught: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            User user = (User) req.getSession().getAttribute("user");
            DayDao dayDao = new DatabaseDayDao(connection);
            DayService dayService = new SimpleDayService(dayDao);

            String title = req.getParameter("title");
            int scheduleId = Integer.parseInt(req.getParameter("id"));

            dayService.addNewDay(scheduleId, title);

            sendMessage(resp, 200, "Day has been added!");
            logger.debug("The " + user.getEmail() + " user is added a new day with " + title + " title!");
        } catch (SQLException e) {
            handleSqlError(resp, e);
            logger.debug("Exception has been caught: " + e.getMessage());
        } catch (ServiceException e) {
            sendMessage(resp, 401, e.getMessage());
            logger.debug("Exception has been caught: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            User user = (User) req.getSession().getAttribute("user");
            DayDao dayDao = new DatabaseDayDao(connection);
            DayService dayService = new SimpleDayService(dayDao);

            int dayId = Integer.parseInt(req.getParameter("id"));
            String title = req.getParameter("title");
            int scheduleId = Integer.parseInt(req.getParameter("scheduleId"));

            dayService.updateDayName(title, dayId, scheduleId);

            sendMessage(resp, 200, "Day's title has been modified!");
            logger.debug("The " + user.getEmail() + " user is modified a day's title to " + title + ".");
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
            DayDao dayDao = new DatabaseDayDao(connection);
            DayService dayService = new SimpleDayService(dayDao);

            int dayId = Integer.parseInt(req.getParameter("id"));

            dayService.deleteDay(dayId);

            sendMessage(resp, 200, "Day has been deleted!");
            logger.debug("The " + user.getEmail() + " user is deleted a day!");
        } catch (SQLException e) {
            handleSqlError(resp, e);
            logger.debug("Exception has been caught: " + e.getMessage());
        } catch (ServiceException e) {
            sendMessage(resp, 401, e.getMessage());
            logger.debug("Exception has been caught: " + e.getMessage());
        }
    }
}

