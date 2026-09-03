package com.assessment.dao;

import com.assessment.model.SubmittedAnswer;
import com.assessment.model.User;
import com.assessment.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SubmissionDAO {


    // =========================================================
    // 1. Get users who have submitted an assessment
    // =========================================================

    public List<User> getSubmittedUsers() {

        List<User> users = new ArrayList<>();

        String sql = """
                SELECT
                    u.id,
                    u.name,
                    u.email,
                    u.password,
                    u.role,
                    u.approval_status
                FROM users u
                INNER JOIN submission_details s
                    ON u.id = s.user_id
                WHERE u.role = 'USER'
                ORDER BY s.submitted_at DESC
                """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                users.add(
                        new User(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("role"),
                                resultSet.getString("approval_status")
                        )
                );
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Database error while loading submitted users",
                    e
            );
        }

        return users;
    }


    // =========================================================
    // 2. Get submitted answers for one user
    // =========================================================

    public List<SubmittedAnswer> getSubmittedAnswers(
            int userId) {

        List<SubmittedAnswer> answers =
                new ArrayList<>();


        String sql = """
                SELECT
                    q.id AS question_id,
                    q.question_text,
                    q.question_type,

                    q.option_a,
                    q.option_b,
                    q.option_c,
                    q.option_d,

                    q.correct_option,

                    ua.selected_option,
                    ua.answer_text,
                    ua.answer_status,
                    ua.admin_comment,

                    u.name,
                    u.email

                FROM user_answers ua

                INNER JOIN questions q
                    ON ua.question_id = q.id

                INNER JOIN users u
                    ON ua.user_id = u.id

                INNER JOIN submission_details s
                    ON ua.user_id = s.user_id

                WHERE ua.user_id = ?

                ORDER BY q.id
                """;


        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {


            statement.setInt(1, userId);


            try (ResultSet resultSet =
                         statement.executeQuery()) {


                while (resultSet.next()) {


                    String questionType =
                            resultSet.getString(
                                    "question_type"
                            );


                    String selectedOption =
                            resultSet.getString(
                                    "selected_option"
                            );


                    String correctOption =
                            resultSet.getString(
                                    "correct_option"
                            );


                    String answerStatus =
                            resultSet.getString(
                                    "answer_status"
                            );


                    /*
                     * Automatically evaluate MCQ.
                     *
                     * We do not allow the Admin to manually
                     * review MCQ.
                     */
                    if ("MCQ".equals(questionType)
                            && selectedOption != null
                            && correctOption != null) {

                        if (selectedOption.equals(
                                correctOption)) {

                            answerStatus = "CORRECT";

                        } else {

                            answerStatus = "INCORRECT";
                        }


                        updateMCQStatus(
                                connection,
                                resultSet.getInt("question_id"),
                                userId,
                                answerStatus
                        );
                    }


                    answers.add(
                            new SubmittedAnswer(

                                    resultSet.getInt(
                                            "question_id"
                                    ),

                                    resultSet.getString(
                                            "question_text"
                                    ),

                                    questionType,

                                    resultSet.getString(
                                            "option_a"
                                    ),

                                    resultSet.getString(
                                            "option_b"
                                    ),

                                    resultSet.getString(
                                            "option_c"
                                    ),

                                    resultSet.getString(
                                            "option_d"
                                    ),

                                    correctOption,

                                    selectedOption,

                                    resultSet.getString(
                                            "answer_text"
                                    ),

                                    answerStatus,

                                    resultSet.getString(
                                            "admin_comment"
                                    ),

                                    resultSet.getString(
                                            "name"
                                    ),

                                    resultSet.getString(
                                            "email"
                                    )
                            )
                    );
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Database error while loading submitted answers",
                    e
            );
        }


        return answers;
    }


    // =========================================================
    // 3. Automatically update MCQ status
    // =========================================================

    private void updateMCQStatus(
            Connection connection,
            int questionId,
            int userId,
            String status)
            throws Exception {


        String sql = """
                UPDATE user_answers
                SET answer_status = ?
                WHERE user_id = ?
                  AND question_id = ?
                """;


        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, userId);
            statement.setInt(3, questionId);

            statement.executeUpdate();
        }
    }


    // =========================================================
    // 4. Admin reviews THEORY / CODING
    // =========================================================

    public boolean updateReview(
            int userId,
            int questionId,
            String status,
            String adminComment) {


        String sql = """
                UPDATE user_answers ua

                INNER JOIN questions q
                    ON ua.question_id = q.id

                SET
                    ua.answer_status = ?,
                    ua.admin_comment = ?,
                    ua.reviewed_at = CURRENT_TIMESTAMP

                WHERE ua.user_id = ?
                  AND ua.question_id = ?
                  AND q.question_type IN ('THEORY', 'CODING')
                """;


        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {


            statement.setString(1, status);

            statement.setString(
                    2,
                    adminComment
            );

            statement.setInt(3, userId);

            statement.setInt(
                    4,
                    questionId
            );


            return statement.executeUpdate() > 0;


        } catch (Exception e) {

            throw new RuntimeException(
                    "Database error while saving answer review",
                    e
            );
        }
    }
}