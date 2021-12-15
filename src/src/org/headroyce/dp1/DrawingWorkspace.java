package org.headroyce.dp1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
//creating the lines and players and putting them in stacks
        lt = new LineTool(canvas);
        lines = new Stack<>();
        VBox tools = new VBox();

        this.players = new Stack<>();

        //undo route button
        // removes the last route created and any player attached to it
        Node undoRouteButton = lt.renderTool("Undo Route");
        undoRouteButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if (evt.isPrimaryButtonDown() && !lines.isEmpty()) {
                    /* if (!players.isEmpty()) {
                        for (int i = 0; i < players.size(); i++) {
                            Sprite s = players.get(i);
                            if (s.getX() + 2*lt.getPointRadius() == lines.get(lines.size() - 1).getPoints().get(0).getX() &&
                                    s.getY() + 2*lt.getPointRadius() == lines.get(lines.size() - 1).getPoints().get(0).getY()) {
                                players.remove(i);
                            }
                        }
                    }*/



                    if (getMode() != MODES.DRAWING_MODE) {
                        // lines.get(lines.size()-1).removePlayer();
                        System.out.println(lines.size());
                        lines.get(lines.size()-1).removePlayer();
                        lines.pop();
                        System.out.println(lines.size());
                        refreshScreen();
                    } else {
                        // lt.removePlayer();
                        lt.removePlayer();
                        // fix issue with last player not going away
                        lt = lines.pop();
                        refreshScreen();
                    }

                } else if (evt.isPrimaryButtonDown()) {
                    lt.clear();
                    refreshScreen();
                }
            }
        });
        //clear button
        // removes all players, points, and lines
        Node clearButton = lt.renderTool("Clear");
        clearButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if (evt.isPrimaryButtonDown()) {
                    if (!lines.isEmpty()) {
                        for (int i = 0; i < lines.size(); i++){
                            lines.get(i).removePlayer();
                        }
                        lines.clear();
                    }
                    lt.removePlayer();
                    lt.clear();
                    refreshScreen();
                }
            }
        });
        //undo point button
        // removes the last point created and any player attached to it
        Node undoPointButton = lt.renderTool("Undo Point");
        undoPointButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if (evt.isPrimaryButtonDown()) {
                    if (getMode() == MODES.DRAWING_MODE) {

                            if (lt.getPoints().size() == 1) {
                                lt.removePlayer();
                            }
                            if (lt.getPoints().size() == 0) {
                                if (!lines.isEmpty()) {
                                    lt = lines.pop();
                                }
                            }
                                lt.undoPoint();

                    } else {
                        if (!lines.isEmpty()) {
                            if (lines.get(lines.size()-1).getPoints().size() == 1) {
                                lines.get(lines.size()-1).removePlayer();
                            }
                            if (lines.get(lines.size()-1).getPoints().size() == 0) {
                                lt = lines.pop();
                            }
                            if (!lines.isEmpty()) {
                                lines.get(lines.size()-1).undoPoint();
                            }
                        }
                    }
                    refreshScreen();
                }
            }
        });
        //creating route button
        Node ltButton = lt.renderTool("Route");
        ltButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if (evt.isPrimaryButtonDown()) {
                    if (getMode() == MODES.DRAWING_MODE) {
                        if (lt.getPoints().size() != 0) {
                            lines.push(lt);
                            System.out.println(lines.size());
                            //lt.clear();
                        }
                        setMode(MODES.ALL_OFF);

                    } else if (getMode() != MODES.DRAWING_MODE) {
                        setMode(MODES.DRAWING_MODE);
                        lt = new LineTool(canvas);
                    }

                }
            }
        });
        //add player button
        Node addPlayerButton = lt.renderTool("Add Player");
        addPlayerButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if(evt.isPrimaryButtonDown()){
                    if(getMode() != MODES.STAMP_MODE){
                        if (getMode() == MODES.DRAWING_MODE) {
                            lines.push(lt);
                        }
                        setMode(MODES.STAMP_MODE);
                    } else {
                        setMode(MODES.ALL_OFF);
                    }
                }
            }
        });
        //undo player button
        // removes the last player created
        Node undoPlayerButton = lt.renderTool("Undo Player");
        undoPlayerButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if(evt.isPrimaryButtonDown()){
                    if (!lines.isEmpty()) {
                        lines.get(lines.size()-1).removePlayer();
                        refreshScreen();
                    }
                }
            }
        });

        Node runPlayButton = lt.renderTool("Run Play");
        runPlayButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).getST().play();
                }
            }
        });
        Node resetPlayButton = lt.renderTool("Reset Play");
        resetPlayButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (int i = 0; i < players.size(); i++) {
                    Sprite s = players.get(i);
                    s.setX(s.getPrevX());
                    s.setY(s.getPrevY());
                }
                refreshScreen();
            }
        });




        tools.getChildren().add(ltButton);
        tools.getChildren().add(undoPointButton);
        tools.getChildren().add(undoRouteButton);
        tools.getChildren().add(clearButton);
        tools.getChildren().add(addPlayerButton);
        tools.getChildren().add(undoPlayerButton);
        tools.getChildren().add(runPlayButton);
        tools.getChildren().add(resetPlayButton);

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

    public GraphicsContext getGC() {
        return gc;
    }

    public Stack<LineTool> getLines() {
        return lines;
    }
    public Stack<Sprite> getPlayers() {
        return players;
    }

    public void refreshScreen() {

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(4);
        gc.strokeLine(0, canvas.getHeight() * 0.8, canvas.getWidth(), canvas.getHeight() * 0.8);

        if (getMode() == MODES.DRAWING_MODE) {
            lt.render(canvas);
        } /*else if () {

        }*/

        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).render(canvas);
        }

    }

    // try to make big oh smaller
    public boolean isSpotVacant (LineTool lineTool) {
        if (players.size() == 0) {
            return true;
        }
        if (lineTool.isRouteVacant()) {
            return true;
        }
        return false;
    }

//putting the players on the created route
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
                    LineTool current = lines.get(i);
                    LList<Point2D> pts = current.getPoints();

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

                       if (x >= point.getX() - current.getPointRadius() &&
                                x <= point.getX() + current.getPointRadius() &&
                                y >= point.getY() - current.getPointRadius() &&
                                y <= point.getY() + current.getPointRadius()) {
                            if (isSpotVacant(current)) {
                                System.out.println("SPOT IS VACANT");
                                Receiver receiver = new Receiver(point.getX() - 2*current.getPointRadius(), point.getY() - 2*current.getPointRadius(), 1,1, DrawingWorkspace.this);
                                receiver.addRoute(pts);
                                current.addPlayer(receiver);
                                players.push(receiver);
                                System.out.println("sprite created");
                                System.out.println(MainWorkspace.FramesPerSecond());
                                refreshScreen();
                            }
                        }
                    }



                }
                System.out.println("s");
            }
        }
    }
}
