package com.assessment.servlet;

import com.assessment.dao.UserDAO;
import com.assessment.model.Question;
import com.assessment.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/dashboard")
public class UserDashboardServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        // ==============================================
        // Check session
        // ==============================================

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


        // ==============================================
        // Get logged-in user
        // ==============================================

        User user =
                (User) session.getAttribute("user");


        // ==============================================
        // Only normal USER can access
        // ==============================================

        if (!"USER".equals(user.getRole())) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "User access required."
            );

            return;
        }


        // ==============================================
        // User must be approved
        // ==============================================

        if (!"APPROVED".equals(
                user.getApprovalStatus())) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.html?error=notapproved"
            );

            return;
        }


        // ==============================================
        // Find assigned question set
        // ==============================================

        int questionSetId =
                userDAO.getAssignedQuestionSetId(
                        user.getId()
                );


        // ==============================================
        // No question set assigned
        // ==============================================

        if (questionSetId == 0) {

            request.setAttribute(
                    "message",
                    "No assessment has been assigned to you yet."
            );

            request.getRequestDispatcher(
                    "/user-dashboard.jsp"
            ).forward(request, response);

            return;
        }


        // ==============================================
        // Load questions
        // ==============================================

        List<Question> questions =
                userDAO.getQuestionsBySetId(
                        questionSetId
                );


        // ==============================================
        // Send data to JSP
        // ==============================================

        request.setAttribute(
                "questions",
                questions
        );

        request.setAttribute(
                "questionSetId",
                questionSetId
        );


        // ==============================================
        // Open User Dashboard
        // ==============================================

        request.getRequestDispatcher(
                "/user-dashboard.jsp"
        ).forward(request, response);
    }
}
