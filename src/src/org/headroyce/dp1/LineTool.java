package org.headroyce.dp1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class LineTool {
    private LList<Point2D> points;
    private Point2D p;

    private boolean mouseDown, mouseMove;
    private int selectedPoint, oldSelectedPoint;
    private static final int LINE_WIDTH = 2;
    private static final int POINT_RADIUS = 5;

    private Canvas c;


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

    public int getPointRadius() {
        return POINT_RADIUS;
    }

    public void clear(){
        points.clear();
    }

    public LList<Point2D> getPoints() {
        return points;
    }

    public boolean undoPoint() {
        points.remove(points.size()-1);
        return true;
    }




    /* public boolean wasButtonPressed() {
        return false;
    }*/
    public boolean addPoint( Point2D p ){
        points.add(p);
        return true;
    }

    public boolean render (Canvas c) {

        GraphicsContext gc =  c.getGraphicsContext2D();
        gc.setFill(Color.RED);

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
