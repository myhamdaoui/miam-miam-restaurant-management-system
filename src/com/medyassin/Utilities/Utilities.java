package com.medyassin.Utilities;

import com.jfoenix.controls.JFXSnackbar;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Utilities {
    /**
     *
     * @param scene -> Current Scene
     * @param target -> Target Layout(xml)
     * @param css -> Custom CSS Stylesheet
     */
    public static void switchScreen(Scene scene, Parent target, String css, boolean resizable) {
        Stage currentStage = (Stage) scene.getWindow();
        scene.setRoot(target);
        scene.getStylesheets().addAll(css);
        currentStage.setWidth(1000);
        currentStage.setHeight(600);
        currentStage.setResizable(false);
        if(resizable) {
            currentStage.setResizable(true);
        }
    }

    public static void showToast(String msg, Label toast) {
        toast.setText(msg);
        toast.setVisible(true);

        FadeTransition f = new FadeTransition(Duration.seconds(2), toast);
        f.setToValue(1);
        f.setFromValue(0);
        f.play();
        f.setOnFinished(e -> {
            FadeTransition f1 = new FadeTransition(Duration.seconds(2), toast);
            f1.setToValue(0);
            f1.setFromValue(1);
            f1.play();
        });
    }
}
