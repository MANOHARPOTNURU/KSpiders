<%
    Integer submittedCount =
            (Integer) request.getAttribute("submittedCount");
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Assessment Submitted</title>


    <style>

        body {

            margin: 0;

            font-family: Arial, sans-serif;

            background: #f4f6f9;

            min-height: 100vh;

            display: flex;

            justify-content: center;

            align-items: center;
        }


        .card {

            background: white;

            width: 500px;

            padding: 45px;

            text-align: center;

            border-radius: 12px;

            box-shadow:
                    0 5px 20px
                    rgba(0,0,0,0.10);
        }


        .success-icon {

            font-size: 55px;

            margin-bottom: 15px;
        }


        h1 {

            color: #166534;

            margin-bottom: 10px;
        }


        p {

            color: #6b7280;

            line-height: 1.6;
        }


        .count {

            font-size: 18px;

            font-weight: bold;

            color: #1f2937;

            margin: 20px 0;
        }


        .logout-btn {

            display: inline-block;

            margin-top: 15px;

            padding: 11px 20px;

            background: #2563eb;

            color: white;

            text-decoration: none;

            border-radius: 6px;
        }


        .logout-btn:hover {

            background: #1d4ed8;
        }

    </style>

</head>


<body>


<div class="card">


    <div class="success-icon">
        ✅
    </div>


    <h1>
        Assessment Submitted
    </h1>


    <p>
        Your assessment has been submitted successfully.
    </p>


    <div class="count">

        Answers submitted:
        <%= submittedCount %>

    </div>


    <p>
        Your submission is now available for review.
    </p>


    <a
            href="<%= request.getContextPath() %>/logout"
            class="logout-btn">

        Logout

    </a>


</div>


</body>

</html>
