package org.headroyce.dp1;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Stack;

//class sprite that is the main building blocks for each player
public class Sprite {


    public Node node;

    private double x;
    private double y;

    private double prevX;
    private double prevY;

    private double vX;
    private double vY;

    private String type;

    private double h;
    private double w;

    //private Timeline timeline;
    private SequentialTransition st;
    private Canvas canvas;

    public final Double pointRadius;

    private DrawingWorkspace dw;


    public Sprite(double x, double y, double vX, double vY, double w, double h, String type, double prevX, double prevY, DrawingWorkspace dw) {
        this.x = x;
        this.y = y;
        this.prevX = prevX;
        this.prevY = prevY;
        this.vX = vX;
        this.vY = vY;
        this.type = type;
        this.w = w;
        this.h = h;
        // this.timeline = new Timeline();
        this.st = new SequentialTransition();
        this.pointRadius = 5.0;

        this.dw = dw;
    }

    public void display(Canvas c) {
        GraphicsContext gc = c.getGraphicsContext2D();

        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, w, h);
        gc.setStroke(Color.WHITE);
        gc.strokeRect(x, y, w, h);
        gc.setStroke(Color.RED);
        gc.setFont(new Font("Arial", 15));
        gc.setLineWidth(1);
        gc.strokeText(type,x,y-3);
        gc.setLineWidth(4);


        System.err.println("PLAYER REMDER");

        this.canvas = c;
    }

    public SequentialTransition getST() {
        return st;
    }

    public void addRoute(LList<Point2D> points) {
        for (int i = 1; i < points.size(); i++) {
            Point2D pt = points.get(i);
            Point2D prev = points.get(i-1);
            Double ptX = pt.getX();
            Double ptY = pt.getY();
            Double prevX = prev.getX();
            Double prevY = prev.getY();
            Double xDiff = ptX - prevX;
            Double yDiff = ptY - prevY;
            Double dist = Math.sqrt((xDiff*xDiff) + (yDiff*yDiff));
            WritableValue<Double> xWritable = new WritableValue<Double>() {
                @Override
                public Double getValue() {
                    return x;
                }

                @Override
                public void setValue(Double aDouble) {
                    // setPrevX(x);
                    x = aDouble;
                    // display(canvas);
                    dw.refreshScreen();
                }
            };
            WritableValue<Double> yWritable = new WritableValue<Double>() {
                @Override
                public Double getValue() {
                    return y;
                }

                @Override
                public void setValue(Double aDouble) {
                    // setPrevY(y);
                    y = aDouble;
                    // display(canvas);
                    dw.refreshScreen();
                }
            };
            // Double endValue = 0.0;
            KeyValue kfX = new KeyValue(xWritable, ptX - 2*pointRadius);
            KeyValue kfY = new KeyValue(yWritable, ptY - 2*pointRadius);
            EventHandler<ActionEvent> onFinished = new EventHandler<>() {
                @Override
                public void handle(ActionEvent event) {
                    //display(canvas);
                    System.out.println("KeyFrame Happened");
                }
            };
            Duration time = new Duration(10*dist/(Math.sqrt(vX*vX + vY*vY)));
            Timeline tl = new Timeline();
            tl.getKeyFrames().add(new KeyFrame(time, onFinished, kfX, kfY));
            st.getChildren().add(tl);
        }


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
    public double getPrevX() {
        return prevX;
    }
    public double getPrevY(){
        return prevY;
    }
    public void setPrevX(double newPrevX){
        this.prevX = newPrevX;
    }
    public void setPrevY(double newPrevY){
        this.prevY = newPrevY;
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

//collision class for the sprites
    public boolean collide(Sprite other) {

        if (collisionBounds == null || other.collisionBounds == null) {
            return false;
        }

        // determine its size
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
/*
    public void handleDeath(MainWorkspace gameWorld) {
        gameWorld.getSpriteManager().addSpritesToBeRemoved(this);
    }*/
}