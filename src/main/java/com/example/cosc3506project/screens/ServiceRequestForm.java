package com.example.cosc3506project.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.util.concurrent.atomic.AtomicReference;


public class ServiceRequestForm  {

    private ProjectManagementSystem.User currentUser;

    BorderPane root = new BorderPane();  // Root layout for the screen
    VBox leftPanel = new VBox(10);  // The left panel menu

    public ServiceRequestForm() {
        this.currentUser = new ProjectManagementSystem.User("2000589", "John", "432-876-7869", "Active", "john@gmail.ca");
        setupLeftPanel();
    }

    private void setupLeftPanel(){
        // Left panel (User & Main sections)
        leftPanel.setPadding(new Insets(10));
        leftPanel.setPrefWidth(200);
        leftPanel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px;");

        Label userLabel = new Label("Users");
        ListView<String> userMenu = new ListView<>();
        userMenu.getItems().addAll("Dashboard", "Request Services", "Track Progress", "Payment History", "Edit Profile");

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
                    case "Payment History":
                        showPaymentHistoryScreen();
                        break;
                    case "Edit Profile":
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

    private void showPaymentHistoryScreen() {

        VBox managePaymentsPanel = new VBox(10);
        managePaymentsPanel.setPadding(new Insets(20));
        Label managePaymentsTitle = new Label("Payment History");
        managePaymentsPanel.setAlignment(Pos.TOP_CENTER);
        managePaymentsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TableView<Payment> table = new TableView<>();
        table.setEditable(false);

        TableColumn<Payment, String> invoiceCol = new TableColumn<>("Invoice#");
        invoiceCol.setMinWidth(100);
        invoiceCol.setCellValueFactory(new PropertyValueFactory<>("invoice"));

        TableColumn<Payment, String> dateSentCol = new TableColumn<>("Date Sent");
        dateSentCol.setMinWidth(100);
        dateSentCol.setCellValueFactory(new PropertyValueFactory<>("dateSent"));

        TableColumn<Payment, String> datePaidCol = new TableColumn<>("Date Paid");
        datePaidCol.setMinWidth(100);
        datePaidCol.setCellValueFactory(new PropertyValueFactory<>("datePaid"));

        TableColumn<Payment, String> accountCol = new TableColumn<>("Account");
        accountCol.setMinWidth(100);
        accountCol.setCellValueFactory(new PropertyValueFactory<>("account"));

        TableColumn<Payment, String> amountCol = new TableColumn<>("Amount");
        amountCol.setMinWidth(100);
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Payment, String> paymentMethodCol = new TableColumn<>("Payment Method");
        paymentMethodCol.setMinWidth(150);
        paymentMethodCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        table.getColumns().addAll(invoiceCol, dateSentCol, datePaidCol, accountCol, amountCol, paymentMethodCol);

        // Sample data
        ObservableList<Payment> data = FXCollections.observableArrayList(
                new Payment("572803", "2024-10-11", "2024-10-14", "John", "$100.00", "VISA"),
                new Payment("576322", "2024-05-03", "2024-05-11", "Bill", "$1000.00", "PAYPAL"),
                new Payment("583027", "2024-08-23", "2024-08-24", "Peter", "$350.00", "MASTERCARD"),
                new Payment("097425", "2024-01-30", "2024-02-05", "Bobby", "$487.54", "VISA"),
                new Payment("412032", "2024-07-15", "2024-07-19", "Mike", "$230.00", "PAYPAL"),
                new Payment("384612", "2024-06-27", "2024-07-03", "Bill", "$12000.00", "VISA"),
                new Payment("601723", "2024-02-05", "2024-02-11", "Max", "$500.00", "MASTERCARD"),
                new Payment("875294", "2024-03-17", "2024-03-21", "Bill", "$740.42", "VISA")
        );

        table.setItems(data);

        managePaymentsPanel.getChildren().addAll(managePaymentsTitle, table);

        // Add components to the root layout

        root.setCenter(managePaymentsPanel);
    }

    private void showEditProfilesScreen() {
        AtomicReference<String> age = new AtomicReference<>("0");
        AtomicReference<String> location = new AtomicReference<>("N/A");

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

        Label ageLabel = new Label("Age: " + age);
        TextField ageField = new TextField();
        ageField.setPromptText("Enter new age");
        HBox ageBox = new HBox(10);
        editProfilesPanel.getChildren().add(ageBox);

        Label locationLabel = new Label("Location: " + location);
        TextField locationField = new TextField();
        locationField.setPromptText("Enter new location");
        HBox locationBox = new HBox(10);
        editProfilesPanel.getChildren().add(locationBox);

        Button saveButton = new Button("Save");
        try{
            saveButton.setOnAction(e -> {
                String newUsername = usernameField.getText();
                String newPhone = phoneField.getText();
                String newEmail = emailField.getText();
                String newAge = ageField.getText();
                String newLocation = locationField.getText();

                if (!newUsername.isEmpty()) {
                    currentUser.setAccount(newUsername);
                }
                if (!newPhone.isEmpty()) {
                    currentUser.setContact(newPhone);
                }
                if (!newEmail.isEmpty()) {
                    currentUser.setEmail(newEmail);
                }
                if (!newAge.isEmpty()) {
                    age.set(newAge);
                }
                if (!newLocation.isEmpty()) {
                    location.set(newLocation);
                }

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

        editProfilesPanel.getChildren().addAll(usernameLabel, usernameField, phoneLabel, phoneField, emailLabel, emailField, ageLabel, ageField, locationLabel, locationField, saveButton);

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


    // Payment class
    public static class Payment {
        private String invoice;
        private String dateSent;
        private String datePaid;
        private String account;
        private String amount;
        private String paymentMethod;

        public Payment(String invoice, String dateSent, String datePaid, String account, String amount, String paymentMethod) {
            this.invoice = invoice;
            this.dateSent = dateSent;
            this.datePaid = datePaid;
            this.account = account;
            this.amount = amount;
            this.paymentMethod = paymentMethod;
        }

        public String getInvoice() {
            return invoice;
        }

        public String getDateSent() {
            return dateSent;
        }

        public String getDatePaid() {
            return datePaid;
        }

        public String getAccount() {
            return account;
        }

        public String getAmount() {
            return amount;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }
    }

}
