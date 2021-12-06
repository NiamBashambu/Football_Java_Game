package org.headroyce.dp1;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;


public abstract class Sprite {

    /**
     * Current display node
     */
    public Node node;

    /**
     * velocity vector x direction
     */
    public double vX = 0;

    /**
     * velocity vector y direction
     */
    public double vY = 0;

    /**
     * dead?
     */
    public boolean isDead = false;

    /**
     * collision shape
     */
    public Circle collisionBounds;

    /**
     * Updates this sprite object's velocity, or animations.
     */
    public abstract void update();

    /**
     * Did this sprite collide into the other sprite?
     *
     * @param other - The other sprite.
     * @return boolean - Whether this or the other sprite collided, otherwise false.
     */
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