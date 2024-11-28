package com.example.cosc3506project.screens;
//user page code
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class ServiceRequestForm  {

    public BorderPane getUserScreen(){

        // Main layout
        BorderPane root = new BorderPane();

        // Left panel (User & Main sections)
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setPrefWidth(200);
        leftPanel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px;");

        Label userLabel = new Label("Users");
        ListView<String> userMenu = new ListView<>();
        userMenu.getItems().addAll("Dashboard", "Request Services", "Track Progress", "Manage Payments", "Edit Profiles");

        Label mainLabel = new Label("Main");
        ListView<String> mainMenu = new ListView<>();
        mainMenu.getItems().addAll("New Service", "Service Status", "Service History");

        leftPanel.getChildren().addAll(userLabel, userMenu, mainLabel, mainMenu);

        // Center panel (Form)
        VBox formPanel = new VBox(10);
        formPanel.setPadding(new Insets(20));
        formPanel.setAlignment(Pos.TOP_LEFT);

        Label formTitle = new Label("New Service Request");
        formTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        Label serviceTypeLabel = new Label("Service Type:");
        ComboBox<String> serviceTypeField = new ComboBox<>();
        serviceTypeField.getItems().addAll("Option 1", "Option 2", "Option 3");

        Label contactLabel = new Label("Contact:");
        TextField contactField = new TextField();

        Label dateLabel = new Label("Date:");
        DatePicker dateField = new DatePicker();

        Label descriptionLabel = new Label("Description:");
        TextArea descriptionField = new TextArea();
        descriptionField.setPrefRowCount(5);

        formGrid.add(serviceTypeLabel, 0, 0);
        formGrid.add(serviceTypeField, 1, 0);
        formGrid.add(contactLabel, 0, 1);
        formGrid.add(contactField, 1, 1);
        formGrid.add(dateLabel, 0, 2);
        formGrid.add(dateField, 1, 2);
        formGrid.add(descriptionLabel, 0, 3);
        formGrid.add(descriptionField, 1, 3);

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        Button submitButton = new Button("Submit");
        Button clearButton = new Button("Clear");

        buttonBox.getChildren().addAll(submitButton, clearButton);

        // Add action to buttons
        submitButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Submission Successful");
            alert.setHeaderText(null);
            alert.setContentText("Service request submitted!");
            alert.showAndWait();
        });

        clearButton.setOnAction(e -> {
            serviceTypeField.setValue(null);
            contactField.clear();
            dateField.setValue(null);
            descriptionField.clear();
        });

        root.setLeft(leftPanel);
        root.setCenter(formPanel);

        return root;
    }

}
