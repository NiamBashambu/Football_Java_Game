package org.headroyce.dp1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class LineTool {
    private LList<Point2D> points;
    private Point2D p;

    private boolean mouseDown, mouseMove;
    private int selectedPoint, oldSelectedPoint;
    private static final int LINE_WIDTH = 2;
    private static final int POINT_RADIUS = 5;

    private String mode;

    private Canvas c;

    public LineTool(Canvas c){
        // setMode("draw");

        this.c = c;
        points = new LList<>();
        mode = null;
        // select(false);
    }

    public Node renderTool() {
        Button toolGUI = new Button("Route");
        return toolGUI;
    }

    public String getMode(){ return mode; }
    public boolean setMode( String newMode ){
        boolean rtn = false;
        if( newMode != null ){
            mode = newMode;
            rtn = true;
        }
        return rtn;
    }

    public boolean wasButtonPressed() {
        return false;
    }

    private class mouseHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent evt) {
            if(evt.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                Point2D p = new Point2D(evt.getX(), evt.getY());
                points.add(p);
            }

        }
    }


    /* public void mouseClick(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();


    }*/

    /* public boolean isMouseDown(Point2D m) {
        this.mouseDown = true;

    } */
}
