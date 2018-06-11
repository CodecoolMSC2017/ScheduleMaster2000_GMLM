package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.SlotDao;
import com.codecool.web.model.Slot;
import com.codecool.web.service.SlotService;
import com.codecool.web.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleSlotService implements SlotService {

    private final SlotDao slotDao;
    private static final Logger logger = LoggerFactory.getLogger(SimpleScheduleService.class);

    public SimpleSlotService(SlotDao slotDao) {
        this.slotDao = slotDao;
    }

    public List<Slot> getSlotsByTaskId(int task_id) throws SQLException, ServiceException {
        List<Slot> result = slotDao.getSlotsByTaskId(task_id);
        return checkListLength(result, "This task is not assigned to day!");

    }

    public List<Slot> getSlotsByDayId(int day_id) throws SQLException, ServiceException {
        List<Slot> result = slotDao.getSlotsByDayId(day_id);
        return checkListLength(result, "There are no tasks assigned to this day!");
    }


    public void assignTaskToDay(int endHour, int day_id, int task_id) throws SQLException {
        slotDao.assignTaskToDay(endHour, day_id, task_id);
    }


    public void deleteSlot(int day_id, int task_id) throws SQLException {
        slotDao.deleteSlot(day_id, task_id);
    }

    private List<Slot> checkListLength(List<Slot> result, String message) throws ServiceException {
        if (result.size() < 1) {
            throw new ServiceException(message);
        }
        return result;
    }

    private boolean chekIfSlotIsExists(int endHour, int day_id) throws SQLException {
        return slotDao.checkIfSlotIsExists(endHour, day_id);
    }

}

