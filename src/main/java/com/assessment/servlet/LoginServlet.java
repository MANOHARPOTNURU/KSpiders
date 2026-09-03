package com.assessment.servlet;

import com.assessment.dao.UserDAO;
import com.assessment.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");


        System.out.println(
                "LOGIN EMAIL = [" + email + "]"
        );


        User user =
                userDAO.authenticate(
                        email,
                        password
                );


        // ==========================================
        // INVALID LOGIN
        // ==========================================

        if (user == null) {

            System.out.println(
                    "LOGIN RESULT = USER NOT FOUND"
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.html?error=invalid"
            );

            return;
        }


        System.out.println(
                "LOGIN RESULT = USER FOUND"
        );

        System.out.println(
                "USER ROLE = [" +
                        user.getRole() + "]"
        );

        System.out.println(
                "APPROVAL STATUS = [" +
                        user.getApprovalStatus() + "]"
        );


        // ==========================================
        // CREATE SESSION
        // ==========================================

        HttpSession session =
                request.getSession();

        session.setAttribute(
                "user",
                user
        );


        // ==========================================
        // ADMIN
        // ==========================================

        if ("ADMIN".equals(user.getRole())) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/dashboard"
            );

            return;
        }


        // ==========================================
        // USER
        // ==========================================

        if ("USER".equals(user.getRole())) {


            // User must be approved
            if (!"APPROVED".equals(
                    user.getApprovalStatus())) {

                session.invalidate();

                response.sendRedirect(
                        request.getContextPath()
                                + "/login.html?error=notapproved"
                );

                return;
            }


            // Approved user
            response.sendRedirect(
                    request.getContextPath()
                            + "/user/dashboard"
            );

            return;
        }


        // ==========================================
        // UNKNOWN ROLE
        // ==========================================

        session.invalidate();

        response.sendRedirect(
                request.getContextPath()
                        + "/login.html?error=invalid"
        );
    }
}