package org.headroyce.dp1;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

//create literally everything for the project
public class Launcher extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DrawingWorkspace dw = new DrawingWorkspace();
        primaryStage.setTitle("Canvas");






        primaryStage.setScene(new Scene(dw, 800, 600));
        primaryStage.show();

        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                dw.refreshScreen();
            }
        });

        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                dw.refreshScreen();
            }
        });




        dw.refreshScreen();
    }
}