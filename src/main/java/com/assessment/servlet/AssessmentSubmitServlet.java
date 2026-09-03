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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/assessment/submit")
public class AssessmentSubmitServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // =====================================================
        // 1. Check session
        // =====================================================

        HttpSession session = request.getSession(false);

        if (session == null ||
                session.getAttribute("user") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.html"
            );

            return;
        }


        // =====================================================
        // 2. Get logged-in user
        // =====================================================

        User user =
                (User) session.getAttribute("user");


        // =====================================================
        // 3. Only USER can submit
        // =====================================================

        if (!"USER".equals(user.getRole())) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Only users can submit assessments."
            );

            return;
        }


        // =====================================================
        // 4. User must be APPROVED
        // =====================================================

        if (!"APPROVED".equals(user.getApprovalStatus())) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "User is not approved."
            );

            return;
        }


        // =====================================================
        // 5. Get question set ID
        // =====================================================

        String questionSetIdParameter =
                request.getParameter("questionSetId");

        if (questionSetIdParameter == null) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Question set ID is missing."
            );

            return;
        }

        int questionSetId;

        try {

            questionSetId =
                    Integer.parseInt(questionSetIdParameter);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid question set ID."
            );

            return;
        }


        // =====================================================
        // 6. Verify the user's assigned set
        // =====================================================

        int assignedSetId =
                userDAO.getAssignedQuestionSetId(
                        user.getId()
                );

        if (assignedSetId == 0) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No assessment is assigned to this user."
            );

            return;
        }

        if (assignedSetId != questionSetId) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "This assessment is not assigned to this user."
            );

            return;
        }


        // =====================================================
        // 7. Load the actual 30 questions
        // =====================================================

        List<Question> questions =
                userDAO.getQuestionsBySetId(
                        questionSetId
                );


        if (questions.size() != 30) {

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Assessment configuration error: expected 30 questions."
            );

            return;
        }


        // =====================================================
        // 8. Collect answers
        // =====================================================

        Map<Integer, String> answers =
                new HashMap<>();


        for (Question question : questions) {

            String parameterName =
                    "answer_" + question.getId();

            String answer =
                    request.getParameter(parameterName);


            // ---------------------------------------------
            // Answer is mandatory
            // ---------------------------------------------

            if (answer == null ||
                    answer.trim().isEmpty()) {

                request.setAttribute(
                        "errorMessage",
                        "Please answer all 30 questions before submitting."
                );

                request.getRequestDispatcher(
                        "/submission-error.jsp"
                ).forward(request, response);

                return;
            }


            answers.put(
                    question.getId(),
                    answer.trim()
            );
        }


        // =====================================================
        // 9. Final answer count check
        // =====================================================

        if (answers.size() != questions.size()) {

            request.setAttribute(
                    "errorMessage",
                    "All 30 questions must be answered."
            );

            request.getRequestDispatcher(
                    "/submission-error.jsp"
            ).forward(request, response);

            return;
        }


        // =====================================================
        // 10. Save submission
        // =====================================================

        try {

            userDAO.submitAssessment(
                    user.getId(),
                    questionSetId,
                    answers
            );


            request.setAttribute(
                    "submittedCount",
                    answers.size()
            );


            request.getRequestDispatcher(
                    "/submission-success.jsp"
            ).forward(
                    request,
                    response
            );


        } catch (RuntimeException e) {

            e.printStackTrace();


            request.setAttribute(
                    "errorMessage",
                    e.getMessage()
            );


            request.getRequestDispatcher(
                    "/submission-error.jsp"
            ).forward(
                    request,
                    response
            );
        }
    }
}