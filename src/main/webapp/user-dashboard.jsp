<%@ page import="java.util.List" %>
<%@ page import="com.assessment.model.Question" %>

<%
    List<Question> questions =
            (List<Question>) request.getAttribute("questions");

    String message =
            (String) request.getAttribute("message");

    Integer questionSetId =
            (Integer) request.getAttribute("questionSetId");
%>


<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        User Assessment - Assessment System
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


        /* =====================================
           HEADER
           ===================================== */

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


        .logout-btn {

            background: #ef4444;

            color: white;

            text-decoration: none;

            padding: 10px 18px;

            border-radius: 6px;

            font-size: 14px;
        }


        .logout-btn:hover {

            background: #dc2626;
        }


        /* =====================================
           MAIN
           ===================================== */

        .container {

            width: 90%;

            max-width: 1000px;

            margin: 35px auto;
        }


        .title-card {

            background: white;

            padding: 25px;

            border-radius: 10px;

            margin-bottom: 25px;

            box-shadow:
                    0 2px 8px
                    rgba(0,0,0,0.08);
        }


        .title-card h2 {

            margin-top: 0;

            font-size: 28px;
        }


        .title-card p {

            color: #6b7280;
        }


        /* =====================================
           QUESTION
           ===================================== */

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

            font-size: 14px;

            font-weight: bold;

            color: #2563eb;

            margin-bottom: 10px;
        }


        .question-text {

            font-size: 18px;

            font-weight: bold;

            line-height: 1.5;

            margin-bottom: 20px;
        }


        /* =====================================
           MCQ
           ===================================== */

        .option {

            display: block;

            padding: 12px 15px;

            margin-bottom: 10px;

            border: 1px solid #d1d5db;

            border-radius: 7px;

            cursor: pointer;
        }


        .option:hover {

            background: #f3f4f6;
        }


        .option input {

            margin-right: 10px;
        }


        /* =====================================
           THEORY / CODING
           ===================================== */

        textarea {

            width: 100%;

            min-height: 180px;

            resize: vertical;

            padding: 14px;

            border:
                    1px solid #d1d5db;

            border-radius: 7px;

            font-family: Arial, sans-serif;

            font-size: 15px;
        }


        .code-answer {

            font-family:
                    Consolas,
                    "Courier New",
                    monospace;

            background: #111827;

            color: #f9fafb;
        }


        .type-badge {

            display: inline-block;

            padding: 5px 10px;

            border-radius: 15px;

            font-size: 12px;

            font-weight: bold;

            margin-bottom: 15px;
        }


        .mcq-badge {

            background: #dbeafe;

            color: #1d4ed8;
        }


        .theory-badge {

            background: #fef3c7;

            color: #92400e;
        }


        .coding-badge {

            background: #ede9fe;

            color: #6d28d9;
        }


        /* =====================================
           SUBMIT
           ===================================== */

        .submit-section {

            text-align: center;

            margin-top: 30px;

            margin-bottom: 50px;
        }


        .submit-btn {

            background: #2563eb;

            color: white;

            border: none;

            padding: 14px 30px;

            border-radius: 7px;

            font-size: 16px;

            font-weight: bold;

            cursor: pointer;
        }


        .submit-btn:hover {

            background: #1d4ed8;
        }


        /* =====================================
           NO ASSIGNMENT
           ===================================== */

        .message {

            background: white;

            padding: 30px;

            border-radius: 10px;

            text-align: center;

            box-shadow:
                    0 2px 8px
                    rgba(0,0,0,0.08);
        }

    </style>

</head>


<body>


<header class="header">

    <h1>
        Assessment System
    </h1>


    <a
            href="<%= request.getContextPath() %>/logout"
            class="logout-btn">

        Logout

    </a>

</header>


<div class="container">


    <div class="title-card">

        <h2>
            Java Programming Assessment
        </h2>

        <p>
            Answer all questions and submit your assessment.
        </p>

        <p>

            <strong>
                Total Questions:
            </strong>

            <%= questions != null ? questions.size() : 0 %>

        </p>

    </div>


    <%

        if (message != null) {

    %>


    <div class="message">

        <h3>
            Assessment Not Available
        </h3>

        <p>
            <%= message %>
        </p>

    </div>


    <%

        } else if (
                questions != null &&
                !questions.isEmpty()
        ) {

    %>


    <form
            action="<%= request.getContextPath() %>/assessment/submit"
            method="post">


        <input
                type="hidden"
                name="questionSetId"
                value="<%= questionSetId %>"
        >


        <%

            int questionNumber = 1;

            for (Question question : questions) {

        %>


        <div class="question-card">


            <div class="question-number">

                Question <%= questionNumber %>

            </div>


            <div>

                <%

                    if ("MCQ".equals(
                            question.getQuestionType())) {

                %>

                <span class="type-badge mcq-badge">
                    MCQ
                </span>

                <%

                    } else if ("THEORY".equals(
                            question.getQuestionType())) {

                %>

                <span class="type-badge theory-badge">
                    THEORY
                </span>

                <%

                    } else if ("CODING".equals(
                            question.getQuestionType())) {

                %>

                <span class="type-badge coding-badge">
                    CODING
                </span>

                <%

                    }

                %>

            </div>


            <div class="question-text">

                <%= question.getQuestionText() %>

            </div>


            <!-- =================================
                 MCQ
                 ================================= -->

            <%

                if ("MCQ".equals(
                        question.getQuestionType())) {

            %>


            <label class="option">

                <input
                        type="radio"
                        name="answer_<%= question.getId() %>"
                        value="A"
                >

                A.
                <%= question.getOptionA() %>

            </label>


            <label class="option">

                <input
                        type="radio"
                        name="answer_<%= question.getId() %>"
                        value="B"
                >

                B.
                <%= question.getOptionB() %>

            </label>


            <label class="option">

                <input
                        type="radio"
                        name="answer_<%= question.getId() %>"
                        value="C"
                >

                C.
                <%= question.getOptionC() %>

            </label>


            <label class="option">

                <input
                        type="radio"
                        name="answer_<%= question.getId() %>"
                        value="D"
                >

                D.
                <%= question.getOptionD() %>

            </label>


            <%

                }

                // =================================
                // THEORY
                // =================================

                else if ("THEORY".equals(
                        question.getQuestionType())) {

            %>


            <textarea
                    name="answer_<%= question.getId() %>"
                    placeholder="Write your answer here..."
                    required></textarea>


            <%

                }

                // =================================
                // CODING
                // =================================

                else if ("CODING".equals(
                        question.getQuestionType())) {

            %>


            <textarea
                    name="answer_<%= question.getId() %>"
                    class="code-answer"
                    placeholder="Write your Java code here..."
                    required></textarea>


            <%

                }

            %>


        </div>


        <%

                questionNumber++;

            }

        %>


        <div class="submit-section">

            <button
                    type="submit"
                    class="submit-btn">

                Submit Assessment

            </button>

        </div>


    </form>


    <%

        } else {

    %>


    <div class="message">

        <h3>
            No Questions Found
        </h3>

        <p>
            Your assigned assessment does not contain any questions.
        </p>

    </div>


    <%

        }

    %>


</div>


</body>

</html>