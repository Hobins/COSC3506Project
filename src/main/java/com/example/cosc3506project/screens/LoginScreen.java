package com.example.cosc3506project.screens;

import com.example.cosc3506project.MainScreen;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginScreen {


    /**
     * Checks to see if the users is an admin, user, or contractor
     * if the user is an admin, go to the admin page
     * if the user is a user, go to the user page
     * if the user is a contractor, go to the contractor page
     *
     */
    public static VBox getScreen(MainScreen app){
        Label companyLabel = new Label("Construction Project Management System");
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        Label resultLabel = new Label();

        Button loginButton = new Button("Login");
        Button createAcc = new Button("Create an Account");
        Button forgotPassword = new Button("Forgot Password?");

        loginButton.setOnAction(e -> {
            String userInput = usernameField.getText();
            String passInput = passwordField.getText();


            try {
                String result = sendLogin(userInput, passInput);
                if ("admin".equals(result)) {
                    resultLabel.setText("Login Successful!");

                    app.setScreen(new AdminPage().getAdminScreen());

                } else if ("user".equals(result)) {
                    resultLabel.setText("Login Successful!");

                    app.setScreen(new UserPage().getUserScreen());
                } else if("contractor".equals(result)){
                    resultLabel.setText("Login Successful!");

                    app.setScreen(new ContractorPage().getContractorScreen());
                }
                else if ("failure".equals(result)) { // Handle specific failure response
                    resultLabel.setText("Login failed. Please check your credentials.");
                } else {
                    resultLabel.setText("Unexpected response: " + result);
                }
            } catch (Exception ex) {
                resultLabel.setText("Error connecting to server.");
                ex.printStackTrace();
            }
        });

        createAcc.setOnAction(e -> {
            resultLabel.setText("Unable to create account. Please contact admin.");
        });

        forgotPassword.setOnAction(e -> {
            resultLabel.setText("Password reset sent to email!");
        });

        VBox layout = new VBox(10);
        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(loginButton, createAcc);
        layout.getChildren().addAll(companyLabel, usernameLabel, usernameField, passwordLabel, passwordField, forgotPassword, buttons, resultLabel);
        layout.setPadding(new Insets(250, 200, 200, 200));
        return layout;
    }

    /**
     * Sends a login request to the LoginServlet.
     * This is called from the MainScreen class after the user clicks the login button.
     *
     * @param username The username to send
     * @param password The password to send
     * @return The response from the server
     */
    private static String sendLogin(String username, String password) throws Exception {

        URL url = new URL("http://localhost:8081/login");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String input = "username=" + username + "&password=" + password;
        try(OutputStream os = connection.getOutputStream()) {
            os.write(input.getBytes());
            os.flush();
        }

        Scanner scanner = new Scanner(connection.getInputStream());
        String response = scanner.hasNext() ? scanner.nextLine() : "";
        scanner.close();

        return response;
    }
}