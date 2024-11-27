package com.example.cosc3506project;

import com.example.cosc3506project.screens.LoginScreen;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainScreen extends Application {

    private BorderPane mainLayout;

    @Override
    public void start(Stage stage) throws IOException {
        // Create the main layout
        mainLayout = new BorderPane();
        // Set the title of the Stage
        stage.setTitle("Hello Application");

        // Set the initial screen content (e.g., Login screen)
        mainLayout.setCenter(LoginScreen.getScreen(this));

        // Create and display the scene
        Scene scene = new Scene(mainLayout, 1000, 800);
        stage.setTitle("Hello Application");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}