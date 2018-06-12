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
import java.util.Iterator;
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


    public void assignTaskToDay(int startHour, int endHour, int day_id, int task_id) throws SQLException, ServiceException {
        if (startHour == endHour) {
            throw new ServiceException("Start hour and end hour can not be the same!");
        }
        try {
            checkIfSlotsAreExists(startHour, endHour,day_id);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }

        for (int i = startHour + 1; i < endHour + 1; i++) {
            slotDao.assignTaskToDay(i, day_id, task_id);
        }

    }

    public void deleteSlot(int day_id, int task_id) throws SQLException {
        slotDao.deleteSlot(day_id, task_id);
    }

    public List<Integer> getFreeHours(int dayId) throws SQLException {
        List<Integer> allHours = generateBasicHourList();
        List<Integer> occupiedHours = slotDao.getOccupiedHours(dayId);
        for (int busyHour : occupiedHours) {
            Iterator iter = allHours.iterator();
            while (iter.hasNext()) {
                int hour = (int) iter.next();
                if (hour == busyHour) {
                    iter.remove();
                }
            }
        }
        return allHours;
    }

    public List<Integer> getFreeStartHours(int dayId) throws SQLException {
        List<Integer> freeHours = getFreeHours(dayId);
        List<Integer> result = new ArrayList();
        for (int hour : freeHours) {
            result.add(hour - 1);
        }
        return result;
    }


    private List<Integer> generateBasicHourList() {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            result.add(i);
        }
        return result;
    }

    private List<Slot> checkListLength(List<Slot> result, String message) throws ServiceException {
        if (result.size() < 1) {
            throw new ServiceException(message);
        }
        return result;
    }

    private boolean checkIfSlotsAreExists(int startHour, int endHour, int day_id) throws SQLException {

        for (int i = startHour + 1; i < endHour + 1; i++) {
            if (slotDao.checkIfSlotIsExists(i, day_id)) {
                throw new IllegalArgumentException("You already have task in this time!");
            }
        }

        return false;
    }

}

