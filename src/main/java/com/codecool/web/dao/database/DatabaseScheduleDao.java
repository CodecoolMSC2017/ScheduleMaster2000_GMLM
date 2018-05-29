package com.codecool.web.dao.database;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseScheduleDao extends AbstractDao implements ScheduleDao {

    public DatabaseScheduleDao(Connection connection) {
        super(connection);
    }

    public List<Schedule> getAllSchedules() throws SQLException {
        List<Schedule> allSchedules = new ArrayList<>();
        String sql = "SELECT * FROM schedules";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                allSchedules.add(fetchSchedule(resultSet));
            }
            return allSchedules;

        }
    }

    public List<Schedule> getUsersSchedules(int userId) throws SQLException {
        List<Schedule> result = new ArrayList<>();
        String sql = "SELECT * from schedules\n" +
            "where user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(fetchSchedule(resultSet));
                }
            }
        }
        return result;
    }

    public Schedule getScheduleById(int id) throws SQLException {
        String sql = "get schedule by id:\n" +
            "SELECT * from schedules\n" +
            "where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchSchedule(resultSet);
                }
            }
        }
        return null;
    }

    public void addNewSchedule(int userId, String name) throws SQLException {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }

        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO schedules (user_id,name) VALUES\n" +
            "(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, userId);
            statement.setString(2, name);
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

    public void removeSchedule(int id) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM slots WHERE day_id IN (SELECT id FROM days where schedule_id = ?);\n" +
            "DELETE FROM days WHERE schedule_id =?;\n" +
            "Delete from schedules where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, id);
            statement.setInt(3, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void updateSchedule(String name, int id) throws SQLException {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }

        String sql = "UPDATE schedules " +
            "SET name= ? " +
            "WHERE id = ?;";

        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    private Schedule fetchSchedule(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Schedule(id, name);
    }
}
