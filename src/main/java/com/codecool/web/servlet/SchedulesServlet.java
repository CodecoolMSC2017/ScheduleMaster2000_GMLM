package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.simple.SimpleScheduleService;

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

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletContext scx = req.getServletContext();

        try(Connection connection = getConnection(scx)) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            User actualUser = (User) req.getSession().getAttribute("user");

            List<Schedule> schedules = scheduleService.getUsersSchedules(actualUser.getId());

            sendMessage(resp,200,schedules);
        } catch (SQLException e) {
            handleSqlError(resp, e);
        }


    }
}
