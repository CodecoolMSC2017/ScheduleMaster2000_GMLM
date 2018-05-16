package com.codecool.web.dao.database;

import com.codecool.web.dao.DayDao;
import com.codecool.web.model.Day;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDayDao extends AbstractDao implements DayDao {

    DatabaseDayDao(Connection connection) {
        super(connection);
    }

    public Day addNewDay(int schedule_id, String title) throws SQLException {
        if (title == null || title.equals("")) {
            throw new IllegalArgumentException("You can't create a day not assigned to a schedule");
        }
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "insert into days (schedule_id,title) VALUES (?,?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, schedule_id);
            preparedStatement.setString(2, title);
            executeInsert(preparedStatement);
            int id = fetchGeneratedId(preparedStatement);
            return new Day(id, title);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    public void updateDayName(String title, int id) throws SQLException {
        if (doesDayTitleExists(title)) {
            throw new IllegalArgumentException("A day with that title already exists");
        }
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE days \n" +
            "SET title = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, id);
            executeInsert(preparedStatement);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    public void assignDayToSchedule(int schedule_id, int day_id) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE days \n" +
            "SET schedule_id = ?\n" +
            "WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, schedule_id);
            preparedStatement.setInt(2, day_id);
            executeInsert(preparedStatement);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    public void deleteDay(int id) throws SQLException {
        String sql = "DELETE FROM slots where day_id = ?;\n" +
            "DELETE FROM days where id = ?;";
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    public List<Day> getAllDays() throws SQLException {
        String sql = "SELECT * FROM days;";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Day> allDays = new ArrayList<>();
            while (resultSet.next()) {
                allDays.add(fetchDay(resultSet));
            }
            return allDays;
        }
    }

    public Day findById(int id) throws SQLException {
        String sql = "SELECT * FROM days\n" +
            "WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchDay(resultSet);
                }
            }
        }
        return null;
    }

    private Day fetchDay(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        return new Day(id, title);
    }

    private boolean doesDayTitleExists(String title) throws SQLException {
        if (title == null || title.equals("")) {
            throw new IllegalArgumentException("Day's title can not be empty");
        }
        String sql = "SELECT * FROM days WHERE title = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return false;
                }
            }
        }
        return true;
    }

}
