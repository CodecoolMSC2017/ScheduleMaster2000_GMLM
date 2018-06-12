package com.codecool.web.dao.database;

import com.codecool.web.dao.SlotDao;
import com.codecool.web.model.Slot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseSlotDao extends AbstractDao implements SlotDao {


    public DatabaseSlotDao(Connection connection) {
        super(connection);
    }

    public List<Slot> getSlotsByTaskId(int task_id) throws SQLException {
        List<Slot> slotsByTaskId = new ArrayList<>();
        String sql = "select * from slots\n" +
            "\twhere task_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, task_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    slotsByTaskId.add(fetchSlot(resultSet));
                }
                return slotsByTaskId;

            }
        }
    }

    public List<Slot> getSlotsByDayId(int day_id) throws SQLException {
        String sql = "select hour from slots\n" +
            "\twhere day_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Slot> slotsByDayId = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    slotsByDayId.add(fetchSlot(resultSet));
                }
                return slotsByDayId;
            }
        }
    }


    public void assignTaskToDay(int endHour, int day_id, int task_id) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO slots(hour, day_id,task_id) VALUES\n" +
            "\t(?,?,?);\n";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, endHour);
            statement.setInt(2, day_id);
            statement.setInt(3, task_id);
            executeInsert(statement);
            int id = fetchGeneratedId(statement);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }


    public void deleteSlot(int day_id, int task_id) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM slots\n" +
            "\twhere day_id = ? AND task_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, day_id);
            statement.setInt(2, task_id);
            executeInsert(statement);
            int id = fetchGeneratedId(statement);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }

    }

    public boolean checkIfSlotIsExists(int endHour, int day_id) throws SQLException {
        String sql = "Select * from slots\n" +
            "\twhere hour = ? and day_id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, endHour);
            preparedStatement.setInt(2, day_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Integer> getOccupiedHours(int dayId) throws SQLException {
        String sql = "select hour from slots\n" +
            "\twhere day_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Integer> occupiedHours = new ArrayList<>();
            statement.setInt(1, dayId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    occupiedHours.add(fetchSlotHours(resultSet));
                }
                return occupiedHours;
            }
        }
    }

    private Integer fetchSlotHours(ResultSet resultSet) throws SQLException {
        return resultSet.getInt("hour");
    }


    private Slot fetchSlot(ResultSet resultSet) throws SQLException {
        int hour = resultSet.getInt("hour");
        int dayId = resultSet.getInt("day_id");
        int taskId = resultSet.getInt("task_id");

        return new Slot(hour, dayId, taskId);

    }

}
