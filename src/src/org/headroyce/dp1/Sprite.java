package org.headroyce.dp1;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Sprite {


    public Node node;

    private double x;
    private double y;

    private double vX;
    private double vY;

    private String type;

    private double h;
    private double w;

    public Sprite(double x, double y, double vX, double vY, double w, double h, String type) {
        this.x = x;
        this.y = y;
        this.vX = vX;
        this.vY = vY;
        this.type = type;
        this.w = w;
        this.h = h;
    }

    public void display(Canvas c) {
        GraphicsContext gc = c.getGraphicsContext2D();

        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, w, h);
        gc.setStroke(Color.WHITE);
        gc.strokeRect(x, y, w, h);
    }


    public void setX(double newX){
        this.x = newX;
    }
    public void setY(double newY){
        this.y = newY;
    }
    public double getX() {
        return x;
    }
    public double getY(){
        return y;
    }
    public void setH(double newH){
        this.h = newH;
    }
    public void setW(double newW){
        this.w = newW;
    }
    public double getH() {
        return h;
    }
    public double getW(){
        return w;
    }

    public Circle collisionBounds;


    public void update() {

    }


    public boolean collide(Sprite other) {

        if (collisionBounds == null || other.collisionBounds == null) {
            return false;
        }

        // determine it's size
        Circle otherSphere = other.collisionBounds;
        Circle thisSphere = collisionBounds;
        Point2D otherCenter = otherSphere.localToScene(otherSphere.getCenterX(), otherSphere.getCenterY());
        Point2D thisCenter = thisSphere.localToScene(thisSphere.getCenterX(), thisSphere.getCenterY());
        double dx = otherCenter.getX() - thisCenter.getX();
        double dy = otherCenter.getY() - thisCenter.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double minDist = otherSphere.getRadius() + thisSphere.getRadius();

        return (distance < minDist);
    }

    public void handleDeath(MainWorkspace gameWorld) {
        gameWorld.getSpriteManager().addSpritesToBeRemoved(this);
    }
}