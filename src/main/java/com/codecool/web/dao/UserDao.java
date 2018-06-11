package com.codecool.web.dao;

import com.codecool.web.model.User;

import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    User findByEmail(String email) throws SQLException;
    User addNewUser(String name, String email, String password) throws SQLException;
    User addNewGoogleUser(String name, String email) throws SQLException;
    List<User> getAllUsers() throws SQLException;
}
