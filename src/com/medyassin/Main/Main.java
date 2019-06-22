package com.medyassin.Main;

import com.medyassin.Views.Controllers.LoginScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.fxml"));
            AnchorPane pane = loader.load();

            /* Set the scene */
            Scene scene = new Scene(pane);

            /* Set CSS Stylesheet */
            scene.getStylesheets().addAll(getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.css").toExternalForm());

            /* Stage = window parameters */
            primaryStage.setTitle("Miam-Miam Restaurant Management System");
            primaryStage.setWidth(1000);
            primaryStage.setHeight(600);
            primaryStage.setScene(scene);
            //primaryStage.setResizable(false);

            // Set icon
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/medyassin/Img/icons/icon.png")));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
