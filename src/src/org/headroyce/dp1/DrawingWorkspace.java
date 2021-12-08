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
import javafx.scene.layout.*;

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
import java.util.Stack;

public class DrawingWorkspace extends BorderPane {
    private Canvas canvas;
    private LineTool lt;
    private GraphicsContext gc;
    private MODES mode;
    private Stack<LineTool> lines;

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
        center.setMinSize(0, 0);


        center.getChildren().add(canvas);
        canvas.widthProperty().bind(center.widthProperty());
        canvas.heightProperty().bind(center.heightProperty());

        lt = new LineTool(canvas);
        lines = new Stack<>();
        lines.push(lt);
        VBox tools = new VBox();

        Node undoRouteButton = lt.renderTool("Undo Route");
        undoRouteButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if (evt.isPrimaryButtonDown() && !lines.isEmpty()) {
                    lines.pop();
                    refreshScreen();
                }
            }
        });
        Node clearButton = lt.renderTool("Clear");
        clearButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if (evt.isPrimaryButtonDown() && !lines.isEmpty()) {
                    lines.clear();
                    lt.clear();
                    lines.push(lt);
                    refreshScreen();
                }
            }
        });
        Node undoPointButton = lt.renderTool("Undo Point");
        undoPointButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if (evt.isPrimaryButtonDown() && !lines.isEmpty()) {
                    if (lines.get(lines.size()-1).getPoints().size() == 0) {
                        lines.pop();
                    }
                    LineTool last;
                    if (!lines.isEmpty()) {
                        last = lines.get(lines.size()-1);
                        last.undoPoint();
                        if (last.getPoints().size() == 0) {
                            lines.pop();
                            System.err.println("POP LINE");
                        }
                        refreshScreen();
                    }
                }
            }
        });
        Node ltButton = lt.renderTool("Route");
        ltButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if (evt.isPrimaryButtonDown()) {
                    if (getMode() == MODES.DRAWING_MODE) {
                        setMode(MODES.ALL_OFF);

                    } else if (getMode() != MODES.DRAWING_MODE) {
                        setMode(MODES.DRAWING_MODE);
                        lt = new LineTool(canvas);
                        lines.push(lt);
                    }

                }
            }
        });
        //add player button
        Node addPlayerButton = lt.renderTool(("Add Player"));
        addPlayerButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if(evt.isPrimaryButtonDown()){
                    if(getMode() != MODES.STAMP_MODE){
                        if (getMode() == MODES.DRAWING_MODE) {

                        }
                        setMode(MODES.STAMP_MODE);
                    } else {
                        setMode(MODES.ALL_OFF);
                    }
                }
            }
        });


        tools.getChildren().add(ltButton);
        tools.getChildren().add(undoPointButton);
        tools.getChildren().add(undoRouteButton);
        tools.getChildren().add(clearButton);
        tools.getChildren().add(addPlayerButton);


        this.setRight(tools);
        this.setCenter(center);

        mode = MODES.ALL_OFF;

    }

    public boolean setMode(MODES newMode) {
        mode = newMode;
        return true;
    }

    public MODES getMode() {
        return mode;
    }

    public void refreshScreen() {

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(4);
        gc.strokeLine(0, canvas.getHeight() * 0.8, canvas.getWidth(), canvas.getHeight() * 0.8);

        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).render(canvas);
        }

    }

    public boolean line () {

        return true;
    }


    public class mouseHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent evt) {
            System.err.println("HERE");
            if (mode == MODES.DRAWING_MODE) {
                Point2D p = new Point2D(evt.getX(), evt.getY());
                lt.addPoint(p);
                lt.render(canvas);
                System.out.println("p");
            }
            if (mode == MODES.STAMP_MODE) {
                // try to make big oh smaller
                for (int i = 0; i < lines.size(); i++) {
                    LList<Point2D> pts = lines.get(i).getPoints();

                    Point2D point = null;
                    if (pts.size() != 0) {
                        point = pts.get(0);
                    }
                    if (point != null) {
                        double x = evt.getX();
                        double y = evt.getX();
                        if (x >= point.getX() &&
                                x <= point.getX() + lines.get(i).getPointRadius()*2 &&
                                y >= point.getY() &&
                                y <= point.getX() + lines.get(i).getPointRadius()*2) {
                            Sprite s = new Sprite(x, y, 0,0);
                            System.out.println("sprite created");

                        }
                    }



                }
                System.out.println("s");
            }
        }
    }
}
