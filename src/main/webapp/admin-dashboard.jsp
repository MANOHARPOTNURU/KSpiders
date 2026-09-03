<%@ page import="java.util.List" %>
<%@ page import="com.assessment.model.User" %>

<%
    List<User> users =
            (List<User>) request.getAttribute("users");

    List<User> approvedUsers =
            (List<User>) request.getAttribute("approvedUsers");

    List<String[]> questionSets =
            (List<String[]>) request.getAttribute("questionSets");

    int totalUsers = 0;
    int pendingUsers = 0;
    int approvedCount = 0;
    int rejectedUsers = 0;

    if (users != null) {

        totalUsers = users.size();

        for (User user : users) {

            if ("PENDING".equals(user.getApprovalStatus())) {
                pendingUsers++;
            }

            if ("APPROVED".equals(user.getApprovalStatus())) {
                approvedCount++;
            }

            if ("REJECTED".equals(user.getApprovalStatus())) {
                rejectedUsers++;
            }
        }
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Admin Dashboard - Assessment System</title>

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

        /* ============================
           HEADER
           ============================ */

        .header {

            background: #1f2937;
            color: white;

            padding: 18px 35px;

            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header-left {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .header h1 {
            margin: 0;
            font-size: 23px;
        }

        .header-link {

            background: #2563eb;
            color: white;

            text-decoration: none;

            padding: 10px 16px;

            border-radius: 6px;

            font-size: 14px;
        }

        .header-link:hover {
            background: #1d4ed8;
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


        /* ============================
           MAIN
           ============================ */

        .container {
            padding: 30px;
        }

        .welcome {
            margin-bottom: 25px;
        }

        .welcome h2 {
            margin-bottom: 6px;
            font-size: 28px;
        }

        .welcome p {
            color: #6b7280;
        }


        /* ============================
           SUMMARY CARDS
           ============================ */

        .cards {

            display: grid;

            grid-template-columns:
                    repeat(4, 1fr);

            gap: 20px;

            margin-bottom: 30px;
        }

        .card {

            background: white;

            padding: 22px;

            border-radius: 10px;

            box-shadow:
                    0 2px 8px
                    rgba(0, 0, 0, 0.08);
        }

        .card h3 {

            margin-top: 0;

            color: #374151;

            font-size: 17px;
        }

        .number {

            font-size: 32px;

            font-weight: bold;

            color: #2563eb;
        }


        /* ============================
           SECTION
           ============================ */

        .section {

            background: white;

            padding: 25px;

            border-radius: 10px;

            box-shadow:
                    0 2px 8px
                    rgba(0, 0, 0, 0.08);

            margin-bottom: 30px;
        }

        .section h2 {
            margin-top: 0;
        }

        .section-description {
            color: #6b7280;
            margin-bottom: 20px;
        }


        /* ============================
           TABLE
           ============================ */

        .table-container {
            overflow-x: auto;
        }

        table {

            width: 100%;

            border-collapse: collapse;

            margin-top: 15px;
        }

        th {

            background: #f3f4f6;

            padding: 14px;

            text-align: left;

            font-size: 14px;
        }

        td {

            padding: 14px;

            border-bottom:
                    1px solid #e5e7eb;

            vertical-align: middle;
        }

        tr:hover {
            background: #f9fafb;
        }


        /* ============================
           STATUS BADGES
           ============================ */

        .status {

            display: inline-block;

            padding: 6px 12px;

            border-radius: 20px;

            font-size: 12px;

            font-weight: bold;
        }

        .approved {
            background: #dcfce7;
            color: #166534;
        }

        .pending {
            background: #fef3c7;
            color: #92400e;
        }

        .rejected {
            background: #fee2e2;
            color: #991b1b;
        }


        /* ============================
           APPROVE / REJECT
           ============================ */

        .approve-btn {

            background: #22c55e;

            color: white;

            border: none;

            padding: 8px 14px;

            border-radius: 6px;

            cursor: pointer;

            margin-right: 5px;
        }

        .approve-btn:hover {
            background: #16a34a;
        }

        .reject-btn {

            background: #ef4444;

            color: white;

            border: none;

            padding: 8px 14px;

            border-radius: 6px;

            cursor: pointer;
        }

        .reject-btn:hover {
            background: #dc2626;
        }


        /* ============================
           ACCESS STATUS
           ============================ */

        .admin-access {

            color: #1d4ed8;

            font-weight: bold;
        }

        .access-granted {

            color: #166534;

            font-weight: bold;
        }

        .access-denied {

            color: #991b1b;

            font-weight: bold;
        }


        /* ============================
           ASSIGNMENT
           ============================ */

        .assignment-form {

            display: flex;

            gap: 10px;

            align-items: center;
        }

        .assignment-form select {

            padding: 9px 12px;

            border:
                    1px solid #d1d5db;

            border-radius: 6px;

            min-width: 220px;

            background: white;
        }

        .assign-btn {

            background: #2563eb;

            color: white;

            border: none;

            padding: 9px 16px;

            border-radius: 6px;

            cursor: pointer;
        }

        .assign-btn:hover {
            background: #1d4ed8;
        }


        /* ============================
           VIEW ANSWERS
           ============================ */

        .view-btn {

            background: #7c3aed;

            color: white;

            text-decoration: none;

            padding: 8px 14px;

            border-radius: 6px;

            display: inline-block;

            font-size: 13px;
        }

        .view-btn:hover {
            background: #6d28d9;
        }


        /* ============================
           EMPTY
           ============================ */

        .empty {

            text-align: center;

            padding: 30px;

            color: #6b7280;
        }


        /* ============================
           RESPONSIVE
           ============================ */

        @media (max-width: 1100px) {

            .cards {
                grid-template-columns:
                        repeat(2, 1fr);
            }
        }

        @media (max-width: 700px) {

            .cards {
                grid-template-columns: 1fr;
            }

            .container {
                padding: 15px;
            }

            .header {

                flex-direction: column;

                align-items: flex-start;

                gap: 12px;
            }

            .header-left {

                flex-direction: column;

                align-items: flex-start;
            }

            .assignment-form {

                flex-direction: column;

                align-items: flex-start;
            }
        }

    </style>

</head>


<body>


<!-- =====================================================
     HEADER
     ===================================================== -->

<header class="header">

    <div class="header-left">

        <h1>
            Assessment System - Admin
        </h1>

        <!-- NEW: Submitted Assessments -->
        <a
                href="<%= request.getContextPath() %>/admin/submissions"
                class="header-link">

            Submitted Assessments

        </a>

    </div>


    <a
            href="<%= request.getContextPath() %>/logout"
            class="logout-btn">

        Logout

    </a>

</header>


<div class="container">


    <!-- =================================================
         WELCOME
         ================================================= -->

    <div class="welcome">

        <h2>
            Admin Dashboard
        </h2>

        <p>
            Manage users, approvals and assessment assignments.
        </p>

    </div>


    <!-- =================================================
         SUMMARY
         ================================================= -->

    <div class="cards">


        <div class="card">

            <h3>
                Total Users
            </h3>

            <div class="number">
                <%= totalUsers %>
            </div>

        </div>


        <div class="card">

            <h3>
                Pending Approval
            </h3>

            <div class="number">
                <%= pendingUsers %>
            </div>

        </div>


        <div class="card">

            <h3>
                Approved Users
            </h3>

            <div class="number">
                <%= approvedCount %>
            </div>

        </div>


        <div class="card">

            <h3>
                Rejected Users
            </h3>

            <div class="number">
                <%= rejectedUsers %>
            </div>

        </div>

    </div>


    <!-- =================================================
         USER MANAGEMENT
         ================================================= -->

    <div class="section">

        <h2>
            User Management
        </h2>

        <p class="section-description">

            Manage user approval and assessment access.

        </p>


        <div class="table-container">

            <table>

                <thead>

                <tr>

                    <th>ID</th>

                    <th>Name</th>

                    <th>Email</th>

                    <th>Role</th>

                    <th>Approval Status</th>

                    <th>Assessment Access</th>

                </tr>

                </thead>


                <tbody>


                <%

                    if (users != null &&
                        !users.isEmpty()) {

                        for (User user : users) {

                %>


                <tr>


                    <td>
                        <%= user.getId() %>
                    </td>


                    <td>
                        <%= user.getName() %>
                    </td>


                    <td>
                        <%= user.getEmail() %>
                    </td>


                    <td>
                        <%= user.getRole() %>
                    </td>


                    <td>

                        <span class="status
                            <%= user.getApprovalStatus()
                                    .toLowerCase() %>">

                            <%= user.getApprovalStatus() %>

                        </span>

                    </td>


                    <td>


                        <%

                            if ("ADMIN".equals(
                                    user.getRole())) {

                        %>

                        <span class="admin-access">
                            Admin Access
                        </span>

                        <%

                            } else if ("PENDING".equals(
                                    user.getApprovalStatus())) {

                        %>

                        <!-- APPROVE -->

                        <form

                                action="<%= request.getContextPath() %>/admin/approval"

                                method="post"

                                style="display:inline;">

                            <input

                                    type="hidden"

                                    name="userId"

                                    value="<%= user.getId() %>"
                            >

                            <input

                                    type="hidden"

                                    name="action"

                                    value="approve"
                            >

                            <button

                                    type="submit"

                                    class="approve-btn">

                                Approve

                            </button>

                        </form>


                        <!-- REJECT -->

                        <form

                                action="<%= request.getContextPath() %>/admin/approval"

                                method="post"

                                style="display:inline;">

                            <input

                                    type="hidden"

                                    name="userId"

                                    value="<%= user.getId() %>"
                            >

                            <input

                                    type="hidden"

                                    name="action"

                                    value="reject"
                            >

                            <button

                                    type="submit"

                                    class="reject-btn">

                                Reject

                            </button>

                        </form>


                        <%

                            } else if ("APPROVED".equals(
                                    user.getApprovalStatus())) {

                        %>

                        <span class="access-granted">

                            Assessment Access Granted

                        </span>


                        <%

                            } else {

                        %>

                        <span class="access-denied">

                            Assessment Access Denied

                        </span>


                        <%

                            }

                        %>


                    </td>

                </tr>


                <%

                        }

                    } else {

                %>


                <tr>

                    <td
                            colspan="6"
                            class="empty">

                        No users found.

                    </td>

                </tr>


                <%

                    }

                %>


                </tbody>

            </table>

        </div>

    </div>


    <!-- =================================================
         QUESTION SET ASSIGNMENT
         ================================================= -->

    <div class="section">

        <h2>
            Assessment Set Assignment
        </h2>

        <p class="section-description">

            Assign one question set to each approved user.

        </p>


        <div class="table-container">

            <table>

                <thead>

                <tr>

                    <th>User ID</th>

                    <th>Name</th>

                    <th>Email</th>

                    <th>Assign Question Set</th>

                </tr>

                </thead>


                <tbody>


                <%

                    if (approvedUsers != null &&
                        !approvedUsers.isEmpty()) {

                        for (User user :
                                approvedUsers) {

                %>


                <tr>

                    <td>
                        <%= user.getId() %>
                    </td>

                    <td>
                        <%= user.getName() %>
                    </td>

                    <td>
                        <%= user.getEmail() %>
                    </td>


                    <td>

                        <form

                                class="assignment-form"

                                action="<%= request.getContextPath() %>/admin/assignment"

                                method="post">

                            <input
                                    type="hidden"
                                    name="userId"
                                    value="<%= user.getId() %>"
                            >


                            <select
                                    name="questionSetId"
                                    required>

                                <option value="">
                                    Select Question Set
                                </option>


                                <%

                                    if (questionSets != null) {

                                        for (String[] set :
                                                questionSets) {

                                %>

                                <option
                                        value="<%= set[0] %>">

                                    <%= set[1] %>

                                </option>

                                <%

                                        }

                                    }

                                %>

                            </select>


                            <button
                                    type="submit"
                                    class="assign-btn">

                                Assign

                            </button>

                        </form>

                    </td>

                </tr>


                <%

                        }

                    } else {

                %>


                <tr>

                    <td
                            colspan="4"
                            class="empty">

                        No approved users available.

                    </td>

                </tr>


                <%

                    }

                %>


                </tbody>

            </table>

        </div>

    </div>


</div>


</body>

</html>