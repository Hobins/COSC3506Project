package com.example.cosc3506project.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class ServiceRequestForm  {

    BorderPane root = new BorderPane();  // Root layout for the screen
    VBox leftPanel = new VBox(10);  // The left panel menu

    public ServiceRequestForm() {
        setupLeftPanel();
    }

    private void setupLeftPanel(){
        // Left panel (User & Main sections)
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

        userMenu.setOnMouseClicked(e -> {
            String selectedItem = userMenu.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                switch (selectedItem) {
                    case "Request Services":
                        showRequestServicesScreen();
                        break;
                    case "Track Progress":
                        showTrackProgressScreen();
                        break;
                    case "Manage Payments":
                        showManagePaymentsScreen();
                        break;
                    case "Edit Profiles":
                        showEditProfilesScreen();
                        break;
                    default:
                        break;
                }
            }
        });

        mainMenu.setOnMouseClicked(e -> {
            String selectedItem = mainMenu.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                switch (selectedItem) {
                    case "New Service":
                        showNewServiceScreen();
                        break;
                    case "Service Status":
                        showServiceStatusScreen();
                        break;
                    case "Service History":
                        showServiceHistoryScreen();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void showTrackProgressScreen() {
        VBox trackProgressPanel = new VBox(10);
        trackProgressPanel.setPadding(new Insets(20));
        Label trackProgressTitle = new Label("Track Progress");
        trackProgressTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        trackProgressPanel.getChildren().add(trackProgressTitle);

        root.setCenter(trackProgressPanel);
    }

    private void showRequestServicesScreen() {
        VBox requestServicesPanel = new VBox(10);
        requestServicesPanel.setPadding(new Insets(20));
        Label requestServicesTitle = new Label("Request Services");
        requestServicesTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        requestServicesPanel.getChildren().add(requestServicesTitle);

        root.setCenter(requestServicesPanel);

    }

    private void showManagePaymentsScreen() {
        VBox managePaymentsPanel = new VBox(10);
        managePaymentsPanel.setPadding(new Insets(20));
        Label managePaymentsTitle = new Label("Manage Payments");
        managePaymentsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        managePaymentsPanel.getChildren().add(managePaymentsTitle);

        root.setCenter(managePaymentsPanel);
    }

    private void showEditProfilesScreen() {
        VBox editProfilesPanel = new VBox(10);
        editProfilesPanel.setPadding(new Insets(20));
        Label editProfilesTitle = new Label("Edit Profiles");
        editProfilesTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        editProfilesPanel.getChildren().add(editProfilesTitle);

        root.setCenter(editProfilesPanel);
    }

    private void showNewServiceScreen() {
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

        formPanel.getChildren().addAll(formTitle, formGrid, buttonBox);

    }

    private void showServiceStatusScreen() {
        VBox serviceStatusPanel = new VBox(10);
        serviceStatusPanel.setPadding(new Insets(20));
        Label serviceStatusTitle = new Label("Service Status");
        serviceStatusTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        serviceStatusPanel.getChildren().add(serviceStatusTitle);

        root.setCenter(serviceStatusPanel);
    }

    private void showServiceHistoryScreen() {
        VBox serviceHistoryPanel = new VBox(10);
        serviceHistoryPanel.setPadding(new Insets(20));
        Label serviceHistoryTitle = new Label("Service History");
        serviceHistoryTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        serviceHistoryPanel.getChildren().add(serviceHistoryTitle);

        root.setCenter(serviceHistoryPanel);
    }

    public BorderPane getUserScreen(){
        root.setLeft(leftPanel);

        return root;
    }

}
