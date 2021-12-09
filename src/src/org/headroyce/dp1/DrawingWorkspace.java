package org.headroyce.dp1;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
    private Stack<Sprite> players;

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

        this.players = new Stack<>();

        Node undoRouteButton = lt.renderTool("Undo Route");
        undoRouteButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if (evt.isPrimaryButtonDown() && !lines.isEmpty()) {
                    if (!players.isEmpty()) {
                        for (int i = 0; i < players.size(); i++) {
                            Sprite s = players.get(i);
                            if (s.getX() + 2*lt.getPointRadius() == lines.get(lines.size() - 1).getPoints().get(0).getX() &&
                                    s.getY() + 2*lt.getPointRadius() == lines.get(lines.size() - 1).getPoints().get(0).getY()) {
                                players.remove(i);
                            }
                        }
                    }

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
                    if (!players.isEmpty()) {
                        players.clear();
                    }
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
                        if (last.getPoints().size() == 1) {
                            for (int i = 0; i < players.size(); i++) {
                                /* System.out.println(players.get(i).getX());
                                System.out.println(last.getPoints().get(0).getX());
                                System.out.println(players.get(i).getY());
                                System.out.println(last.getPoints().get(0).getY());*/
                                if (players.get(i).getX() + 2*lt.getPointRadius() == last.getPoints().get(0).getX() &&
                                        players.get(i).getY() + 2*lt.getPointRadius() == last.getPoints().get(0).getY()) {
                                    players.remove(i);
                                }
                            }
                        }
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
                        // find out why I made this if statement
                        if (getMode() == MODES.DRAWING_MODE) {

                        }
                        setMode(MODES.STAMP_MODE);
                    } else {
                        setMode(MODES.ALL_OFF);
                    }
                }
            }
        });
        Node undoPlayerButton = lt.renderTool(("Undo Player"));
        undoPlayerButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if(evt.isPrimaryButtonDown()){
                    if (!players.isEmpty()) {
                        players.pop();
                        refreshScreen();
                    }

                }
            }
        });


        tools.getChildren().add(ltButton);
        tools.getChildren().add(undoPointButton);
        tools.getChildren().add(undoRouteButton);
        tools.getChildren().add(clearButton);
        tools.getChildren().add(addPlayerButton);
        tools.getChildren().add(undoPlayerButton);

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

        for (int i = 0; i < players.size(); i++) {
            players.get(i).display(canvas);
        }

    }

    // try to make big oh smaller
    public boolean isSpotVacant (Point2D p) {
        if (players.size() == 0) {
            return true;
        }
        for (int i = 0; i < players.size(); i++) {
            System.out.println(p.getX() + ", " + p.getY());
            double playerX = players.get(i).getX() + 2*lines.get(0).getPointRadius();
            double playerY = players.get(i).getY() + 2*lines.get(0).getPointRadius();
            System.out.println((playerX) + ", " + (playerY));

            if (p.getX() == playerX && p.getY() == playerY) {
                System.out.println("occupied");
                return false;
            }
        }
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
                        double y = evt.getY();

                        System.out.println((x) + " and " + (point.getX() - lines.get(i).getPointRadius()));
                        System.out.println((x) + " and " + (point.getX() + lines.get(i).getPointRadius()));
                        System.out.println((y) + " and " + (point.getY() - lines.get(i).getPointRadius()));
                        System.out.println((y) + " and " + (point.getY() + lines.get(i).getPointRadius()));

                       if (x >= point.getX() - lines.get(i).getPointRadius() &&
                                x <= point.getX() + lines.get(i).getPointRadius() &&
                                y >= point.getY() - lines.get(i).getPointRadius() &&
                                y <= point.getY() + lines.get(i).getPointRadius() &&
                                isSpotVacant(point)) {
                            Receiver receiver = new Receiver(point.getX() - 2*lines.get(i).getPointRadius(), point.getY() - 2*lines.get(i).getPointRadius(), 0,0);
                            players.push(receiver);
                            System.out.println("sprite created");
                            refreshScreen();

                        }
                    }



                }
                System.out.println("s");
            }
        }
    }
}
