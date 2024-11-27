package com.example.cosc3506project.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    private static final String CORRECT_USERNAME = "admin";
    private static final String CORRECT_PASSWORD = "123";

    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Login attempt for user: " + request.getParameter("username")); // Log username
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (CORRECT_USERNAME.equals(username) && CORRECT_PASSWORD.equals(password)) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("failure");
        }
    }
}
