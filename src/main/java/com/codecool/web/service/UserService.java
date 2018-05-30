package com.codecool.web.service;

import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    User findUserByEmail(String email) throws SQLException, ServiceException;
    User addNewUser(String name, String email, String password) throws SQLException, IllegalArgumentException, ServiceException;
    List<User> getAllUsers() throws SQLException, ServiceException;
}
