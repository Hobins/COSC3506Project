package com.example.cosc3506project;

import com.example.cosc3506project.servlets.ActiveProjectServlet;
import com.example.cosc3506project.servlets.LoginServlet;
import com.example.cosc3506project.servlets.ServiceHistoryServlet;
import com.example.cosc3506project.servlets.ServiceStatusServlet;
import jakarta.servlet.Servlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class WebServer {

    public static void main(String[] args) throws Exception {
        // Create a Jetty server on port 8081
        Server server = new Server(8081);

        // Create a Servlet context
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // Add the servlet to the context
        context.addServlet(new ServletHolder((Servlet) new LoginServlet()), "/login");
        context.addServlet(new ServletHolder((Servlet) new ServiceHistoryServlet()), "/service-history");
        context.addServlet(new ServletHolder((Servlet) new ServiceStatusServlet()), "/service-status");
        context.addServlet(new ServletHolder((Servlet) new ActiveProjectServlet()), "/active-project");

        // Set the server context
        server.setHandler(context);

        // Start the server
        server.start();
        server.join();
    }
}
