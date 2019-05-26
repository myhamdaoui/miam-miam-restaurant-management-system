package com.medyassin.Utilities;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
}
