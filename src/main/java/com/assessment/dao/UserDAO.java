package com.assessment.dao;

import com.assessment.model.Question;
import com.assessment.model.User;
import com.assessment.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDAO {


    // =========================================================
    // 1. Authenticate
    // =========================================================

    public User authenticate(
            String email,
            String password) {

        String sql = """
                SELECT
                    id,
                    name,
                    email,
                    password,
                    role,
                    approval_status
                FROM users
                WHERE email = ?
                  AND password = ?
                """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("role"),
                            resultSet.getString("approval_status")
                    );
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Database error during login",
                    e
            );
        }

        return null;
    }


    // =========================================================
    // 2. Register USER
    // =========================================================

    public boolean registerUser(
            String name,
            String email,
            String password) {

        String sql = """
                INSERT INTO users
                (
                    name,
                    email,
                    password,
                    role,
                    approval_status
                )
                VALUES
                (
                    ?,
                    ?,
                    ?,
                    'USER',
                    'PENDING'
                )
                """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);

            return statement.executeUpdate() > 0;

        } catch (
                java.sql.SQLIntegrityConstraintViolationException e) {

            return false;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Database error during user registration",
                    e
            );
        }
    }


    // =========================================================
    // 3. Register ADMIN
    // =========================================================

    public boolean registerAdmin(
            String name,
            String email,
            String password) {

        String sql = """
                INSERT INTO users
                (
                    name,
                    email,
                    password,
                    role,
                    approval_status
                )
                VALUES
                (
                    ?,
                    ?,
                    ?,
                    'ADMIN',
                    'APPROVED'
                )
                """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);

            return statement.executeUpdate() > 0;

        } catch (
                java.sql.SQLIntegrityConstraintViolationException e) {

            return false;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Database error during admin registration",
                    e
            );
        }
    }


    // =========================================================
    // 4. Get all users
    // =========================================================

    public List<User> getAllUsers() {

        List<User> users =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    name,
                    email,
                    password,
                    role,
                    approval_status
                FROM users
                ORDER BY id
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
                    "Database error while fetching users",
                    e
            );
        }

        return users;
    }


    // =========================================================
    // 5. Update approval status
    // =========================================================

    public boolean updateApprovalStatus(
            int userId,
            String status) {

        String sql = """
                UPDATE users
                SET approval_status = ?
                WHERE id = ?
                  AND role = 'USER'
                """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, userId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Database error while updating approval status",
                    e
            );
        }
    }


    // =========================================================
    // 6. Get approved users
    // =========================================================

    public List<User> getApprovedUsers() {

        List<User> users =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    name,
                    email,
                    password,
                    role,
                    approval_status
                FROM users
                WHERE role = 'USER'
                  AND approval_status = 'APPROVED'
                ORDER BY name
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
                    "Database error while fetching approved users",
                    e
            );
        }

        return users;
    }


    // =========================================================
    // 7. Get all question sets
    // =========================================================

    public List<String[]> getAllQuestionSets() {

        List<String[]> sets =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    set_name
                FROM question_sets
                ORDER BY id
                """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                sets.add(
                        new String[]{
                                String.valueOf(
                                        resultSet.getInt("id")
                                ),
                                resultSet.getString("set_name")
                        }
                );
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Database error while fetching question sets",
                    e
            );
        }

        return sets;
    }


    // =========================================================
    // 8. Assign / reassign question set
    // =========================================================

    public boolean assignQuestionSet(
            int userId,
            int questionSetId) {

        String sql = """
                INSERT INTO user_set_assignment
                (
                    user_id,
                    question_set_id
                )
                VALUES
                (
                    ?,
                    ?
                )
                ON DUPLICATE KEY UPDATE
                    question_set_id =
                        VALUES(question_set_id),
                    assigned_at =
                        CURRENT_TIMESTAMP
                """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, questionSetId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Database error while assigning question set",
                    e
            );
        }
    }


    // =========================================================
    // 9. Get assigned set ID
    // =========================================================

    public int getAssignedQuestionSetId(
            int userId) {

        String sql = """
                SELECT question_set_id
                FROM user_set_assignment
                WHERE user_id = ?
                """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return resultSet.getInt(
                            "question_set_id"
                    );
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Database error while fetching assigned set",
                    e
            );
        }

        return 0;
    }


    // =========================================================
    // 10. Get questions by set
    // =========================================================

    public List<Question> getQuestionsBySetId(
            int questionSetId) {

        List<Question> questions =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    question_set_id,
                    question_text,
                    option_a,
                    option_b,
                    option_c,
                    option_d,
                    correct_option,
                    question_type
                FROM questions
                WHERE question_set_id = ?
                ORDER BY id
                """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, questionSetId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    questions.add(
                            new Question(
                                    resultSet.getInt("id"),
                                    resultSet.getInt(
                                            "question_set_id"
                                    ),
                                    resultSet.getString(
                                            "question_text"
                                    ),
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
                                    resultSet.getString(
                                            "correct_option"
                                    ),
                                    resultSet.getString(
                                            "question_type"
                                    )
                            )
                    );
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Database error while fetching questions",
                    e
            );
        }

        return questions;
    }


    // =========================================================
    // 11. Submit assessment
    // =========================================================

    public void submitAssessment(
            int userId,
            int questionSetId,
            Map<Integer, String> answers) {


        String checkAssignmentSql = """
                SELECT question_set_id
                FROM user_set_assignment
                WHERE user_id = ?
                """;


        String insertAnswerSql = """
                INSERT INTO user_answers
                (
                    user_id,
                    question_id,
                    selected_option,
                    answer_text
                )
                VALUES
                (
                    ?,
                    ?,
                    ?,
                    ?
                )
                ON DUPLICATE KEY UPDATE
                    selected_option =
                        VALUES(selected_option),
                    answer_text =
                        VALUES(answer_text),
                    answered_at =
                        CURRENT_TIMESTAMP
                """;


        String insertSubmissionSql = """
                INSERT INTO submission_details
                (
                    user_id,
                    status
                )
                VALUES
                (
                    ?,
                    'SUBMITTED'
                )
                ON DUPLICATE KEY UPDATE
                    submitted_at =
                        CURRENT_TIMESTAMP,
                    status =
                        'SUBMITTED'
                """;


        Connection connection = null;


        try {

            connection =
                    DBConnection.getConnection();

            connection.setAutoCommit(false);


            // ----------------------------------------------
            // Verify assigned set
            // ----------------------------------------------

            try (PreparedStatement checkStatement =
                         connection.prepareStatement(
                                 checkAssignmentSql)) {

                checkStatement.setInt(
                        1,
                        userId
                );


                try (ResultSet resultSet =
                             checkStatement.executeQuery()) {

                    if (!resultSet.next()) {

                        throw new RuntimeException(
                                "No assessment set assigned to user."
                        );
                    }


                    int assignedSet =
                            resultSet.getInt(
                                    "question_set_id"
                            );


                    if (assignedSet != questionSetId) {

                        throw new RuntimeException(
                                "User is not assigned to this question set."
                        );
                    }
                }
            }


            // ----------------------------------------------
            // Load actual questions
            // ----------------------------------------------

            String questionSql = """
                    SELECT
                        id,
                        question_type
                    FROM questions
                    WHERE question_set_id = ?
                    """;


            Map<Integer, String> questionTypes =
                    new java.util.HashMap<>();


            try (PreparedStatement questionStatement =
                         connection.prepareStatement(
                                 questionSql)) {

                questionStatement.setInt(
                        1,
                        questionSetId
                );


                try (ResultSet resultSet =
                             questionStatement.executeQuery()) {

                    while (resultSet.next()) {

                        questionTypes.put(
                                resultSet.getInt("id"),
                                resultSet.getString(
                                        "question_type"
                                )
                        );
                    }
                }
            }


            // ----------------------------------------------
            // Verify exactly 30 questions
            // ----------------------------------------------

            if (questionTypes.size() != 30) {

                throw new RuntimeException(
                        "Assessment must contain exactly 30 questions."
                );
            }


            // ----------------------------------------------
            // Verify all answers are for valid questions
            // ----------------------------------------------

            if (answers.size() != 30) {

                throw new RuntimeException(
                        "All 30 questions must be answered."
                );
            }


            for (Integer questionId :
                    answers.keySet()) {

                if (!questionTypes.containsKey(
                        questionId)) {

                    throw new RuntimeException(
                            "Invalid question submitted."
                    );
                }
            }


            // ----------------------------------------------
            // Save answers
            // ----------------------------------------------

            try (PreparedStatement answerStatement =
                         connection.prepareStatement(
                                 insertAnswerSql)) {


                for (Map.Entry<Integer, String> entry :
                        answers.entrySet()) {


                    int questionId =
                            entry.getKey();


                    String answer =
                            entry.getValue();


                    String questionType =
                            questionTypes.get(
                                    questionId
                            );


                    String selectedOption = null;
                    String answerText = null;


                    // MCQ
                    if ("MCQ".equals(
                            questionType)) {

                        if (!answer.matches("[ABCD]")) {

                            throw new RuntimeException(
                                    "Invalid MCQ answer."
                            );
                        }

                        selectedOption = answer;
                    }


                    // THEORY or CODING
                    else {

                        answerText = answer;
                    }


                    answerStatement.setInt(
                            1,
                            userId
                    );

                    answerStatement.setInt(
                            2,
                            questionId
                    );

                    answerStatement.setString(
                            3,
                            selectedOption
                    );

                    answerStatement.setString(
                            4,
                            answerText
                    );

                    answerStatement.addBatch();
                }


                answerStatement.executeBatch();
            }


            // ----------------------------------------------
            // Create/update submission record
            // ----------------------------------------------

            try (PreparedStatement submissionStatement =
                         connection.prepareStatement(
                                 insertSubmissionSql)) {

                submissionStatement.setInt(
                        1,
                        userId
                );

                submissionStatement.executeUpdate();
            }


            // ----------------------------------------------
            // Commit
            // ----------------------------------------------

            connection.commit();

        } catch (Exception e) {


            try {

                if (connection != null) {
                    connection.rollback();
                }

            } catch (Exception rollbackException) {

                rollbackException.printStackTrace();
            }


            throw new RuntimeException(
                    "Database error while submitting assessment",
                    e
            );


        } finally {


            try {

                if (connection != null) {

                    connection.setAutoCommit(true);

                    connection.close();
                }

            } catch (Exception closeException) {

                closeException.printStackTrace();
            }
        }
    }
}