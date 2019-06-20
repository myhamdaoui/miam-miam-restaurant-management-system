package com.medyassin.Views.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.medyassin.DatabaseControllers.ManageCustomerController;
import com.medyassin.Models.Customer;
import com.medyassin.TableViewModels.CustomerTVModel;
import com.medyassin.Utilities.CustomAlert.CustomerAlert;
import com.medyassin.Utilities.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.KeyEvent;
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
    private Pane pendingOrders;

    @FXML
    private Pane viewAllOrders;

    @FXML
    private Pane makeOrder;

    @FXML
    private TableView customersTable;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private JFXTextField nameTF;

    @FXML
    private Circle manageCustomersMask;

    @FXML
    private Circle makeOrderMask;

    @FXML
    private Circle viewAllOrdersMask;

    @FXML
    private Circle pendingOrdersMask;

    @FXML
    private JFXButton confirmBtn;

    @FXML
    private Circle UserImageMaskCircle;

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
    private JFXTextField idTF;

    @FXML
    private JFXComboBox<String> actionCB;

    // Data for the Customer table
    private ObservableList<CustomerTVModel> data = FXCollections.observableArrayList();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        - Add icons to the sidebar
        - Add user image to the top bar
        - Add username to the top bar
        */
        setStaticItems();

        // Set Customer Table
        setCustomerTable();


        confirmBtn.setOnAction(e -> {
            if(actionCB.getValue() == null) {
                try {
                    CustomerAlert.display("error", "Veuillez choisir un opération");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            } else if(actionCB.getValue().equals("Ajouter")) {
                try {
                    addNewCustomer();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if(actionCB.getValue().equals("Supprimer")) {
                deleteCustomer();

            } else if(actionCB.getValue().equals("Mise à jour")) {
                //TODO
                updateCustomer();
            }

        });

        // Set Combo box
        actionCB.getItems().addAll("Mise à jour", "Supprimer", "Ajouter");
        actionCB.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("Ajouter")) {
                // Set id
                setIDTF();
                // Clear inputs
                clearCustomerForm();
            }
        });

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

    private void setStaticItems() {
        // Set Image for the circular mask
        UserImageMaskCircle.setFill(new ImagePattern(new Image("/com/medyassin/Img/user_img.jpg", false)));

        // Set no focus for logout btn
        logoutBtn.setDisableVisualFocus(true);

        // Set Image Icon for mask of 'Manage customers' ...
        manageCustomersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/user.png", false)));
        makeOrderMask.setFill(new ImagePattern(new Image(getClass().getResource("/com/medyassin/Img/icons/order.png").toExternalForm(), false)));
        viewAllOrdersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/view.png", false)));
        pendingOrdersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/pending.png", false)));
    }

    private void deleteCustomer() {
        int id = Integer.parseInt(idTF.getText());
        try {
            if(ManageCustomerController.deleteCustomer(id)) {
               CustomerAlert.display("success", "Le client a été bien supprimé");
            } else {
                try {
                    CustomerAlert.display("error", "Can't delete this client or client doesn't exists");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Refresh
        refreshCustomerTable();

        // Clear inputs
        clearCustomerForm();
    }

    private void updateCustomer() {
        int id = Integer.parseInt(idTF.getText());
        String name = nameTF.getText();
        String phoneN = phoneNTF.getText();
        String address = addressTF.getText();

        try {
            boolean success = ManageCustomerController.updateCustomer(id, name, phoneN, address);
            if(!success) {
                try {
                    CustomerAlert.display("error", "Update customer has failed !");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                try {
                    CustomerAlert.display("success", "Update Customer has been done successfully !");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
            ArrayList<Customer> customers = ManageCustomerController.getAllCustomers();
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
            idTF.setText(ManageCustomerController.getNextID() + "");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void addNewCustomer() throws IOException {
        String name = nameTF.getText();
        String phoneN = phoneNTF.getText();
        String address = addressTF.getText();

        //Validate inputs
        boolean validated = validateInputs();

        if(validated) {
            try {
                if (!ManageCustomerController.addNewCustomer(name, phoneN, address)) {
                    CustomerAlert.display("error", "Can't add new customer");
                } else {
                    CustomerAlert.display("success", "Le client a été bien ajouté");
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

            // Set next id
            setIDTF();
        }
    }

    private boolean validateInputs() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Ce champ est obligatoire");
        nameTF.getValidators().add(validator);
        phoneNTF.getValidators().add(validator);
        addressTF.getValidators().add(validator);

        if(!nameTF.validate()) {
            return false;
        } else if(!phoneNTF.validate()) {
            return false;
        } else if(!addressTF.validate()) {
            return false;
        }

        return true;
    }

    @FXML
    private void logoutBtnAction(ActionEvent actionEvent) throws IOException {
        // Go to login page
        Scene currentScene = borderPane.getScene();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.fxml"));
        Utilities.switchScreen(currentScene, anchorPane, getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.css").toExternalForm(), false, 600);
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

    private void searchForCustomer(String name) {
        data = FXCollections.observableArrayList();
        try {
            ArrayList<Customer> customers = ManageCustomerController.searchForCustomers(name);
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

    @FXML
    void searchEvent(KeyEvent e) {
        JFXTextField tf = (JFXTextField) e.getSource();
        searchForCustomer(tf.getText());
    }

    @FXML
    public void makeOrderClick(MouseEvent e) {
        Scene currentScene = manageCustomers.getScene();
        try {
            BorderPane target = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/UserMakeOrder.fxml"));
            Utilities.switchScreen(currentScene, target,getClass().getResource("/com/medyassin/Views/Fxmls/UserMakeOrder.css").toExternalForm(), true, 710);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void allOrderClick(MouseEvent e) {
        Scene currentScene = manageCustomers.getScene();
        try {
            BorderPane target = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/ViewAllOrders.fxml"));
            Utilities.switchScreen(currentScene, target,getClass().getResource("/com/medyassin/Views/Fxmls/ViewAllOrders.css").toExternalForm(), true,  600);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void pendingClick(MouseEvent e) {
        Scene currentScene = manageCustomers.getScene();
        try {
            BorderPane target = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/PendingOrders.fxml"));
            Utilities.switchScreen(currentScene, target,getClass().getResource("/com/medyassin/Views/Fxmls/PendingOrders.css").toExternalForm(), true, 600);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}