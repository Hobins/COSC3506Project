package com.example.cosc3506project.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "123";
    private static final String USER_USERNAME = "user";
    private static final String USER_PASSWORD = "123";

    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Login attempt for user: " + request.getParameter("username")); // Log username
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (ADMIN_USER.equals(username) && ADMIN_PASS.equals(password)) {
            response.getWriter().write("success");
        } else if (USER_USERNAME.equals(username) && USER_PASSWORD.equals(password)) {
            response.getWriter().write("user");
        } else {
            response.getWriter().write("failure");
        }
    }
}
