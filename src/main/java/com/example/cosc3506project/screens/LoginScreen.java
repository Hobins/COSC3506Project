package com.example.cosc3506project.screens;

import com.example.cosc3506project.MainScreen;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginScreen {

    private static final String CORRECT_USERNAME = "admin@test.com";
    private static final String CORRECT_PASSWORD = "password123";

    public static VBox getScreen(MainScreen app){
        Label companyLabel = new Label("Management Company Name");
        Label usernameLabel = new Label("Email");
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

            if(userInput.equals(CORRECT_USERNAME) && passInput.equals(CORRECT_PASSWORD)){
                resultLabel.setText("Login Successful!");
            } else{
                resultLabel.setText("Login failed. Please check your credentials.");
            }

        });

        createAcc.setOnAction(e -> {
            resultLabel.setText("Unable to create account. Please contact admin.");
        });

        forgotPassword.setOnAction(e -> {
            resultLabel.setText("Password reset sent to email!");
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(companyLabel, usernameLabel, usernameField, passwordLabel, passwordField, forgotPassword, loginButton, createAcc, resultLabel);
        layout.setPadding(new Insets(100, 200, 200, 200));
        return layout;
    }
}