package org.headroyce.dp1;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

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

    public Node renderTool() {
        Button toolGUI = new Button("Line");
        return toolGUI;
    }

    /* public boolean isMouseDown(Point2D m) {
        this.mouseDown = true;

    } */
}
