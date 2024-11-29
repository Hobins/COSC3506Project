package com.example.cosc3506project.screens;


import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ContractorPage {

    private ProjectManagementSystem.User currentUser;


    BorderPane root = new BorderPane();  // Root layout for the screen
    VBox leftPanel = new VBox(10);  // The left panel menu



    // Constructor to initialize the left panel
    public ContractorPage() {
        this.currentUser = new ProjectManagementSystem.User("2350529", "Ryan", "705-382-3941", "Active", "ryan123@gmail.ca");
        setupLeftPanel();
    }

    // Setup the left panel once
    private void setupLeftPanel() {
        // Left panel (Admin menu)
        leftPanel.setPadding(new Insets(10));
        leftPanel.setPrefWidth(200);
        leftPanel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px;");

        Label contractorLabel = new Label("Administrator");
        ListView<String> contractorMenu = new ListView<>();
        contractorMenu.getItems().addAll( "See active projects", "Edit/Update Projects", "Submit Invoice", "Edit Profile");

        contractorMenu.setOnMouseClicked(e -> {
            String selectedItem = contractorMenu.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                switch (selectedItem) {
                    case "See active projects":
                        showActiveProjectsScreen();
                        break;
                    case "Edit/Update Projects":
                        showEditScreen();
                        break;
                    case "Submit Invoice":
                        showInvoiceScreen();
                        break;
                    case "Edit Profile":
                        showEditProfileScreen();
                        break;
                    default:
                        break;
                }
            }
        });

        leftPanel.getChildren().addAll(contractorLabel, contractorMenu);
    }

    public BorderPane getContractorScreen() {
        root.setLeft(leftPanel);


        return root;
    }

    private void showEditProfileScreen() {
        VBox editProfilesPanel = new VBox(10);
        editProfilesPanel.setPadding(new Insets(20));

        Label editProfilesTitle = new Label("Edit Profile");
        editProfilesTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        editProfilesPanel.getChildren().add(editProfilesTitle);

        Label usernameLabel = new Label("Username: " + currentUser.getAccount());
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter new username");

        Label phoneLabel = new Label("Phone Number: " + currentUser.getContact());
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter new phone number");

        Label emailLabel = new Label("Email: " + currentUser.getEmail());
        TextField emailField = new TextField();
        emailField.setPromptText("Enter new email");
//
//        Label ageLabel = new Label("Age: " + age);
//        TextField ageField = new TextField();
//        ageField.setPromptText("Enter new age");
//        HBox ageBox = new HBox(10);
//        editProfilesPanel.getChildren().add(ageBox);

//        Label locationLabel = new Label("Location: " + location);
//        TextField locationField = new TextField();
//        locationField.setPromptText("Enter new location");
//        HBox locationBox = new HBox(10);
//        editProfilesPanel.getChildren().add(locationBox);

        Button saveButton = new Button("Save");
        try{
            saveButton.setOnAction(e -> {
                String newUsername = usernameField.getText();
                String newPhone = phoneField.getText();
                String newEmail = emailField.getText();
//                String newAge = ageField.getText();
//                String newLocation = locationField.getText();

                if (!newUsername.isEmpty()) {
                    currentUser.setAccount(newUsername);
                }
                if (!newPhone.isEmpty()) {
                    currentUser.setContact(newPhone);
                }
                if (!newEmail.isEmpty()) {
                    currentUser.setEmail(newEmail);
                }
//                if (!newAge.isEmpty()) {
//                    age.set(newAge);
//                }
//                if (!newLocation.isEmpty()) {
//                    location.set(newLocation);
//                }

                usernameLabel.setText("Username: " + currentUser.getAccount());
                phoneLabel.setText("Phone Number: " + currentUser.getContact());
                emailLabel.setText("Email: " + currentUser.getEmail());
//                ageLabel.setText("Age: " + age);
//                locationLabel.setText("Location: " + location);
            });
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        editProfilesPanel.getChildren().addAll(usernameLabel, usernameField, phoneLabel, phoneField, emailLabel, emailField, saveButton);

        root.setCenter(editProfilesPanel);
    }

    private void showInvoiceScreen() {
        VBox mainContent = new VBox(10);
        mainContent.setPadding(new Insets(10));
        mainContent.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px;");

        Label title = new Label("Invoice Submission");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        GridPane invoiceForm = new GridPane();
        invoiceForm.setHgap(10);
        invoiceForm.setVgap(10);
        invoiceForm.setPadding(new Insets(10));

        Label invoiceLabel = new Label("Invoice To:");
        TextField invoiceField = new TextField("Bill");
        Label numberLabel = new Label("Invoice #:");
        TextField numberField = new TextField("123456");
        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();

        invoiceForm.add(invoiceLabel, 0, 0);
        invoiceForm.add(invoiceField, 1, 0);
        invoiceForm.add(numberLabel, 0, 1);
        invoiceForm.add(numberField, 1, 1);
        invoiceForm.add(dateLabel, 0, 2);
        invoiceForm.add(datePicker, 1, 2);

        // Itemized Table
        VBox itemizedTable = new VBox(10);
        itemizedTable.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-padding: 10px;");

        Label tableTitle = new Label("Invoice Details:");
        GridPane itemGrid = new GridPane();
        itemGrid.setHgap(10);
        itemGrid.setVgap(5);

        String[][] items = {
                {"Materials", "$10.00"},
                {"Labour", "$30.00"},
                {"Tax", "$10.00"},
                {"Total", "$50.00"}
        };

        for (int i = 0; i < items.length; i++) {
            Label item = new Label(items[i][0]);
            Label price = new Label(items[i][1]);

            itemGrid.add(item, 0, i);
            itemGrid.add(price, 1, i);
        }

        itemizedTable.getChildren().addAll(tableTitle, itemGrid);

        // Action Buttons
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        Button payNowButton = new Button("Submit");
        Button clearButton = new Button("Clear");

        buttons.getChildren().addAll(payNowButton, clearButton);

        mainContent.getChildren().addAll(title, invoiceForm, itemizedTable, buttons);

        root.setCenter(mainContent);

        payNowButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invoice Submitted");
            alert.setHeaderText("Invoice Submitted Successfully");
            alert.setContentText("Your invoice has been submitted successfully. You will receive a confirmation email shortly.");
            alert.showAndWait();
        });




    }

    private void showEditScreen() {
        VBox editScreenPanel = new VBox(10);
        editScreenPanel.setPadding(new Insets(20));
        Label editTitle = new Label("Edit Projects");
        editTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        editScreenPanel.getChildren().add(editTitle);

        TableView<ServiceRequestForm.Services> editProjectsTable = new TableView<>();

        TableColumn<ServiceRequestForm.Services, String> serviceTypeCol = new TableColumn<>("Service Type");
        serviceTypeCol.setCellValueFactory(new PropertyValueFactory<>("serviceType"));

        TableColumn<ServiceRequestForm.Services, String> dateCol = new TableColumn<>("Date Requested");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<ServiceRequestForm.Services, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<ServiceRequestForm.Services, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<ServiceRequestForm.Services, String> completeCol = new TableColumn<>("Mark as Complete");
        completeCol.setMinWidth(80);
        completeCol.setCellFactory(param -> new TableCell<>() {
            private final Button completeButton = new Button("Complete");

            {
                completeButton.setOnAction(event -> {
                    ServiceRequestForm.Services services = (ServiceRequestForm.Services) getTableView().getItems();
                    services.setStatus("Complete");
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                ServiceRequestForm.Services services = (ServiceRequestForm.Services) getTableView().getItems();
                super.updateItem(item, empty);
                if (empty && services.getStatus() == "Complete") {
                    setGraphic(null);
                } else {
                    setGraphic(completeButton);
                }
            }
        });

        editProjectsTable.getColumns().addAll(serviceTypeCol, dateCol, descriptionCol, statusCol, completeCol);

        editScreenPanel.getChildren().add(editProjectsTable);

        editProjectsTable.setItems(FXCollections.observableArrayList(
                new ServiceRequestForm.Services("Plumbing", "09/06/2024", "Requested plumbing to be laid", "Complete"),
                new ServiceRequestForm.Services("Electrical", "10/03/2024", "Requested new wiring throughout property", "Pending"),
                new ServiceRequestForm.Services("House addition", "11/16/2024", "Requested house addition to be constructed", "Pending"),
                new ServiceRequestForm.Services("Housing Unit", "11/22/2024", "Requested construction of property", "Pending")
        ));

        root.setCenter(editScreenPanel);
    }

    private void showActiveProjectsScreen() {
        VBox activeProjectsPanel = new VBox(10);
        activeProjectsPanel.setPadding(new Insets(20));
        Label serviceHistoryTitle = new Label("Active Projects");
        serviceHistoryTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        activeProjectsPanel.getChildren().add(serviceHistoryTitle);

        TableView<ServiceRequestForm.Services> projectsTable = new TableView<>();

        TableColumn<ServiceRequestForm.Services, String> serviceTypeCol = new TableColumn<>("Service Type");
        serviceTypeCol.setCellValueFactory(new PropertyValueFactory<>("serviceType"));

        TableColumn<ServiceRequestForm.Services, String> dateCol = new TableColumn<>("Date Requested");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<ServiceRequestForm.Services, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<ServiceRequestForm.Services, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        projectsTable.getColumns().addAll(serviceTypeCol, dateCol, descriptionCol, statusCol);

        activeProjectsPanel.getChildren().add(projectsTable);

        projectsTable.setItems(FXCollections.observableArrayList(
                new ServiceRequestForm.Services("Plumbing", "09/06/2024", "Requested plumbing to be laid", "Approved"),
                new ServiceRequestForm.Services("Electrical", "10/03/2024", "Requested new wiring throughout property", "Approved"),
                new ServiceRequestForm.Services("House addition", "11/16/2024", "Requested house addition to be constructed", "Waiting for Approval"),
                new ServiceRequestForm.Services("Housing Unit", "11/22/2024", "Requested construction of property", "Waiting for Approval")
        ));

        root.setCenter(activeProjectsPanel);
    }


}
