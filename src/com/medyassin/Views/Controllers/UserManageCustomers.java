package com.medyassin.Views.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.medyassin.DatabaseControllers.ManageCustomer;
import com.medyassin.Models.Customer;
import com.medyassin.TableViewModels.CustomerTVModel;
import com.medyassin.Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserManageCustomers implements Initializable, EventHandler<MouseEvent> {
    @FXML
    private Pane manageCustomers;

    @FXML
    private TableView customersTable;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private JFXTextField nameTF;

    @FXML
    private Circle manageCustomersMask;

    @FXML
    private Pane viewAllOrders;

    @FXML
    private JFXButton confirmBtn;

    @FXML
    private Circle UserImageMaskCircle;

    @FXML
    private Pane pendingOrders;

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXTextField addressTF;

    @FXML
    private JFXTextField phoneNTF;

    @FXML
    private TableColumn cPhoneNumber;

    @FXML
    private TableColumn cAddress;

    @FXML
    private TableColumn cName;

    @FXML
    private TableColumn cID;

    @FXML
    private Pane makeOrder;

    @FXML
    private JFXTextField idTF;

    @FXML
    private JFXComboBox<String> actionCB;

    // Data for the Customer table
    private ObservableList<CustomerTVModel> data = FXCollections.observableArrayList();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set Image for the circular mask
        UserImageMaskCircle.setFill(new ImagePattern(new Image("/com/medyassin/Img/user_img.jpg", false)));

        // Set no focus for logout btn
        logoutBtn.setDisableVisualFocus(true);

        // Set Image Icon for mask of 'Manage customers'
        manageCustomersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/user.png", false)));

        // Set Mouse event on left menu
        makeOrder.setOnMouseEntered(this);
        makeOrder.setOnMouseExited(this);

        viewAllOrders.setOnMouseEntered(this);
        viewAllOrders.setOnMouseExited(this);

        pendingOrders.setOnMouseEntered(this);
        pendingOrders.setOnMouseExited(this);

        // Set Customer Table
        setCustomerTable();

        // Set Add New Customer Action Event on Confirm Button
        confirmBtn.setOnAction(e -> {
            if(actionCB.getValue() == null) {
                System.err.println("Veuillez choisir un opération");

            } else if(actionCB.getValue().equals("Ajouter")) {
                addNewCustomer();
            } else if(actionCB.getValue().equals("Supprimer")) {
                //TODO
                System.out.println(actionCB.getValue());

            } else if(actionCB.getValue().equals("Rechercher")) {
                //TODO
                System.out.println(actionCB.getValue());

            } else if(actionCB.getValue().equals("Mise à jour")) {
                //TODO
                updateCustomer();
            }

        });

        // Set Combo box
        actionCB.getItems().addAll("Mise à jour", "Supprimer", "Rechercher", "Ajouter");

        // Set next ID for TextField
        setIDTF();

        // Set row selection listener
        customersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                CustomerTVModel cm = (CustomerTVModel) newSelection;
                idTF.setText(cm.getCustomerId());
                nameTF.setText(cm.getCustomerName());
                phoneNTF.setText(cm.getCustomerPhoneN());
                addressTF.setText(cm.getCustomerAddress());
            }
        });
    }

    private void updateCustomer() {
        int id = Integer.parseInt(idTF.getText());
        String name = nameTF.getText();
        String phoneN = phoneNTF.getText();
        String address = addressTF.getText();

        try {
            boolean success = ManageCustomer.updateCustomer(id, name, phoneN, address);
            if(!success) {
                System.out.println("Alert: update customer has failed !");
            } else {
                System.out.println("Alert: update customer is done !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Refresh
        refreshCustomerTable();
    }

    private void clearCustomerForm() {
        nameTF.setText("");
        phoneNTF.setText("");
        addressTF.setText("");
    }

    private void refreshCustomerTable() {
        data = FXCollections.observableArrayList();
        try {
            ArrayList<Customer> customers = ManageCustomer.getAllCustomers();
            for(Customer customer: customers) {
                data.add(new CustomerTVModel(customer.getcID(), customer.getcName(), customer.getcPhoneN(), customer.getcAddress()));
            }
            customersTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerTable() {
        cID.setCellValueFactory(new PropertyValueFactory<CustomerTVModel, String>("customerId"));
        cName.setCellValueFactory(new PropertyValueFactory<CustomerTVModel, String>("customerName"));
        cPhoneNumber.setCellValueFactory(new PropertyValueFactory<CustomerTVModel, String>("customerPhoneN"));
        cAddress.setCellValueFactory(new PropertyValueFactory<CustomerTVModel, String>("customerAddress"));

        refreshCustomerTable();
    }

    private void setIDTF() {
        try {
            idTF.setText(ManageCustomer.getNextID() + "");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void addNewCustomer() {
        String name = nameTF.getText();
        String phoneN = phoneNTF.getText();
        String address = addressTF.getText();

        try {
            if(!ManageCustomer.addNewCustomer(name, phoneN, address)) {
                System.out.print("Alert: can't add new customer");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Refresh
        refreshCustomerTable();

        // Clear inputs
        clearCustomerForm();
    }

    @FXML
    private void logoutBtnAction(ActionEvent actionEvent) throws IOException {
        // Go to login page
        Scene currentScene = borderPane.getScene();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.fxml"));
        Utilities.switchScreen(currentScene, anchorPane, getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.css").toExternalForm(), false);
    }

    @FXML
    private void manageCustomersHover() {
        manageCustomers.setStyle("-fx-background-color: #1bb85a;");
    }

    @FXML
    private void manageCustomersHoverEnd() {
        manageCustomers.setStyle("-fx-background-color:  #22DB6C;");
    }

    @Override
    public void handle(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        if(event.getEventType().toString().equals("MOUSE_ENTERED")) {
            pane.setStyle("-fx-background-color:  #4E4E4E;");
        } else if(event.getEventType().toString().equals("MOUSE_EXITED")) {
            pane.setStyle("-fx-background-color:  #343434;");
        }
    }
}