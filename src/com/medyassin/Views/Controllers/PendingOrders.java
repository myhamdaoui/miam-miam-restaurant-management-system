package com.medyassin.Views.Controllers;

import com.jfoenix.controls.JFXButton;
import com.medyassin.DatabaseControllers.AllOrdersController;
import com.medyassin.DatabaseControllers.UserController;
import com.medyassin.TableViewModels.AddNewOrderTVModel;
import com.medyassin.TableViewModels.ViewAllOrdersTVModel;
import com.medyassin.Utilities.CustomAlert.CustomerAlert;
import com.medyassin.Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PendingOrders implements Initializable {
    @FXML
    private Pane manageCustomers;

    @FXML
    private Pane pendingOrders;

    @FXML
    private Pane viewAllOrders;

    @FXML
    private Pane makeOrder;

    @FXML
    private Pane manageUsers;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private Circle manageCustomersMask;

    @FXML
    private Circle makeOrderMask;

    @FXML
    private Circle viewAllOrdersMask;

    @FXML
    private Circle pendingOrdersMask;

    @FXML
    private Circle UserImageMaskCircle;

    @FXML
    private Circle manageUsersMask;

    @FXML
    private TableColumn orderIDTC;

    @FXML
    private TableColumn clientNameTC;

    @FXML
    private TableColumn orderDateTC;

    @FXML
    private TableColumn amountTC;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView pendingOrdersTable;

    @FXML
    private Label nameLabel;

    private ObservableList<ViewAllOrdersTVModel> data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        - Add icons to the sidebar
        - Add user image to the top bar
        - Add username to the top bar
        */
        setStaticItems();

        setVisibility();

        /* Set AllOrders table */
        setPendingOrdersTable();

        /* filter by date */
        filterByDate();
    }

    private void filterByDate() {
        datePicker.setOnAction(e -> {
            if(datePicker.getValue() == null) {
                refreshTable("all");
            } else {
                refreshTable(datePicker.getValue().toString());
            }

        });
    }

    private void setPendingOrdersTable() {
        orderIDTC.setCellValueFactory(new PropertyValueFactory<ViewAllOrdersTVModel, String>("orderID"));
        clientNameTC.setCellValueFactory(new PropertyValueFactory<ViewAllOrdersTVModel, String>("clientName"));
        orderDateTC.setCellValueFactory(new PropertyValueFactory<ViewAllOrdersTVModel, String>("orderDate"));
        amountTC.setCellValueFactory(new PropertyValueFactory<ViewAllOrdersTVModel, String>("orderAmount"));
        manageUsersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/user.png", false)));

        refreshTable("all");
    }

    private void refreshTable(String date) {
        try {
            data = AllOrdersController.getAllOrders(date, false);
            pendingOrdersTable.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* LOGIC HERE */

    @FXML
    public void markAsPaid(ActionEvent e) throws IOException, SQLException, ClassNotFoundException {
        // get row index

        ObservableList ol = pendingOrdersTable.getSelectionModel().getSelectedCells();
        if(ol.size() > 0) {
            TablePosition<ViewAllOrdersTVModel, String> tp = (TablePosition<ViewAllOrdersTVModel, String>)ol.get(0);
            int index = tp.getRow();
            ViewAllOrdersTVModel orderData = (ViewAllOrdersTVModel)pendingOrdersTable.getSelectionModel().getSelectedItem();
            System.out.println("index:" + index);
            // remove item from data
            data.remove(index);

            // remove from orderdetails "BD"
            if(AllOrdersController.markAsPaid(orderData.getOrderID())) {
                CustomerAlert.display("success", "la commande a été payée");
            } else {
                CustomerAlert.display("error", "Erreur: La commande ne peut pas être payée");
            }


            // set data again
            pendingOrdersTable.setItems(data);
        }
    }

    /*
    - Show sections based on user role
     */
    public void setVisibility() {
        try {
            if(UserController.getUserRole().equals("admin")) {
                makeOrder.setMaxHeight(0);
                makeOrder.setVisible(false);
            } else {
                manageUsers.setMaxHeight(0);
                manageUsers.setVisible(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* SECTION Pending orders */

    private void setStaticItems() {
        // Set Image for the circular mask

        try {
            String url = "/com/medyassin/Img/" + UserController.getUserImg();
            System.out.println(url);
            UserImageMaskCircle.setFill(new ImagePattern(new Image(url, false)));

            // set name
            nameLabel.setText("Bienvenue " + UserController.getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Set no focus for logout btn
        logoutBtn.setDisableVisualFocus(true);

        // Set Image Icon for mask of 'Manage customers' ...
        manageCustomersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/user.png", false)));
        makeOrderMask.setFill(new ImagePattern(new Image(getClass().getResource("/com/medyassin/Img/icons/order.png").toExternalForm(), false)));
        viewAllOrdersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/view.png", false)));
        pendingOrdersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/pending.png", false)));
    }

    @FXML
    private void logoutBtnAction(ActionEvent actionEvent) throws IOException {
        // Go to login page
        Scene currentScene = logoutBtn.getScene();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.fxml"));
        Utilities.switchScreen(currentScene, anchorPane, getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.css").toExternalForm(), false, 600);
    }

    @FXML
    public void manageUsersClick(MouseEvent e) {
        Scene currentScene = manageCustomers.getScene();
        try {
            BorderPane target = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/ManageUsers.fxml"));
            Utilities.switchScreen(currentScene, target,getClass().getResource("/com/medyassin/Views/Fxmls/UserManageCustomers.css").toExternalForm(), true, 600);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
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
            Utilities.switchScreen(currentScene, target,getClass().getResource("/com/medyassin/Views/Fxmls/ViewAllOrders.css").toExternalForm(), true, 600);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void userManagClick(MouseEvent e) {
        Scene currentScene = manageCustomers.getScene();
        try {
            BorderPane target = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/UserManageCustomers.fxml"));
            Utilities.switchScreen(currentScene, target,getClass().getResource("/com/medyassin/Views/Fxmls/UserManageCustomers.css").toExternalForm(), true, 600);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
