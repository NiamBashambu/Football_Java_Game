  package org.headroyce.dp1;

  import javafx.scene.image.Image;

  public class OffensiveLineman extends Sprite {
      private Image img;

     public OffensiveLineman(double x, double y){
         super(x,y,0,0);
         this.img = new Image("data:image/jpeg;base64,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", 100, 100, false, true);




     }
 }
