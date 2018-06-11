package com.codecool.web.dao;

import com.codecool.web.model.Slot;

import java.sql.SQLException;
import java.util.List;

public interface SlotDao {

    List<Slot> getSlotsByTaskId(int task_id) throws SQLException;

    List<Slot> getSlotsByDayId(int day_id) throws SQLException;

    void assignTaskToDay(int endHour, int day_id, int task_id) throws SQLException;

    void deleteSlot(int day_id, int task_id) throws SQLException;

    boolean checkIfSlotIsExists(int endHour, int day_id) throws SQLException;
}
