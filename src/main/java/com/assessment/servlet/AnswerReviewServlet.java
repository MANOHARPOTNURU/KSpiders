package com.assessment.servlet;

import com.assessment.dao.SubmissionDAO;
import com.assessment.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin/review")
public class AnswerReviewServlet extends HttpServlet {

    private final SubmissionDAO submissionDAO =
            new SubmissionDAO();


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        // =====================================================
        // 1. Check Admin session
        // =====================================================

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


        // =====================================================
        // 2. Check Admin role
        // =====================================================

        User admin =
                (User) session.getAttribute("user");


        if (!"ADMIN".equals(admin.getRole())) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Admin access required."
            );

            return;
        }


        // =====================================================
        // 3. Get submitted values
        // =====================================================

        String userIdParameter =
                request.getParameter("userId");


        String questionIdParameter =
                request.getParameter("questionId");


        String status =
                request.getParameter("status");


        String adminComment =
                request.getParameter("adminComment");


        if (userIdParameter == null ||
                questionIdParameter == null ||
                status == null) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Required review information is missing."
            );

            return;
        }


        int userId;
        int questionId;


        try {

            userId =
                    Integer.parseInt(
                            userIdParameter
                    );

            questionId =
                    Integer.parseInt(
                            questionIdParameter
                    );

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid user ID or question ID."
            );

            return;
        }


        // =====================================================
        // 4. Validate review status
        // =====================================================

        if (!"CORRECT".equals(status)
                && !"PARTIALLY_CORRECT".equals(status)
                && !"INCORRECT".equals(status)) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid review status."
            );

            return;
        }


        // =====================================================
        // 5. Save review
        // =====================================================

        submissionDAO.updateReview(
                userId,
                questionId,
                status,
                adminComment
        );


        // =====================================================
        // 6. Return to submitted answers
        // =====================================================

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/submissions?userId="
                        + userId
        );
    }
}
