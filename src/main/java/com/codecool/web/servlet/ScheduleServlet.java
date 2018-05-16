package com.codecool.web.servlet;


import com.codecool.web.dao.DayDao;
import com.codecool.web.dao.database.DatabaseDayDao;
import com.codecool.web.model.Day;
import com.codecool.web.service.DayService;

import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleDayService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/schedule")
public class ScheduleServlet extends AbstractServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            DayDao dayDao = new DatabaseDayDao(connection);
            DayService dayService = new SimpleDayService(dayDao);

            String scheduleId = req.getParameter("id");

            List<Day> days = dayService.findDaysByScheduleId(Integer.parseInt(scheduleId));
            sendMessage(resp, 200, days);
        } catch (ServiceException e) {
            sendMessage(resp, 401, e.getMessage());
        } catch (SQLException e) {
            handleSqlError(resp, e);
        }
    }
}
