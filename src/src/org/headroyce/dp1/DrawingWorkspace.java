package org.headroyce.dp1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Stack;

public class DrawingWorkspace extends BorderPane {
    private Canvas canvas;
    private LineTool lt;
    // private LineToolDB  ltDB;
    private GraphicsContext gc;
    private MODES mode;
    private Stack<LineTool> lines;
    private Stack<Sprite> immovables;
    private double canvasWidth;
    private double canvasHeight;
    private Node addPlayerButton;
    private Node ltButton;
    private Node ltDBButton;
    private Node addDefensivePlayerButton;
    private Stack<Sprite> DefensiveBacks;
    private Stack<Sprite> Linebackers;




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
       // Slider slider= new Slider();
       // VBox sliders = new VBox();

        System.out.println(canvas.widthProperty().intValue());

        this.immovables = new Stack<>();
        immovables.add(new Quarterback(canvas.widthProperty().intValue()/2, canvas.heightProperty().intValue() * 0.9, DrawingWorkspace.this));
        immovables.add(new OffensiveLineman(canvas.widthProperty().intValue() * 0.58, canvas.heightProperty().intValue() * 0.83, DrawingWorkspace.this));
        immovables.add(new OffensiveLineman(canvas.widthProperty().intValue()* 0.54, canvas.heightProperty().intValue() * 0.82, DrawingWorkspace.this));
        immovables.add(new OffensiveLineman(canvas.widthProperty().intValue()/2, canvas.heightProperty().intValue() * 0.81, DrawingWorkspace.this));
        immovables.add(new OffensiveLineman(canvas.widthProperty().intValue()* 0.46, canvas.heightProperty().intValue() * 0.82, DrawingWorkspace.this));
        immovables.add(new OffensiveLineman(canvas.widthProperty().intValue() * 0.42, canvas.heightProperty().intValue() * 0.83, DrawingWorkspace.this));
        immovables.add(new DefensiveLine(canvas.widthProperty().intValue() * 0.58, canvas.heightProperty().intValue() * 0.73, DrawingWorkspace.this));
        immovables.add(new DefensiveLine(canvas.widthProperty().intValue()* 0.54, canvas.heightProperty().intValue() * 0.72, DrawingWorkspace.this));
        immovables.add(new DefensiveLine(canvas.widthProperty().intValue()* 0.46, canvas.heightProperty().intValue() * 0.72, DrawingWorkspace.this));
        immovables.add(new DefensiveLine(canvas.widthProperty().intValue() * 0.42, canvas.heightProperty().intValue() * 0.73, DrawingWorkspace.this));


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
        ltButton = lt.OFrenderTool("Route");
        ltButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if (evt.isPrimaryButtonDown()) {
                    if (getMode() == MODES.DRAWING_MODE) {
                        ltButton.setStyle(null);

                        if (lt.getPoints().size() != 0) {
                            lines.push(lt);
                            System.out.println(lines.size());
                            //lt.clear();
                        }
                        setMode(MODES.ALL_OFF);

                    } else if (getMode() != MODES.DRAWING_MODE) {
                        ltButton.setStyle("-fx-background-color: darkgray");


                        setMode(MODES.DRAWING_MODE);
                        lt = new LineTool(canvas);
                    }

                }
            }
        });
        ltDBButton = lt.DFrenderTool("Defensive Route");
        ltDBButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if (evt.isPrimaryButtonDown()) {
                    if (getMode() == MODES.DRAWING_MODE) {
                        ltDBButton.setStyle(null);

                        if (lt.getPoints().size() != 0) {
                            lines.push(lt);
                            System.out.println(lines.size());
                            //lt.clear();
                        }
                        setMode(MODES.ALL_OFF);

                    } else if (getMode() != MODES.DRAWING_MODE) {
                        ltDBButton.setStyle("-fx-background-color: darkgray");

                        setMode(MODES.DRAWING_MODE);
                        lt = new LineTool(canvas);
                    }

                }

            }
        });
        addDefensivePlayerButton = lt.DFrenderTool("Add Defensive Player");
        addDefensivePlayerButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if(evt.isPrimaryButtonDown()){
                    if(getMode() != MODES.STAMP_MODE){
                        addDefensivePlayerButton.setStyle("-fx-background-color: darkgray");
                        if (getMode() == MODES.DRAWING_MODE) {
                            lines.push(lt);
                            ltDBButton.setStyle(null);

                        }
                        setMode(MODES.STAMP_MODE);
                    } else {
                        addDefensivePlayerButton.setStyle(null);

                        setMode(MODES.ALL_OFF);
                    }
                }
            }
        });
        //add player button
        addPlayerButton = lt.OFrenderTool("Add Player");
        addPlayerButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {

                if(evt.isPrimaryButtonDown()){
                    if(getMode() != MODES.STAMP_MODE){
                        addPlayerButton.setStyle("-fx-background-color: darkgray");
                        if (getMode() == MODES.DRAWING_MODE) {
                            lines.push(lt);
                            ltButton.setStyle(null);

                        }
                        setMode(MODES.STAMP_MODE);
                    } else {
                        addPlayerButton.setStyle(null);

                        setMode(MODES.ALL_OFF);
                    }
                }
            }
        });
       //add defense button
        MenuButton addDefenseButton = new MenuButton("Add Defense");
        addDefenseButton.setStyle("-fx-background-color: Red");
        addDefenseButton.setTextFill(Color.WHITE);
        MenuItem man1 = new MenuItem("Man One Deep");
        MenuItem man2 = new MenuItem("Man Two Deep");
        MenuItem zone1 = new MenuItem("Zone One Deep");
        MenuItem zone2 = new MenuItem("Zone Two Deep");
        this.DefensiveBacks = new Stack<>();
        this.Linebackers = new Stack<>();
        addDefenseButton.getItems().addAll(man1,man2,zone1,zone2);
        man1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DefensiveBacks.clear();
                Linebackers.clear();
                Linebackers.add(new Linebacker(canvas.widthProperty().intValue()*0.54, canvas.heightProperty().intValue() * 0.65, 0,0,DrawingWorkspace.this));
                Linebackers.add(new Linebacker(canvas.widthProperty().intValue()*0.46, canvas.heightProperty().intValue() * 0.65, 0,0,DrawingWorkspace.this));
                DefensiveBacks.add(new DefensiveBack(canvas.widthProperty().intValue()*0.5, canvas.heightProperty().intValue() * 0.2, 0,0,DrawingWorkspace.this));
                refreshScreen();








            }
        });
        man2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DefensiveBacks.clear();
                Linebackers.clear();
                Linebackers.add(new Linebacker(canvas.widthProperty().intValue()*0.54, canvas.heightProperty().intValue() * 0.65, 0,0,DrawingWorkspace.this));
                Linebackers.add(new Linebacker(canvas.widthProperty().intValue()*0.46, canvas.heightProperty().intValue() * 0.65, 0,0,DrawingWorkspace.this));
                DefensiveBacks.add(new DefensiveBack(canvas.widthProperty().intValue()*0.2, canvas.heightProperty().intValue() * 0.2, 0,0,DrawingWorkspace.this));
                DefensiveBacks.add(new DefensiveBack(canvas.widthProperty().intValue()*0.6, canvas.heightProperty().intValue() * 0.2, 0,0,DrawingWorkspace.this));
                refreshScreen();


            }
        });
        zone1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DefensiveBacks.clear();
                Linebackers.clear();
                Linebackers.add(new Linebacker(canvas.widthProperty().intValue()*0.54, canvas.heightProperty().intValue() * 0.65, 0,0,DrawingWorkspace.this));
                Linebackers.add(new Linebacker(canvas.widthProperty().intValue()*0.46, canvas.heightProperty().intValue() * 0.65, 0,0,DrawingWorkspace.this));
                DefensiveBacks.add(new DefensiveBack(canvas.widthProperty().intValue()*0.5, canvas.heightProperty().intValue() * 0.2, 0,0,DrawingWorkspace.this));
                refreshScreen();
            }
        });
        zone2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DefensiveBacks.clear();
                Linebackers.clear();
                Linebackers.add(new Linebacker(canvas.widthProperty().intValue()*0.54, canvas.heightProperty().intValue() * 0.65, 0,0,DrawingWorkspace.this));
                Linebackers.add(new Linebacker(canvas.widthProperty().intValue()*0.46, canvas.heightProperty().intValue() * 0.65, 0,0,DrawingWorkspace.this));
                DefensiveBacks.add(new DefensiveBack(canvas.widthProperty().intValue()*0.2, canvas.heightProperty().intValue() * 0.2, 0,0,DrawingWorkspace.this));
                DefensiveBacks.add(new DefensiveBack(canvas.widthProperty().intValue()*0.6, canvas.heightProperty().intValue() * 0.2, 0,0,DrawingWorkspace.this));
                refreshScreen();

            }
        });

        //undo player button
        // removes the last player created
        /*Node undoPlayerButton = lt.renderTool("Undo Player");
        undoPlayerButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if(evt.isPrimaryButtonDown()){
                    if (!lines.isEmpty()) {
                        lines.get(lines.size()-1).removePlayer();
                    } else {
                        lt.removePlayer();
                    }
                    System.out.println(lines.size());
                    System.out.println(lines.get(0).getPlayer());
                    refreshScreen();
                }
            }
        });*/

        Node runPlayButton = lt.renderTool("Run Play");
        runPlayButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (int i = 0; i < lines.size(); i++) {
                    if (lt.getPlayer() != null) {
                        lt.getPlayer().getST().play();
                    }
                    lt.getPlayer();
                    if (!lines.isEmpty()) {
                        if (lines.get(i).getPlayer() != null) {
                            lines.get(i).getPlayer().getST().play();
                        }
                    }
                }
            }
        });
        Node resetPlayButton = lt.renderTool("Reset Play");
        resetPlayButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (int i = 0; i < lines.size(); i++) {
                    Sprite s = lines.get(i).getPlayer();
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
        tools.getChildren().add(addDefenseButton);
        tools.getChildren().add(ltDBButton);
        tools.getChildren().add(addDefensivePlayerButton);
        // tools.getChildren().add(undoPlayerButton);
        tools.getChildren().add(runPlayButton);
        tools.getChildren().add(resetPlayButton);

        //sliders.getChildren().add(slider);

        this.setRight(tools);
       // this.setLeft(sliders);
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
    public Stack<Sprite> getImmovables() {
        return immovables;
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


        double[] xValues = new double[]{ 0.5, 0.58, 0.54, 0.5, 0.46, 0.42,0.58,0.54,0.46,0.42};
        double[] yValues = new double[]{0.9, 0.82, 0.81, 0.8, 0.81, 0.82,0.73,0.72,0.72,0.73};


        for (int i = 0; i < immovables.size(); i++) {
            immovables.get(i).setX(canvas.widthProperty().intValue()*xValues[i]);
            immovables.get(i).setY(canvas.heightProperty().intValue()*yValues[i]);
            immovables.get(i).display(canvas);
        }

        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).render(canvas);
        }
        double [] xVal = new double[]{0.5,0.2,0.4,0.5,};
        double [] yVal = new double[]{0.2,0.2,0.2,0.2,0.2,0.2};
        for(int i = 0; i < DefensiveBacks.size();i++){
            DefensiveBacks.get(i).setX(canvas.widthProperty().intValue()*xVal[i]);
            DefensiveBacks.get(i).setY(canvas.heightProperty().intValue()*yVal[i]);
            DefensiveBacks.get(i).display(canvas);
        }
        double [] xXVal = new double[]{0.54,0.46,0.54,0.46,0.54,0.46,0.54,0.46};
        double [] yYVal = new double[]{0.65,0.65,0.65,0.65,0.65,0.65,0.65,0.65};
        for(int i = 0; i < Linebackers.size();i++){
            Linebackers.get(i).setX(canvas.widthProperty().intValue()*xXVal[i]);
            Linebackers.get(i).setY(canvas.heightProperty().intValue()*yYVal[i]);
            Linebackers.get(i).display(canvas);
        }

    }

    // try to make big oh smaller
    /*public boolean isSpotVacant (LineTool lineTool) {
        for (int i = 0; i < lines.size(); i++) {

        }
        if (lineTool.isRouteVacant()) {
            return true;
        }
        return false;
    }*/

//putting the players on the created route
    public class mouseHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent evt) {
            System.err.println("HERE");
            if (mode == MODES.DRAWING_MODE) {
                Point2D p = new Point2D(evt.getX(), evt.getY());
                lt.addPoint(p);
                lt.render(canvas);
                LList<Point2D> pts = lt.getPoints();
                if (lt.getPlayer() != null) {
                    lt.getPlayer().getST().getChildren().clear();
                    lt.getPlayer().addRoute(pts);
                }
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
                            System.out.println("SPOT IS VACANT");
                            Receiver receiver = new Receiver(point.getX() - 2*current.getPointRadius(), point.getY() - 2*current.getPointRadius(), 1,1, DrawingWorkspace.this);

                           receiver.addRoute(pts);
                            current.addPlayer(receiver);
                           DefensiveBack defensiveBack = new DefensiveBack(point.getX() - 2*current.getPointRadius(), point.getY() - 2*current.getPointRadius(), 1,1, DrawingWorkspace.this);

                           defensiveBack.addRoute(pts);
                           current.addPlayer(defensiveBack);
                            System.out.println("sprite created");
                            // System.out.println(MainWorkspace.FramesPerSecond());
                            refreshScreen();
                        }
                    }



                }
                System.out.println("s");
            }
        }
    }
}
