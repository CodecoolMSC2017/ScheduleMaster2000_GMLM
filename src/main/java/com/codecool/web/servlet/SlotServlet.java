package com.codecool.web.servlet;

import com.codecool.web.dao.DayDao;
import com.codecool.web.dao.SlotDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseDayDao;
import com.codecool.web.dao.database.DatabaseSlotDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.dto.TaskSlotsDto;

import com.codecool.web.model.Task;
import com.codecool.web.service.DayService;
import com.codecool.web.service.SlotService;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleDayService;
import com.codecool.web.service.simple.SimpleSlotService;
import com.codecool.web.service.simple.SimpleTaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/slot")
public class SlotServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try(Connection connection = getConnection(req.getServletContext())) {
            SlotDao slotDao = new DatabaseSlotDao(connection);
            SlotService slotService = new SimpleSlotService(slotDao);

            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);

            DayDao dayDao = new DatabaseDayDao(connection);
            DayService dayService = new SimpleDayService(dayDao);

            int dayId = Integer.parseInt(req.getParameter("dayId"));
            // TODO implement getFreeHours method in SlotService and SlotDao
            List<Integer> endHours = slotService.getFreeHours(dayId);
            List<Integer> startHours = slotService.getFreeStartHours(dayId);
            List<Task> unassignedTask = taskService.getUnassignedTasks();


            TaskSlotsDto taskSlotDto = new TaskSlotsDto(unassignedTask,startHours,endHours);

            sendMessage(resp, 200, taskSlotDto);
        } catch (SQLException sq) {
            handleSqlError(resp, sq);
        } catch (ServiceException e) {
            sendMessage(resp,401,e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = getConnection(req.getServletContext())) {
            SlotDao slotDao = new DatabaseSlotDao(connection);
            SlotService slotService = new SimpleSlotService(slotDao);

            int dayId = Integer.parseInt(req.getParameter("dayId"));
            int taskId = Integer.parseInt(req.getParameter("taskId"));
            int startHour = Integer.parseInt(req.getParameter("startHour"));
            int endHour = Integer.parseInt(req.getParameter("endHour"));

            slotService.assignTaskToDay(endHour,dayId,taskId);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = getConnection(req.getServletContext())) {
            SlotDao slotDao = new DatabaseSlotDao(connection);
            SlotService slotService = new SimpleSlotService(slotDao);

            int dayId = Integer.parseInt(req.getParameter("dayId"));
            int taskId = Integer.parseInt(req.getParameter("taskId"));

            slotService.deleteSlot(dayId, taskId);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
