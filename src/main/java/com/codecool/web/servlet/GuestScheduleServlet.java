package com.codecool.web.servlet;


import com.codecool.web.dao.DayDao;
import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.database.DatabaseDayDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dto.ScheduleDayDto;
import com.codecool.web.model.Day;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.DayService;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleDayService;
import com.codecool.web.service.simple.SimpleScheduleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/guestschedule")
public class GuestScheduleServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            DayDao dayDao = new DatabaseDayDao(connection);
            DayService dayService = new SimpleDayService(dayDao);

            int scheduleId = Integer.parseInt(req.getParameter("scheduleId"));

            Schedule schedule = scheduleService.getPublishedSchedule(scheduleId);
            List<Day> daysOfSchedule= dayService.findDaysByScheduleId(scheduleId);
            //Made a DTO to return the schedule object and the corresponding days too
            ScheduleDayDto scheduleDayDto = new ScheduleDayDto(schedule,daysOfSchedule);
            sendMessage(resp, 200, scheduleDayDto);

        } catch (SQLException e) {
            handleSqlError(resp, e);
        } catch (ServiceException e) {
            sendMessage(resp, 401,e.getMessage());
        }
    }
}
