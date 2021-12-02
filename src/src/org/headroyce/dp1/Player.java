package org.headroyce.dp1;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/*
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Player  {
    private final ImageView imageView;
    private final int count;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;
private int lastIndex;

    public Player  (

        ImageView imageView,
        Duration duration,
        int count,   int columns,
        int offsetX, int offsetY,
        int width,   int height) {
            this.imageView = imageView;
            this.count     = count;
            this.columns   = columns;
            this.offsetX   = offsetX;
            this.offsetY   = offsetY;
            this.width     = width;
            this.height    = height;
            setCycleDuration(duration);
            setInterpolator(Interpolator.LINEAR);
        }



    protected void interpolate(double k) {
            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if (index != lastIndex) {
                final int x = (index % columns) * width  + offsetX;
                final int y = (index / columns) * height + offsetY;
                imageView.setViewport(new Rectangle2D(x, y, width, height));
                lastIndex = index;
            }
        }


    }*/
public class Player{
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;

    public Player()
    {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
    }

    public void setImage(Image i)
    {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename)
    {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double x, double y)
    {
        positionX = x;
        positionY = y;
    }

    public void setVelocity(double x, double y)
    {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y)
    {
        velocityX += x;
        velocityY += y;
    }

    public void update(double time)
    {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, positionX, positionY );
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    public boolean intersects(Player s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    public String toString()
    {
        return " Position: [" + positionX + "," + positionY + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }
}





