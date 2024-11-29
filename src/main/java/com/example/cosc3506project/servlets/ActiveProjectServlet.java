package com.example.cosc3506project.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/active-project")
public class ActiveProjectServlet extends HttpServlet {


    public static class Services {

        private String serviceType;
        private String date;
        private String description;
        private String status;

        public Services(String serviceType, String date, String description, String status) {
            this.serviceType = serviceType;
            this.date = date;
            this.description = description;
            this.status = status;

        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Services> services = new ArrayList<>();
        services.add(new Services("Plumbing", "09/06/2024", "Requested plumbing to be laid", "Approved"));
        services.add(new Services("Electrical", "10/03/2024", "Requested new wiring throughout property", "Approved"));
        services.add(new Services("House addition", "11/16/2024", "Requested house addition to be constructed", "Waiting for Approval"));
        services.add(new Services("Housing Unit", "11/22/2024", "Requested construction of property", "Waiting for Approval"));


        JSONArray jsonArray = new JSONArray(services);
        for (Services service : services) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("serviceType", service.serviceType);
            jsonObject.put("date", service.date);
            jsonObject.put("description", service.description);
            jsonObject.put("status", service.status);
        }

        resp.getWriter().write(jsonArray.toString());

    }
}