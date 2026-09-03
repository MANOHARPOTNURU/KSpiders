<%@ page import="java.util.List" %>
<%@ page import="com.assessment.model.SubmittedAnswer" %>

<%
    List<SubmittedAnswer> answers =
            (List<SubmittedAnswer>)
                    request.getAttribute("answers");

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


        body {

            margin: 0;

            font-family: Arial, sans-serif;

            background: #f4f6f9;

            color: #1f2937;
        }


        /* =================================
           HEADER
           ================================= */

        .header {

            background: #1f2937;

            color: white;

            padding: 18px 35px;

            display: flex;

            justify-content: space-between;

            align-items: center;
        }


        .header h1 {

            margin: 0;

            font-size: 23px;
        }


        .back-link {

            background: #2563eb;

            color: white;

            padding: 10px 16px;

            border-radius: 6px;

            text-decoration: none;
        }


        /* =================================
           CONTAINER
           ================================= */

        .container {

            width: 90%;

            max-width: 1000px;

            margin: 30px auto;
        }


        /* =================================
           USER INFO
           ================================= */

        .user-card {

            background: white;

            padding: 25px;

            border-radius: 10px;

            margin-bottom: 25px;

            box-shadow:
                    0 2px 8px
                    rgba(0,0,0,0.08);
        }


        .user-card h2 {

            margin-top: 0;
        }


        /* =================================
           QUESTION CARD
           ================================= */

        .question-card {

            background: white;

            padding: 25px;

            border-radius: 10px;

            margin-bottom: 20px;

            box-shadow:
                    0 2px 8px
                    rgba(0,0,0,0.08);
        }


        .question-number {

            color: #2563eb;

            font-weight: bold;

            margin-bottom: 8px;
        }


        .question-type {

            display: inline-block;

            padding: 5px 10px;

            border-radius: 15px;

            font-size: 12px;

            font-weight: bold;

            margin-bottom: 15px;
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


        .question-text {

            font-size: 17px;

            font-weight: bold;

            line-height: 1.5;

            margin-bottom: 20px;
        }


        /* =================================
           ANSWER
           ================================= */

        .answer-box {

            background: #f9fafb;

            border: 1px solid #e5e7eb;

            border-radius: 8px;

            padding: 16px;

            margin-bottom: 18px;

            white-space: pre-wrap;

            line-height: 1.6;
        }


        .code-answer {

            background: #111827;

            color: #f9fafb;

            font-family:
                    Consolas,
                    "Courier New",
                    monospace;
        }


        /* =================================
           AUTOMATIC RESULT
           ================================= */

        .correct-result {

            display: inline-block;

            background: #dcfce7;

            color: #166534;

            padding: 8px 14px;

            border-radius: 20px;

            font-weight: bold;

            margin-top: 8px;
        }


        .incorrect-result {

            display: inline-block;

            background: #fee2e2;

            color: #991b1b;

            padding: 8px 14px;

            border-radius: 20px;

            font-weight: bold;

            margin-top: 8px;
        }


        /* =================================
           REVIEW
           ================================= */

        .review-box {

            margin-top: 20px;

            padding: 20px;

            border: 1px solid #dbeafe;

            background: #f8fbff;

            border-radius: 8px;
        }


        .review-box h3 {

            margin-top: 0;

            color: #1e40af;
        }


        .review-box label {

            display: block;

            margin-bottom: 7px;

            font-weight: bold;
        }


        .review-box select {

            width: 100%;

            padding: 10px;

            margin-bottom: 15px;

            border: 1px solid #d1d5db;

            border-radius: 6px;

            background: white;

        }


        .review-box textarea {

            width: 100%;

            min-height: 100px;

            padding: 10px;

            resize: vertical;

            border: 1px solid #d1d5db;

            border-radius: 6px;

            margin-bottom: 15px;

            font-family: Arial, sans-serif;
        }


        .save-review-btn {

            background: #2563eb;

            color: white;

            border: none;

            padding: 10px 18px;

            border-radius: 6px;

            cursor: pointer;

            font-weight: bold;
        }


        .save-review-btn:hover {

            background: #1d4ed8;
        }


        .current-status {

            display: inline-block;

            padding: 6px 12px;

            border-radius: 20px;

            background: #fef3c7;

            color: #92400e;

            font-size: 12px;

            font-weight: bold;
        }


        .admin-comment {

            margin-top: 15px;

            background: #f3f4f6;

            padding: 12px;

            border-radius: 6px;

            white-space: pre-wrap;
        }

    </style>

</head>


<body>


<header class="header">

    <h1>
        Submitted Answers
    </h1>


    <a
            class="back-link"
            href="<%= request.getContextPath() %>/admin/submissions">

        Back to Submissions

    </a>

</header>


<div class="container">


    <!-- ==========================================
         USER INFORMATION
         ========================================== -->

    <div class="user-card">

        <h2>
            <%= userName %>
        </h2>

        <p>

            <strong>Email:</strong>

            <%= userEmail %>

        </p>

        <p>

            <strong>Total Answers:</strong>

            <%= answers != null
                    ? answers.size()
                    : 0 %>

        </p>

    </div>


    <!-- ==========================================
         ANSWERS
         ========================================== -->

    <%

        if (answers != null &&
                !answers.isEmpty()) {

            int number = 1;


            for (SubmittedAnswer answer :
                    answers) {

                String typeClass =
                        answer.getQuestionType()
                                .toLowerCase();

    %>


    <div class="question-card">


        <div class="question-number">

            Question <%= number %>

        </div>


        <span
                class="question-type <%= typeClass %>">

            <%= answer.getQuestionType() %>

        </span>


        <div class="question-text">

            <%= answer.getQuestionText() %>

        </div>


        <!-- ==================================================
             MCQ
             ================================================== -->

        <%

            if ("MCQ".equals(
                    answer.getQuestionType())) {

        %>


        <div class="answer-box">

            <strong>
                Selected Option:
            </strong>

            <%= answer.getSelectedOption() %>

            <br><br>

            <strong>
                Correct Option:
            </strong>

            <%= answer.getCorrectOption() %>

        </div>


        <%

            if ("CORRECT".equals(
                    answer.getAnswerStatus())) {

        %>


        <span class="correct-result">

            ✅ Correct

        </span>


        <%

            } else {

        %>


        <span class="incorrect-result">

            ❌ Incorrect

        </span>


        <%

            }

        %>


        <%

            }


            // =================================================
            // THEORY
            // =================================================

            else if ("THEORY".equals(
                    answer.getQuestionType())) {

        %>


        <div class="answer-box">

            <strong>
                User Answer:
            </strong>

            <br><br>

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


                <label>
                    Review Status
                </label>


                <select
                        name="status"
                        required>

                    <option
                            value="">
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


                <label>
                    Admin Comment
                </label>


                <textarea
                        name="adminComment"
                        placeholder="Enter your feedback..."><%= answer.getAdminComment() != null
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
                    Existing Admin Comment:
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


            // =================================================
            // CODING
            // =================================================

            else if ("CODING".equals(
                    answer.getQuestionType())) {

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


                <label>
                    Review Status
                </label>


                <select
                        name="status"
                        required>

                    <option
                            value="">
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


                <label>
                    Admin Comment
                </label>


                <textarea
                        name="adminComment"
                        placeholder="Enter your feedback..."><%= answer.getAdminComment() != null
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
                    Existing Admin Comment:
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


    <div class="user-card">

        No submitted answers found.

    </div>


    <%

        }

    %>


</div>


</body>

</html>