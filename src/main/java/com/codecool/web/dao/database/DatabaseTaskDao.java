package com.codecool.web.dao.database;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTaskDao extends AbstractDao implements TaskDao {

    public DatabaseTaskDao(Connection connection) {
        super(connection);
    }

    public Task addNewTask(String name, String content, int user_id) throws SQLException {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Name can not be empty");
        }
        if (!doesTaskExists(name)) {
            throw new IllegalArgumentException("Task with this name already exists");
        }
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO tasks (name,content,user_id) VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, content);
            preparedStatement.setInt(3, user_id);
            executeInsert(preparedStatement);
            int id = fetchGeneratedId(preparedStatement);
            return new Task(id, name, content);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    public void updateTask(String name, String content, int task_id) throws SQLException {
        if (doesTaskExists(name)) {
            throw new IllegalArgumentException("A task with that name already exists");
        }
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE tasks SET name = ?, content = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, content);
            preparedStatement.setInt(3, task_id);
            executeInsert(preparedStatement);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    private boolean doesTaskExists(String name) throws SQLException {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        String sql = "SELECT * FROM tasks WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Task findById(int id) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchTask(resultSet);
                }
            }
        }
        return null;
    }

    public List<Task> getAllTasks() throws SQLException {
        String sql = "SELECT * FROM tasks;";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Task> allTasks = new ArrayList<>();
            while (resultSet.next()) {
                allTasks.add(fetchTask(resultSet));
            }
            return allTasks;
        }
    }

    public void deleteTask(int id) throws SQLException{
        String sql = "DELETE FROM slots WHERE task_id =?;\n" +
            "DELETE FROM tasks WHERE id =?;";
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    public List<Task> getTaskByDayId (int day_id) throws SQLException {
        String sql = "SELECT id, user_id, name FROM tasks\n" +
            "LEFT JOIN slots\n" +
            "ON tasks.id = slots.task_id\n" +
            "WHERE day_id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            List<Task> tasksByDay = new ArrayList<>();
            preparedStatement.setInt(1, day_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    tasksByDay.add(fetchTask(resultSet));
                }
            }
            return tasksByDay;
        }
    }

    public List<Task> getUsersTask(int user_id) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            List<Task> usersTask = new ArrayList<>();
            preparedStatement.setInt(1, user_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usersTask.add(fetchTask(resultSet));
                }
            }
            return usersTask;
        }
    }


    private Task fetchTask(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String content = resultSet.getString("content");
        return new Task(id, name, content);

    }
}
