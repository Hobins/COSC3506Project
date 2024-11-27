package com.example.cosc3506project.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    private static final String CORRECT_USERNAME = "admin@test.com";
    private static final String CORRECT_PASSWORD = "123";

    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Login attempt for user: " + request.getParameter("username")); // Log username
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (CORRECT_USERNAME.equals(username) && CORRECT_PASSWORD.equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // 

            //response.sendRedirect("profile");
        } else {
            response.getWriter().write("Invalid username or password");
        }
    }
}
