package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.UserService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleLoginService;
import com.codecool.web.service.simple.SimpleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/googleLogin")
public class GoogleLoginServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(GoogleLoginServlet.class);
    private String email;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            LoginService loginService = new SimpleLoginService(userDao);

            email = req.getParameter("email");

            User googleUser = loginService.googleLoginUser(email);
            req.getSession().setAttribute("user", googleUser);

            sendMessage(resp, HttpServletResponse.SC_OK, googleUser);
        } catch (SQLException e) {
            handleSqlError(resp, e);
            logger.debug("Exception has been caught: " + e.getMessage());
        } catch (ServiceException e) {
            try (Connection connection = getConnection(req.getServletContext())) {
                UserDao userDao = new DatabaseUserDao(connection);
                UserService userService = new SimpleUserService(userDao);
                LoginService loginService = new SimpleLoginService(userDao);

                if (e.getMessage().equals("This google account isn't registered yet.")) {
                    String name = req.getParameter("name");

                    userService.addNewGoogleUser(name, email);

                    User googleUser = loginService.googleLoginUser(email);
                    req.getSession().setAttribute("user", googleUser);
                    sendMessage(resp, HttpServletResponse.SC_OK, googleUser);
                }
            } catch (SQLException ex) {
                handleSqlError(resp, ex);
                logger.debug("Exception has been caught: " + e.getMessage());
            } catch (ServiceException ex) {
                sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                logger.debug("Exception has been caught: " + e.getMessage());
            }
        }
    }
}
