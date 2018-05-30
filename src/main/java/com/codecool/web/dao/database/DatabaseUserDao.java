package com.codecool.web.dao.database;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DatabaseUserDao extends AbstractDao implements UserDao {

    public DatabaseUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("Email cannot be empty!");
        }
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchUser(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public User addNewUser(String name, String email, String password) throws SQLException {
        if (name == null || name.equals("") || email == null || email.equals("") || password == null || password.equals("")) {
            throw new IllegalArgumentException("Name, email, password fields cannot be empty or null!");
        }
        if (!validateEmail(email)) {
            throw new IllegalArgumentException("Invalid email address type! Try example@ex.com");
        }
        if (isEmailExists(email)) {
            throw new IllegalArgumentException("Email address does already exist, try another one!");
        }
        if (!validatePassword(password)) {
            throw new IllegalArgumentException("Invalid password type! Password must contain uppercase, lowercase and digit characters!");
        }
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO users (name, email, password, permission) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setBoolean(4, false);
            executeInsert(statement);
            int id = fetchGeneratedId(statement);
            return new User(id, name, email, password, false);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                users.add(fetchUser(resultSet));
            }
        }
        return users;
    }

    private User fetchUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        boolean isAdmin = resultSet.getBoolean("permission");
        return new User(id, name, email, password, isAdmin);
    }

    private boolean isEmailExists(String email) throws SQLException {
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("Email cannot be empty or null!");
        }
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return email != null && pat.matcher(email).matches();
    }

    private boolean validatePassword(String pw) {
        boolean isUpperCase = false;
        boolean isLowerCase = false;
        boolean isDigit = false;
        boolean isLengthProper = false;
        for (int i = 0; i < pw.length(); i++) {
            char ch = pw.charAt(i);
            if (pw.length() >= 8) {
                isLengthProper = true;
            }
            if (Character.isUpperCase(ch)) {
                isUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                isLowerCase = true;
            } else if (Character.isDigit(ch)) {
                isDigit = true;
            }
        }
        return isUpperCase && isLowerCase && isDigit && isLengthProper;
    }
}
