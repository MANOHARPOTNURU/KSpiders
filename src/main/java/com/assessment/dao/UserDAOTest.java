package com.assessment.dao;

import com.assessment.model.User;

public class UserDAOTest {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        User user = userDAO.authenticate(
                "admin@test.com",
                "admin123"
        );

        if (user != null) {
            System.out.println("Login test successful!");
            System.out.println("Name: " + user.getName());
            System.out.println("Role: " + user.getRole());
            System.out.println("Approval: " + user.getApprovalStatus());
        } else {
            System.out.println("Login test failed!");
        }
    }
}
