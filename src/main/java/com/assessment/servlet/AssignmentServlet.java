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

@WebServlet("/admin/assignment")
public class AssignmentServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ---------------------------------------------
        // Check Admin session
        // ---------------------------------------------

        HttpSession session = request.getSession(false);

        if (session == null ||
                session.getAttribute("user") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.html"
            );

            return;
        }


        // ---------------------------------------------
        // Verify Admin role
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
        // Read form values
        // ---------------------------------------------

        String userIdParameter =
                request.getParameter("userId");

        String questionSetIdParameter =
                request.getParameter("questionSetId");


        if (userIdParameter == null ||
                questionSetIdParameter == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/dashboard"
            );

            return;
        }


        try {

            int userId =
                    Integer.parseInt(userIdParameter);

            int questionSetId =
                    Integer.parseInt(questionSetIdParameter);


            // -----------------------------------------
            // Assign question set
            // -----------------------------------------

            userDAO.assignQuestionSet(
                    userId,
                    questionSetId
            );


            // -----------------------------------------
            // Return to Admin Dashboard
            // -----------------------------------------

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/dashboard"
            );

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid user or question set ID."
            );
        }
    }
}
