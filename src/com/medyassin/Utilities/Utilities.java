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
    public static void switchScreen(Scene scene, Parent target, String css) {
        Stage currentStage = (Stage) scene.getWindow();
        scene.setRoot(target);
        scene.getStylesheets().addAll(css);
        currentStage.setMinWidth(600);
        currentStage.setMinHeight(500);
    }
}
