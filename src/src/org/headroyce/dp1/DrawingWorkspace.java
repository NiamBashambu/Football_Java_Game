package org.headroyce.dp1;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
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
    private MODES mode;

    public static enum MODES {
        ALL_OFF,
        DRAWING_MODE,
        STAMP_MODE
    }

    public DrawingWorkspace() {
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();


        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new mouseHandler());

        StackPane center = new StackPane();
        center.setMinSize(0,0);


        center.getChildren().add(canvas);
        canvas.widthProperty().bind(center.widthProperty());
        canvas.heightProperty().bind(center.heightProperty());


        lt = new LineTool(canvas);
        VBox tools = new VBox();

        Node ltButton = lt.renderTool();
        ltButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if (evt.isPrimaryButtonDown()) {
                    if (getMode() == MODES.DRAWING_MODE) {
                        setMode(MODES.ALL_OFF);
                    } else if (getMode() == MODES.ALL_OFF) {
                        setMode(MODES.DRAWING_MODE);
                    }

                }
            }
        });
        tools.getChildren().add(ltButton);

       this.setRight(tools);
       this.setCenter(center);

       mode = MODES.ALL_OFF;

    }

    public boolean setMode( MODES newMode ){
        mode = newMode;
        return true;
    }

    public MODES getMode(){
        return mode;
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


    public class mouseHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent evt) {

            System.err.println("HERE");
                if( mode == MODES.DRAWING_MODE) {
                    Point2D p = new Point2D(evt.getX(), evt.getY());
                    lt.addPoint(p);
                    System.out.println("p");
                }
        }
    }

}
