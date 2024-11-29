package com.example.cosc3506project.screens;


import com.example.cosc3506project.servlets.ActiveProjectServlet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

public class ContractorPage {

    private AdminPage.User currentUser;


    BorderPane root = new BorderPane();

    VBox leftPanel = new VBox(10);



    /**
     * Constructor for the ContractorPage
     * This will call the setupLeftPanel method to set up the left panel
     * and forces the user to be hardcoded
     *
     */
    public ContractorPage() {
        this.currentUser = new AdminPage.User("2350529", "Ryan", "705-382-3941", "Active", "ryan123@gmail.ca");
        setupLeftPanel();
    }

    /**
     * This sets-ups the left panel for the whole contactor menu
     *
     */
    private void setupLeftPanel() {
        leftPanel.setPadding(new Insets(10));
        leftPanel.setPrefWidth(200);
        leftPanel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px;");

        Label contractorLabel = new Label("Contractor");
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

    /**
     * This is called when the user is logged in and confirmed to be a contractor
     *
     */
    public BorderPane getContractorScreen() {
        root.setLeft(leftPanel);

        return root;
    }

    /**
     * This displays the profile screen and allows the user to edit themselves while logged in.
     *
     */
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

        Button saveButton = new Button("Save");
        try{
            saveButton.setOnAction(e -> {
                String newUsername = usernameField.getText();
                String newPhone = phoneField.getText();
                String newEmail = emailField.getText();
                if (!newUsername.isEmpty()) {
                    currentUser.setAccount(newUsername);
                }
                if (!newPhone.isEmpty()) {
                    currentUser.setContact(newPhone);
                }
                if (!newEmail.isEmpty()) {
                    currentUser.setEmail(newEmail);
                }

                usernameLabel.setText("Username: " + currentUser.getAccount());
                phoneLabel.setText("Phone Number: " + currentUser.getContact());
                emailLabel.setText("Email: " + currentUser.getEmail());
            });
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        editProfilesPanel.getChildren().addAll(usernameLabel, usernameField, phoneLabel, phoneField, emailLabel, emailField, saveButton);

        root.setCenter(editProfilesPanel);
    }

    /**
     * This allows the contractor to send invoices to a specific person
     *
     */
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
        // Add items to the grid via a loop
        for (int i = 0; i < items.length; i++) {
            Label item = new Label(items[i][0]);
            Label price = new Label(items[i][1]);

            itemGrid.add(item, 0, i);
            itemGrid.add(price, 1, i);
        }

        itemizedTable.getChildren().addAll(tableTitle, itemGrid);

        // Adds the submit and clear buttons
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

    /**
     * This allows the contractor to view all the projects they are currently working on
     * and allow them to mark them completed.
     */
    private void showEditScreen() {
        VBox editScreenPanel = new VBox(10);
        editScreenPanel.setPadding(new Insets(20));
        Label editTitle = new Label("Edit Projects");
        editTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        editScreenPanel.getChildren().add(editTitle);

        TableView<ActiveProjectServlet.Services> editProjectsTable = new TableView<>();

        TableColumn<ActiveProjectServlet.Services, String> serviceTypeCol = new TableColumn<>("Service Type");
        serviceTypeCol.setCellValueFactory(new PropertyValueFactory<>("serviceType"));

        TableColumn<ActiveProjectServlet.Services, String> dateCol = new TableColumn<>("Date Requested");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<ActiveProjectServlet.Services, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<ActiveProjectServlet.Services, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<ActiveProjectServlet.Services, String> completeCol = new TableColumn<>("Mark as Complete");
        completeCol.setMinWidth(80);
        completeCol.setCellFactory(param -> new TableCell<>() {
            private final Button completeButton = new Button("Complete Project");
            {
                completeButton.setOnAction(e -> {
                    ActiveProjectServlet.Services services = getTableView().getItems().get(getIndex());
                    services.setStatus("Complete");
                    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
                    editProjectsTable.refresh();
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(completeButton);
                }
            }
        });

        editProjectsTable.getColumns().addAll(serviceTypeCol, dateCol, descriptionCol, statusCol, completeCol);

        editScreenPanel.getChildren().add(editProjectsTable);

        editProjectsTable.setItems(FXCollections.observableArrayList(
                new ActiveProjectServlet.Services("Plumbing", "09/06/2024", "Requested plumbing to be laid", "Complete"),
                new ActiveProjectServlet.Services("Electrical", "10/03/2024", "Requested new wiring throughout property", "Pending"),
                new ActiveProjectServlet.Services("House addition", "11/16/2024", "Requested house addition to be constructed", "Pending"),
                new ActiveProjectServlet.Services("Housing Unit", "11/22/2024", "Requested construction of property", "Pending")
        ));

        root.setCenter(editScreenPanel);
    }

    /**
     * Show the screen for the user to view their active projects
     * This screen will display the table from the ActiveProjectServlet
     *
     */
    private void showActiveProjectsScreen() {
        VBox activeProjectsPanel = new VBox(10);
        activeProjectsPanel.setPadding(new Insets(20));
        Label activeProjectTitle = new Label("Active Projects");
        activeProjectTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        activeProjectsPanel.getChildren().add(activeProjectTitle);

        TableView<ActiveProjectServlet.Services> projectsTable = new TableView<>();

        TableColumn<ActiveProjectServlet.Services, String> serviceTypeCol = new TableColumn<>("Service Type");
        serviceTypeCol.setCellValueFactory(new PropertyValueFactory<>("serviceType"));

        TableColumn<ActiveProjectServlet.Services, String> dateCol = new TableColumn<>("Date Requested");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<ActiveProjectServlet.Services, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<ActiveProjectServlet.Services, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        projectsTable.getColumns().addAll(serviceTypeCol, dateCol, descriptionCol, statusCol);

        activeProjectsPanel.getChildren().add(projectsTable);

        projectsTable.setItems(getDataFromActiveProjectServlet());

        root.setCenter(activeProjectsPanel);
    }

/**
     * This method retrieves the data from the ActiveProjectServlet
     * and returns an ObservableList of the data
     *
     * @return ObservableList of the data from the ActiveProjectServlet
     */
    private ObservableList<ActiveProjectServlet.Services> getDataFromActiveProjectServlet() {
        ObservableList<ActiveProjectServlet.Services> data = FXCollections.observableArrayList();

        try {
            URL url = new URL("http://localhost:8081/active-project");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + connection.getResponseCode());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    responseBuilder.append(line);
                }

                // Parse the JSON response from the active project servlet
                JSONArray jsonArray = new JSONArray(responseBuilder.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String serviceType = jsonObject.getString("serviceType");
                    String date = jsonObject.getString("date");
                    String description = jsonObject.getString("description");
                    String status = jsonObject.getString("status");

                    // Add the parsed object to the data list to be displayed in the table
                    data.add(new ActiveProjectServlet.Services(serviceType, date, description, status));
                }
            }

            connection.disconnect();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();

        }

        return data;
    }


}
