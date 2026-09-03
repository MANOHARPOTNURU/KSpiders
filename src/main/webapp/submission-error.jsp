<%
    String errorMessage =
            (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Submission Error</title>


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

            padding: 40px;

            text-align: center;

            border-radius: 12px;

            box-shadow:
                    0 5px 20px
                    rgba(0,0,0,0.10);
        }


        h1 {

            color: #991b1b;
        }


        .error {

            background: #fee2e2;

            color: #991b1b;

            padding: 15px;

            border-radius: 6px;

            margin: 20px 0;
        }


        a {

            display: inline-block;

            padding: 10px 18px;

            background: #2563eb;

            color: white;

            text-decoration: none;

            border-radius: 6px;
        }

    </style>

</head>


<body>


<div class="card">

    <h1>
        Assessment Submission Failed
    </h1>


    <div class="error">

        <%= errorMessage != null
                ? errorMessage
                : "An unexpected error occurred." %>

    </div>


    <a
            href="<%= request.getContextPath() %>/user/dashboard">

        Return to Assessment

    </a>

</div>


</body>

</html>
