<%@ page import="java.util.List" %>
<%@ page import="com.assessment.model.SubmittedAnswer" %>

<%
    List<SubmittedAnswer> answers =
            (List<SubmittedAnswer>) request.getAttribute("answers");

    String userName =
            (String) request.getAttribute("userName");

    String userEmail =
            (String) request.getAttribute("userEmail");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Submitted Answers - Assessment System
    </title>

    <style>

        * {
            box-sizing: border-box;
        }

        html,
        body {
            width: 100%;
            min-height: 100%;
            margin: 0;
            padding: 0;
        }

        body {

            font-family: Arial, Helvetica, sans-serif;

            background: #f4f6f9;

            color: #1f2937;

            line-height: 1.5;
        }


        /* =========================================
           HEADER
           ========================================= */

        .header {

            width: 100%;

            background: #1f2937;

            color: white;

            padding: 18px 35px;

            display: flex;

            justify-content: space-between;

            align-items: center;

            gap: 20px;
        }

        .header h1 {

            margin: 0;

            font-size: 24px;

            white-space: nowrap;
        }

        .back-link {

            display: inline-block;

            background: #2563eb;

            color: white;

            text-decoration: none;

            padding: 10px 18px;

            border-radius: 7px;

            font-size: 14px;

            font-weight: 600;

            white-space: nowrap;
        }

        .back-link:hover {

            background: #1d4ed8;
        }


        /* =========================================
           MAIN CONTAINER
           ========================================= */

        .container {

            width: min(1100px, calc(100% - 48px));

            margin: 32px auto 60px;

            padding: 0;
        }


        /* =========================================
           USER INFORMATION
           ========================================= */

        .user-card {

            width: 100%;

            background: white;

            padding: 26px 30px;

            border-radius: 12px;

            margin-bottom: 26px;

            box-shadow:
                    0 4px 14px
                    rgba(0, 0, 0, 0.08);
        }

        .user-card h2 {

            margin: 0 0 10px;

            font-size: 27px;
        }

        .user-card p {

            margin: 7px 0;

            color: #6b7280;

            font-size: 15px;
        }


        /* =========================================
           QUESTION CARD
           ========================================= */

        .question-card {

            width: 100%;

            display: block;

            background: white;

            padding: 28px 30px;

            border-radius: 12px;

            margin-bottom: 24px;

            box-shadow:
                    0 4px 14px
                    rgba(0, 0, 0, 0.08);

            overflow: hidden;
        }


        .question-number {

            color: #2563eb;

            font-size: 16px;

            font-weight: 700;

            margin-bottom: 10px;
        }


        /* =========================================
           QUESTION TYPE
           ========================================= */

        .question-type {

            display: inline-block;

            padding: 6px 11px;

            border-radius: 20px;

            font-size: 12px;

            font-weight: 700;

            margin-bottom: 18px;
        }

        .mcq {

            background: #dbeafe;

            color: #1d4ed8;
        }

        .theory {

            background: #fef3c7;

            color: #92400e;
        }

        .coding {

            background: #ede9fe;

            color: #6d28d9;
        }


        /* =========================================
           QUESTION TEXT
           ========================================= */

        .question-text {

            width: 100%;

            font-size: 18px;

            font-weight: 700;

            margin-bottom: 22px;

            line-height: 1.6;

            overflow-wrap: anywhere;
        }


        /* =========================================
           ANSWER BOX
           ========================================= */

        .answer-box {

            width: 100%;

            background: #f8fafc;

            border: 1px solid #e2e8f0;

            border-radius: 9px;

            padding: 20px;

            margin-bottom: 18px;

            white-space: pre-wrap;

            overflow-wrap: anywhere;

            word-break: break-word;

            line-height: 1.7;
        }


        .answer-label {

            display: block;

            margin-bottom: 12px;

            font-weight: 700;

            color: #374151;
        }


        .code-answer {

            background: #111827;

            color: #f9fafb;

            border-color: #111827;

            font-family:
                    Consolas,
                    "Courier New",
                    monospace;

            font-size: 14px;

            overflow-x: auto;
        }


        /* =========================================
           MCQ RESULT
           ========================================= */

        .mcq-details {

            width: 100%;

            display: grid;

            grid-template-columns: repeat(2, minmax(0, 1fr));

            gap: 15px;

            margin-bottom: 18px;
        }

        .mcq-item {

            background: #f8fafc;

            border: 1px solid #e2e8f0;

            border-radius: 8px;

            padding: 16px;
        }

        .mcq-item strong {

            display: block;

            margin-bottom: 6px;

            color: #374151;
        }


        /* =========================================
           RESULT BADGES
           ========================================= */

        .correct-result,
        .incorrect-result {

            display: inline-block;

            padding: 8px 15px;

            border-radius: 20px;

            font-size: 13px;

            font-weight: 700;
        }

        .correct-result {

            background: #dcfce7;

            color: #166534;
        }

        .incorrect-result {

            background: #fee2e2;

            color: #991b1b;
        }


        /* =========================================
           ADMIN REVIEW
           ========================================= */

        .review-box {

            width: 100%;

            margin-top: 22px;

            padding: 24px;

            background: #f8fbff;

            border: 1px solid #bfdbfe;

            border-radius: 10px;
        }

        .review-box h3 {

            margin: 0 0 16px;

            color: #1e40af;

            font-size: 19px;
        }


        .current-status {

            display: inline-block;

            background: #fef3c7;

            color: #92400e;

            padding: 6px 12px;

            border-radius: 20px;

            font-size: 12px;

            font-weight: 700;

            margin-left: 5px;
        }


        .review-form {

            width: 100%;
        }


        .review-form label {

            display: block;

            margin: 18px 0 7px;

            font-weight: 700;

            color: #374151;
        }


        .review-form select {

            width: 100%;

            padding: 11px 13px;

            background: white;

            border: 1px solid #cbd5e1;

            border-radius: 7px;

            font-size: 14px;

            color: #1f2937;
        }


        .review-form textarea {

            display: block;

            width: 100%;

            min-height: 120px;

            padding: 12px 14px;

            background: white;

            border: 1px solid #cbd5e1;

            border-radius: 7px;

            font-family: Arial, Helvetica, sans-serif;

            font-size: 14px;

            resize: vertical;

            line-height: 1.5;
        }


        .review-form textarea:focus,
        .review-form select:focus {

            outline: none;

            border-color: #2563eb;

            box-shadow:
                    0 0 0 3px
                    rgba(37, 99, 235, 0.12);
        }


        .save-review-btn {

            margin-top: 16px;

            padding: 11px 20px;

            border: none;

            border-radius: 7px;

            background: #2563eb;

            color: white;

            font-size: 14px;

            font-weight: 700;

            cursor: pointer;
        }


        .save-review-btn:hover {

            background: #1d4ed8;
        }


        /* =========================================
           EXISTING COMMENT
           ========================================= */

        .admin-comment {

            margin-top: 18px;

            padding: 15px;

            background: #eef2ff;

            border-left: 4px solid #6366f1;

            border-radius: 6px;

            white-space: pre-wrap;

            overflow-wrap: anywhere;
        }


        /* =========================================
           EMPTY
           ========================================= */

        .empty {

            width: 100%;

            background: white;

            padding: 35px;

            border-radius: 10px;

            text-align: center;

            color: #6b7280;
        }


        /* =========================================
           RESPONSIVE
           ========================================= */

        @media (max-width: 800px) {

            .container {

                width: calc(100% - 30px);

                margin-top: 20px;
            }

            .header {

                padding: 16px;

                flex-direction: column;

                align-items: flex-start;
            }

            .header h1 {

                font-size: 20px;
            }

            .mcq-details {

                grid-template-columns: 1fr;
            }

            .question-card {

                padding: 20px;
            }

            .user-card {

                padding: 20px;
            }
        }

    </style>

</head>


<body>


<!-- ==================================================
     HEADER
     ================================================== -->

<header class="header">

    <h1>
        Submitted Answers
    </h1>


    <a
            class="back-link"
            href="<%= request.getContextPath() %>/admin/submissions">

        ← Back to Submissions

    </a>

</header>


<!-- ==================================================
     MAIN
     ================================================== -->

<main class="container">


    <!-- =================================================
         USER INFORMATION
         ================================================= -->

    <section class="user-card">

        <h2>
            <%= userName %>
        </h2>

        <p>

            <strong>Email:</strong>

            <%= userEmail %>

        </p>

        <p>

            <strong>Total Answers:</strong>

            <%= answers != null ? answers.size() : 0 %>

        </p>

    </section>


    <!-- =================================================
         ANSWERS
         ================================================= -->

    <%

        if (answers != null &&
                !answers.isEmpty()) {

            int number = 1;


            for (SubmittedAnswer answer : answers) {

                String questionType =
                        answer.getQuestionType();

                String typeClass =
                        questionType.toLowerCase();

    %>


    <section class="question-card">


        <!-- Question Number -->

        <div class="question-number">

            Question <%= number %>

        </div>


        <!-- Question Type -->

        <span class="question-type <%= typeClass %>">

            <%= questionType %>

        </span>


        <!-- Question -->

        <div class="question-text">

            <%= answer.getQuestionText() %>

        </div>


        <!-- =================================================
             MCQ
             ================================================= -->

        <%

            if ("MCQ".equals(questionType)) {

        %>


        <div class="mcq-details">


            <div class="mcq-item">

                <strong>
                    Selected Option
                </strong>

                <%= answer.getSelectedOption() %>

            </div>


            <div class="mcq-item">

                <strong>
                    Correct Option
                </strong>

                <%= answer.getCorrectOption() %>

            </div>


        </div>


        <%

            if ("CORRECT".equals(
                    answer.getAnswerStatus())) {

        %>


        <span class="correct-result">

            Correct

        </span>


        <%

            } else {

        %>


        <span class="incorrect-result">

            Incorrect

        </span>


        <%

            }

        %>


        <!-- =================================================
             THEORY
             ================================================= -->

        <%

            } else if ("THEORY".equals(questionType)) {

        %>


        <div class="answer-box">

            <span class="answer-label">
                User Answer
            </span>

            <%= answer.getAnswerText() %>

        </div>


        <div class="review-box">


            <h3>
                Admin Review
            </h3>


            <p>

                Current status:

                <span class="current-status">

                    <%= answer.getAnswerStatus() %>

                </span>

            </p>


            <form
                    class="review-form"
                    action="<%= request.getContextPath() %>/admin/review"
                    method="post">


                <input
                        type="hidden"
                        name="userId"
                        value="<%= request.getParameter("userId") %>"
                >


                <input
                        type="hidden"
                        name="questionId"
                        value="<%= answer.getQuestionId() %>"
                >


                <label for="status_<%= answer.getQuestionId() %>">

                    Review Status

                </label>


                <select
                        id="status_<%= answer.getQuestionId() %>"
                        name="status"
                        required>


                    <option value="">
                        Select Review Status
                    </option>


                    <option
                            value="CORRECT"
                            <%= "CORRECT".equals(
                                    answer.getAnswerStatus())
                                    ? "selected"
                                    : "" %>>

                        Correct

                    </option>


                    <option
                            value="PARTIALLY_CORRECT"
                            <%= "PARTIALLY_CORRECT".equals(
                                    answer.getAnswerStatus())
                                    ? "selected"
                                    : "" %>>

                        Partially Correct

                    </option>


                    <option
                            value="INCORRECT"
                            <%= "INCORRECT".equals(
                                    answer.getAnswerStatus())
                                    ? "selected"
                                    : "" %>>

                        Incorrect

                    </option>

                </select>


                <label for="comment_<%= answer.getQuestionId() %>">

                    Admin Comment

                </label>


                <textarea
                        id="comment_<%= answer.getQuestionId() %>"
                        name="adminComment"
                        placeholder="Enter feedback for the user..."><%= answer.getAdminComment() != null
                        ? answer.getAdminComment()
                        : "" %></textarea>


                <button
                        type="submit"
                        class="save-review-btn">

                    Save Review

                </button>


            </form>


            <%

                if (answer.getAdminComment() != null &&
                        !answer.getAdminComment().isBlank()) {

            %>


            <div class="admin-comment">

                <strong>
                    Existing Admin Comment
                </strong>

                <br><br>

                <%= answer.getAdminComment() %>

            </div>


            <%

                }

            %>


        </div>


        <!-- =================================================
             CODING
             ================================================= -->

        <%

            } else if ("CODING".equals(questionType)) {

        %>


        <div class="answer-box code-answer">

            <%= answer.getAnswerText() %>

        </div>


        <div class="review-box">


            <h3>
                Admin Review
            </h3>


            <p>

                Current status:

                <span class="current-status">

                    <%= answer.getAnswerStatus() %>

                </span>

            </p>


            <form
                    class="review-form"
                    action="<%= request.getContextPath() %>/admin/review"
                    method="post">


                <input
                        type="hidden"
                        name="userId"
                        value="<%= request.getParameter("userId") %>"
                >


                <input
                        type="hidden"
                        name="questionId"
                        value="<%= answer.getQuestionId() %>"
                >


                <label for="code_status_<%= answer.getQuestionId() %>">

                    Review Status

                </label>


                <select
                        id="code_status_<%= answer.getQuestionId() %>"
                        name="status"
                        required>


                    <option value="">
                        Select Review Status
                    </option>


                    <option
                            value="CORRECT"
                            <%= "CORRECT".equals(
                                    answer.getAnswerStatus())
                                    ? "selected"
                                    : "" %>>

                        Correct

                    </option>


                    <option
                            value="PARTIALLY_CORRECT"
                            <%= "PARTIALLY_CORRECT".equals(
                                    answer.getAnswerStatus())
                                    ? "selected"
                                    : "" %>>

                        Partially Correct

                    </option>


                    <option
                            value="INCORRECT"
                            <%= "INCORRECT".equals(
                                    answer.getAnswerStatus())
                                    ? "selected"
                                    : "" %>>

                        Incorrect

                    </option>

                </select>


                <label for="code_comment_<%= answer.getQuestionId() %>">

                    Admin Comment

                </label>


                <textarea
                        id="code_comment_<%= answer.getQuestionId() %>"
                        name="adminComment"
                        placeholder="Enter feedback for the user..."><%= answer.getAdminComment() != null
                        ? answer.getAdminComment()
                        : "" %></textarea>


                <button
                        type="submit"
                        class="save-review-btn">

                    Save Review

                </button>


            </form>


            <%

                if (answer.getAdminComment() != null &&
                        !answer.getAdminComment().isBlank()) {

            %>


            <div class="admin-comment">

                <strong>
                    Existing Admin Comment
                </strong>

                <br><br>

                <%= answer.getAdminComment() %>

            </div>


            <%

                }

            %>


        </div>


        <%

            }


            number++;

        }

    } else {

    %>


    <div class="empty">

        No submitted answers found.

    </div>


    <%

        }

    %>


</main>


</body>

</html>