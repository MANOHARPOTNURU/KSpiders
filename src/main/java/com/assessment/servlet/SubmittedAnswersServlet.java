package com.assessment.servlet;

import com.assessment.dao.SubmissionDAO;
import com.assessment.model.SubmittedAnswer;
import com.assessment.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/submissions")
public class SubmittedAnswersServlet extends HttpServlet {

    private final SubmissionDAO submissionDAO =
            new SubmissionDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ==========================================
        // 1. Check session
        // ==========================================

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


        // ==========================================
        // 2. Check ADMIN role
        // ==========================================

        User admin =
                (User) session.getAttribute("user");

        if (!"ADMIN".equals(admin.getRole())) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Admin access required."
            );

            return;
        }


        // ==========================================
        // 3. Read userId
        // ==========================================

        String userIdParameter =
                request.getParameter("userId");


        // ==========================================
        // 4. NO userId
        //    Show list of submitted users
        // ==========================================

        if (userIdParameter == null ||
                userIdParameter.trim().isEmpty()) {

            List<User> submittedUsers =
                    submissionDAO.getSubmittedUsers();

            request.setAttribute(
                    "submittedUsers",
                    submittedUsers
            );

            request.getRequestDispatcher(
                    "/submitted-assessments.jsp"
            ).forward(
                    request,
                    response
            );

            return;
        }


        // ==========================================
        // 5. userId EXISTS
        //    Show that user's answers
        // ==========================================

        try {

            int userId =
                    Integer.parseInt(
                            userIdParameter
                    );


            List<SubmittedAnswer> answers =
                    submissionDAO.getSubmittedAnswers(
                            userId
                    );


            if (answers == null ||
                    answers.isEmpty()) {

                request.setAttribute(
                        "errorMessage",
                        "No submitted answers were found for user ID "
                                + userId + "."
                );

                request.getRequestDispatcher(
                        "/submission-error.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }


            // ======================================
            // Pass data to submitted-answers.jsp
            // ======================================

            request.setAttribute(
                    "answers",
                    answers
            );

            request.setAttribute(
                    "userName",
                    answers.get(0).getUserName()
            );

            request.setAttribute(
                    "userEmail",
                    answers.get(0).getUserEmail()
            );


            request.getRequestDispatcher(
                    "/submitted-answers.jsp"
            ).forward(
                    request,
                    response
            );


        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid user ID."
            );
        }
    }
}