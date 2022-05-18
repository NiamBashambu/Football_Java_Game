package org.headroyce.dp1;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class LineTool {
    private LList<Point2D> points;
    private Point2D p;

    private boolean mouseDown, mouseMove;
    private int selectedPoint, oldSelectedPoint;
    private static final int LINE_WIDTH = 2;
    private static final int POINT_RADIUS = 5;

    private Canvas c;

    private Sprite sprite;
    private DefensiveBack defensiveBack;


    public LineTool(Canvas c){
        // setMode("draw");

        this.c = c;
        points = new LList<>();
        // select(false);
    }
    public Node renderTool(String s) {
        Button toolGUI = new Button(s);

        return toolGUI;
    }
    public Node OFrenderTool(String s) {
        Button toolGUI = new Button(s);
        toolGUI.setStyle("-fx-background-color: Blue");
        toolGUI.setTextFill(Color.WHITE);

        return toolGUI;
    }
    public Node DFrenderTool(String s) {
        Button toolGUI = new Button(s);
        toolGUI.setStyle("-fx-background-color: Red");
        toolGUI.setTextFill(Color.WHITE);
        return toolGUI;
    }

    public int getPointRadius() {
        return POINT_RADIUS;
    }

    public void clear(){
        points.clear();
    }

    public void addPlayer(Sprite s) {
        sprite = s;
    }
    public void addDBPlayer (DefensiveBack db){defensiveBack = db;}
    public void removePlayer() {
        sprite = null;
    }
    public void removeDBPlayer() {
        defensiveBack = null;
    }
    public Sprite getPlayer() {
        return sprite;
    }
    public DefensiveBack getDBPlayer() {
        return defensiveBack;
    }
    public boolean isRouteVacant() {
        if (sprite == null) {
            return true;
        }
        return false;
    }
    public boolean isDBRouteVacant() {
        if (defensiveBack == null) {
            return true;
        }
        return false;
    }

    public LList<Point2D> getPoints() {
        return points;
    }

    public boolean undoPoint() {
        if (points.size() != 0){
            points.remove(points.size()-1);
        }
        return true;
    }




    /* public boolean wasButtonPressed() {
        return false;
    }*/
    public boolean addPoint( Point2D p ){
        points.add(p);
        return true;
    }

    /* public void refresh() {
        if (sprite != null) {
            sprite.display(c);
        }
    }*/

    public boolean render (Canvas c) {
        GraphicsContext gc =  c.getGraphicsContext2D();
        gc.setFill(Color.RED);
        if (points.size()==0) {
            System.out.println("no points");
        }
        for (int i = 0; i < points.size(); i++) {
            Point2D p = points.get(i);
            if (i != 0) {
                gc.setFill(Color.WHITE);
            }
            gc.setStroke(Color.BLACK);
            gc.strokeOval(p.getX()-POINT_RADIUS, p.getY()-POINT_RADIUS, 2*POINT_RADIUS, 2*POINT_RADIUS);
            gc.fillOval(p.getX()-POINT_RADIUS, p.getY()-POINT_RADIUS, 2*POINT_RADIUS, 2*POINT_RADIUS);
            if (i != 0) {
                Point2D p2 = points.get(i-1);
                gc.setStroke(Color.LIGHTGREEN);
                gc.strokeLine(p2.getX(), p2.getY(), p.getX(), p.getY());
            }
        }
        if (sprite != null) {
            sprite.display(c);
        }
        if (defensiveBack != null) {
            defensiveBack.display(c);
        }
        return true;
    }





    /* public void mouseClick(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();


    }*/

    /* public boolean isMouseDown(Point2D m) {
        this.mouseDown = true;

    } */
}
