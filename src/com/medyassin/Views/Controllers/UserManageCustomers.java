package com.medyassin.Views.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
    private JFXTextField AddressTF;

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

        // Set Table
        cID.setCellValueFactory(new PropertyValueFactory<CustomerTVModel, String>("customerId"));
        cName.setCellValueFactory(new PropertyValueFactory<CustomerTVModel, String>("customerName"));
        cPhoneNumber.setCellValueFactory(new PropertyValueFactory<CustomerTVModel, String>("customerPhoneN"));
        cAddress.setCellValueFactory(new PropertyValueFactory<CustomerTVModel, String>("customerAddress"));

        data = getData();
        customersTable.setItems(data);

    }

    private ObservableList<CustomerTVModel> getData() {
        ObservableList<CustomerTVModel> data = FXCollections.observableArrayList();
        data.add(new CustomerTVModel("01", "yassin", "060606060", "Oujda Maroc"));
        data.add(new CustomerTVModel("02", "Ahmed", "064545454", "Berkane, Maroc"));

        return data;
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