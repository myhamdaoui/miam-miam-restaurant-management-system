package com.medyassin.Views.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.medyassin.DatabaseControllers.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {
    @FXML
    private AnchorPane loginScreen;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Nothing until now
    }

    @FXML
    private void btnLoginAction(ActionEvent actionEvent) {
        //Get Username input
        String usernameInput = username.getText();

        //Get password input
        String passwordInput = password.getText();

        //Check values form database

        //login
        try {
            if(LoginController.login(usernameInput, passwordInput)) {
                System.out.println("login success");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
