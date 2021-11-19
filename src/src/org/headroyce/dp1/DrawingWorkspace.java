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
    private LineTool lt;
    private GraphicsContext gc;

    public DrawingWorkspace() {
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();

        StackPane center = new StackPane();
        center.setMinSize(0,0);


        center.getChildren().add(canvas);
        canvas.widthProperty().bind(center.widthProperty());
        canvas.heightProperty().bind(center.heightProperty());


        lt = new LineTool(canvas);
        VBox tools = new VBox();
        tools.getChildren().add(lt.renderTool());

       this.setRight(tools);
       this.setCenter(center);

    }

    public void refreshScreen(){

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.GREEN);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(4);
        gc.strokeLine(0, canvas.getHeight()*0.8, canvas.getWidth(), canvas.getHeight()*0.8);


        // lt.renderTool();

    }

}
