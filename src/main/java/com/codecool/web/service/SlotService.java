package com.codecool.web.service;

import com.codecool.web.model.Slot;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface SlotService {

    List<Slot> getSlotsByTaskId(int task_id) throws SQLException, ServiceException;

    List<Slot> getSlotsByDayId(int day_id) throws SQLException, ServiceException;

    public void assignTaskToDay(int startHour, int endHour, int day_id, int task_id) throws SQLException, ServiceException;

    void deleteSlot(int day_id, int task_id) throws SQLException;

    List<Integer> getFreeHours(int dayId) throws SQLException;

    List<Integer> getFreeStartHours(int dayId) throws SQLException;
}
