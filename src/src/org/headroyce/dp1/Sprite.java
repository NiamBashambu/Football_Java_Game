package org.headroyce.dp1;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;


public class Sprite {


    public Node node;

    private double x;
    private double y;

    private double vX;
    private double vY;

    private Image img;

    public Sprite(double x, double y, double vX, double vY, Image img) {
        this.x = x;
        this.y = y;
        this.vX = vX;
        this.vY = vY;
        this.img = img;
    }


    public void setX(double newX){
        this.x = x;
    }
    public void setY(double newY){
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public double getY(){
        return y;
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