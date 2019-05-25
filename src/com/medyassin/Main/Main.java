package com.medyassin.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    /* Stage */
    private Stage stage;

    /* Scene */
    private Scene scene;


    @Override
    public void start(Stage primaryStage) throws Exception{
        /* Init the screen */
        setInitialParams(primaryStage);
    }

    /**
     * Init the screen
     * @param primaryStage
     */
    private void setInitialParams(Stage primaryStage) {
        stage = primaryStage;

        try {
            /* Get XML Loader */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./../View/Fxmls/LoginScreen.fxml"));
            AnchorPane pane = loader.load();

            /* Set the scene */
            Scene scene = new Scene(pane);

            /* Set CSS Stylesheet */
            scene.getStylesheets().addAll(getClass().getResource("./../View/Fxmls/LoginScreen.css").toExternalForm());

            /* Stage = window parameters */
            primaryStage.setTitle("Restaurant Management System");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(1000);
            primaryStage.setMaxWidth(1000);
            primaryStage.setMinHeight(600);
            primaryStage.setMaxHeight(600);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
