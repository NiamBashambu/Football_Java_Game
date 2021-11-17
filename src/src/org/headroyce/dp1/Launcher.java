package org.headroyce.dp1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        primaryStage.setTitle("Canvas");
        root.setStyle("-fx-background-color:green");





        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}