package com.assessment.servlet;

import com.assessment.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.assessment.model.User;

@WebServlet("/admin/approval")
public class ApprovalServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Check Admin session
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.html"
            );

            return;
        }

        // Get logged-in user
        User loggedInUser =
                (User) session.getAttribute("user");

        // Only ADMIN can approve/reject users
        if (!"ADMIN".equals(loggedInUser.getRole())) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Access denied. Admin privileges required."
            );

            return;
        }

        // Get user ID
        String userIdParameter =
                request.getParameter("userId");

        // Get requested action
        String action =
                request.getParameter("action");

        if (userIdParameter == null || action == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/dashboard"
            );

            return;
        }

        try {

            int userId =
                    Integer.parseInt(userIdParameter);

            String status;

            if ("approve".equalsIgnoreCase(action)) {

                status = "APPROVED";

            } else if ("reject".equalsIgnoreCase(action)) {

                status = "REJECTED";

            } else {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid approval action."
                );

                return;
            }

            // Update database
            userDAO.updateApprovalStatus(userId, status);

            // Return to Admin Dashboard
            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/dashboard"
            );

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid user ID."
            );
        }
    }
}