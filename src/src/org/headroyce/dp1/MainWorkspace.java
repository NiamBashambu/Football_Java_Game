/*package org.headroyce.dp1;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

//thing to manager the game timer and the sprite manager and sprites
public abstract class MainWorkspace {


    private Scene gameSurface;

    private Group sceneNodes;

    private static Timeline gameLoop;


    private final int framesPerSecond;


    private final String windowTitle;


    private final SpriteManager spriteManager = new SpriteManager();




    public MainWorkspace(final int fps, final String title) {
        framesPerSecond = fps;
        windowTitle = title;
        // create and set timeline for the game loop
        buildAndSetGameLoop();
    }


    protected final void buildAndSetGameLoop() {

        final Duration oneFrameAmt = Duration.millis(1000 / (float) getFramesPerSecond());
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
                new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {

                        // update sprites
                        updateSprites();

                        // check for collision
                        checkCollisions();

                        // removed dead things
                        cleanupSprites();

                    }
                }); // oneFrame

        // sets the game world's game loop (Timeline)
    }

    public abstract void initialize(final Stage primaryStage);


    public void beginGameLoop() {
        getGameLoop().play();
    }

    protected void updateSprites() {
        for (Sprite sprite : spriteManager.getAllSprites()) {
            handleUpdate(sprite);
        }
    }


    protected void handleUpdate(Sprite sprite) {
    }

//check collisions for sprites
    protected void checkCollisions() {
        // check other sprite's collisions
        spriteManager.resetCollisionsToCheck();
        // check each sprite against other sprite objects.
        for (Sprite spriteA : spriteManager.getCollisionsToCheck()) {
            for (Sprite spriteB : spriteManager.getAllSprites()) {
                if (handleCollision(spriteA, spriteB)) {
                    // The break helps optimize the collisions
                    //  The break statement means one object only hits another
                    // object as opposed to one hitting many objects.
                    // To be more accurate comment out the break statement.
                    break;
                }
            }
        }
    }


    protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
        return false;
    }


    protected void cleanupSprites() {
        spriteManager.cleanupSprites();
    }


   public  int getFramesPerSecond() {
        return FramesPerSecond();
    }

    public static int FramesPerSecond() {
        return 60;
    }


    public String getWindowTitle() {
        return windowTitle;
    }

//get the game timer stuff
    protected static Timeline getGameLoop() {
        return gameLoop;
    }


    protected static void setGameLoop(Timeline gameLoop) {
        MainWorkspace.gameLoop = gameLoop;
    }


    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public Scene getGameSurface() {
        return gameSurface;
    }


    protected void setGameSurface(Scene gameSurface) {
        this.gameSurface = gameSurface;
    }


    public Group getSceneNodes() {
        return sceneNodes;
    }


    protected void setSceneNodes(Group sceneNodes) {
        this.sceneNodes = sceneNodes;
    }




    public void shutdown() {
        getGameLoop().stop();

    }
}*/