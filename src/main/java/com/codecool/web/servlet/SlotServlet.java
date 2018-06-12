package com.codecool.web.servlet;

import com.codecool.web.dao.SlotDao;
import com.codecool.web.dao.TaskDao;

import com.codecool.web.dao.database.DatabaseSlotDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.dto.TaskSlotsDto;

import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.SlotService;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
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

            User user = (User)req.getSession().getAttribute("user");

            int dayId = Integer.parseInt(req.getParameter("dayId"));

            List<Integer> endHours = slotService.getFreeHours(dayId);
            List<Integer> startHours = slotService.getFreeStartHours(dayId);
            List<Task> unassignedTask = taskService.getUnassignedTasks(user.getId());


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

            String dayId = req.getParameter("dayId");
            String taskId = req.getParameter("taskId");
            String startHour = req.getParameter("startHour");
            String endHour = req.getParameter("endHour");

            slotService.assignTaskToDay(startHour, endHour, dayId, taskId);
            sendMessage(resp, 200, "Success!");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        } catch (ServiceException e) {
            sendMessage(resp,401,e.getMessage());
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
            sendMessage(resp, 200, "Success!");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
