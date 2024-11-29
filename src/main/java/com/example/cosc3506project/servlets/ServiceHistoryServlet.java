package com.example.cosc3506project.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/service-history")
public class ServiceHistoryServlet extends HttpServlet {

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
        services.add(new Services("Plumbing", "2021-10-01", "Leaky faucet", "Completed"));
        services.add(new Services("Electrical", "2021-10-02", "Broken light switch", "Completed"));
        services.add(new Services("Plumbing", "2021-10-03", "Clogged drain", "Completed"));

        JSONArray servicesArray = new JSONArray();
        for (Services service : services) {
            JSONObject serviceObject = new JSONObject();
            serviceObject.put("serviceType", service.serviceType);
            serviceObject.put("date", service.date);
            serviceObject.put("description", service.description);
            serviceObject.put("status", service.status);
            servicesArray.put(serviceObject);
        }

        resp.getWriter().write(servicesArray.toString());

    }

}
