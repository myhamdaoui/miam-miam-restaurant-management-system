package com.medyassin.View.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
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
    private void btnLoginAction() {
        //Get Username input
        String usernameInput = username.getText();

        //Get password input
        String passwordInput = password.getText();

        //Check values

        //login
        System.out.println(usernameInput + ", " + passwordInput);
    }
}
