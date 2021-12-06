package org.headroyce.dp1;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;


public abstract class Sprite {


    public Node node;

    private double x;
<<<<<<< HEAD
    private double y;

    private double vX = 0;
    private double vY = 0;

=======
  private  double y;
    private double vX = 0;
>>>>>>> e90be18ae42a31c44ce12482c8063a2ef2c0b4f8


    private double vY = 0;

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


    public abstract void update();


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