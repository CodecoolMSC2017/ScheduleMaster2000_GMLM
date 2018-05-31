package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.UserService;
import com.codecool.web.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class SimpleUserService implements UserService {
    private final UserDao userDao;
    private static final Logger logger = LoggerFactory.getLogger(SimpleUserService.class);

    public SimpleUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserByEmail(String email) throws SQLException, ServiceException {
        try {
            logger.debug(String.format("User %s has been accessed", email));
            return userDao.findByEmail(email);
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception was caught: " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public User addNewUser(String name, String email, String password) throws SQLException, ServiceException {
        try {
            logger.debug("New user has been added");
            return userDao.addNewUser(name, email, password);
        } catch (IllegalArgumentException ex) {
            logger.debug("Exception was caught: " + ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException, ServiceException {
        if (userDao.getAllUsers().size() == 0) {
            throw new ServiceException("No users yet!");
        } else {
            logger.debug("All users have been accessed");
            return userDao.getAllUsers();
        }
    }
}
