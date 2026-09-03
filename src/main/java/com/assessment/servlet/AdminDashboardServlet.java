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
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ---------------------------------------------
        // Check session
        // ---------------------------------------------

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("user") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.html"
            );

            return;
        }


        // ---------------------------------------------
        // Check Admin role
        // ---------------------------------------------

        User loggedInUser =
                (User) session.getAttribute("user");

        if (!"ADMIN".equals(loggedInUser.getRole())) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Admin access required."
            );

            return;
        }


        // ---------------------------------------------
        // Load all users
        // ---------------------------------------------

        List<User> users =
                userDAO.getAllUsers();


        // ---------------------------------------------
        // Load approved users
        // ---------------------------------------------

        List<User> approvedUsers =
                userDAO.getApprovedUsers();


        // ---------------------------------------------
        // Load question sets
        // ---------------------------------------------

        List<String[]> questionSets =
                userDAO.getAllQuestionSets();


        request.setAttribute(
                "users",
                users
        );

        request.setAttribute(
                "approvedUsers",
                approvedUsers
        );

        request.setAttribute(
                "questionSets",
                questionSets
        );


        // ---------------------------------------------
        // Forward to JSP
        // ---------------------------------------------

        request.getRequestDispatcher(
                "/admin-dashboard.jsp"
        ).forward(request, response);
    }
}
