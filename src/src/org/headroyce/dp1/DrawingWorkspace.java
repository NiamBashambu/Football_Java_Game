package org.headroyce.dp1;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.shape.Line;
import javafx.scene.Group;
import javafx.scene.effect.ColorInput;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.stage.Stage;

public class DrawingWorkspace extends BorderPane{
    private Canvas canvas;
    private GraphicsContext gc;

    public DrawingWorkspace() {
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());


        this.setCenter(canvas);
    }

    public void refreshScreen(){
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(4);
        gc.strokeLine(0, canvas.getHeight()*0.8, canvas.getWidth(), canvas.getHeight()*0.8);
    }
}
