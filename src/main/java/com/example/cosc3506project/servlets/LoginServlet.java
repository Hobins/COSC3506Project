package com.example.cosc3506project.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "123";
    private static final String USER_USER = "user";
    private static final String USER_PASS = "123";
    private static final String CONTRACTOR_USER = "contractor";
    private static final String CONTRACTOR_PASS = "123";

    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Login attempt for user: " + request.getParameter("username")); // Log username
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (ADMIN_USER.equals(username) && ADMIN_PASS.equals(password)) {
            response.getWriter().write("success");
        } else if (USER_USER.equals(username) && USER_PASS.equals(password)) {
            response.getWriter().write("user");
        } else if (CONTRACTOR_USER.equals(username) && CONTRACTOR_PASS.equals(password)) {
            response.getWriter().write("contractor");
        }
        else {
            response.getWriter().write("failure");
        }
    }
}
