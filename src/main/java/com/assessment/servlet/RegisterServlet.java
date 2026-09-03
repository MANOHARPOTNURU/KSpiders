package com.assessment.servlet;

import com.assessment.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    private static final String ADMIN_REGISTRATION_KEY = "ADMIN@2026";

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String adminKey = request.getParameter("adminKey");

        // Register USER
        if ("USER".equals(role)) {

            boolean registered =
                    userDAO.registerUser(name, email, password);

            if (registered) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/login.html?registered=true"
                );

            } else {

                response.sendRedirect(
                        request.getContextPath()
                                + "/register.html?error=emailExists"
                );
            }

            return;
        }


        // Register ADMIN
        if ("ADMIN".equals(role)) {

            if (!ADMIN_REGISTRATION_KEY.equals(adminKey)) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/register.html?error=invalidAdminKey"
                );

                return;
            }


            boolean registered =
                    userDAO.registerAdmin(name, email, password);

            if (registered) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/login.html?registered=true"
                );

            } else {

                response.sendRedirect(
                        request.getContextPath()
                                + "/register.html?error=emailExists"
                );
            }

            return;
        }


        response.sendRedirect(
                request.getContextPath()
                        + "/register.html?error=invalidRole"
        );
    }
}
