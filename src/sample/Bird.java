package sample;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Bird extends Pane {

    public Point2D velocity = new Point2D(0,0);
    Rectangle rectangle;

    public Bird(){
        rectangle = new Rectangle(20, 20, Color.RED);
        setTranslateY(300);
        setTranslateX(100);
        getChildren().addAll(rectangle);
    }

    public void moveY(int value){
        boolean moveDown = value>0?true:false;
        for (int i=0; i<Math.abs(value); i++){
            for(Wall w: Main.walls){
                if(this.getBoundsInParent().intersects(w.getBoundsInParent())){
                    if (moveDown){
                        setTranslateY(getTranslateY()-1);
                        return;
                    }else {
                        setTranslateY(getTranslateY()+1);
                        return;
                    }
                }
            }

            if (getTranslateY()<0){
                setTranslateY(0);
            }
            if (getTranslateY()>580){
                setTranslateY(580);
            }
            this.setTranslateY(getTranslateY()+(moveDown?1:-1));
        }
    }

    public void moveX(int value){
        for (int i=0; i<value; i++){
            for(Wall w: Main.walls){
                if(this.getBoundsInParent().intersects(w.getBoundsInParent())){
                    if (getTranslateX()+20==w.getTranslateX()) {
                        setTranslateX(getTranslateX() - 1);
                        return;
                    }
                }
                if(getTranslateX()+20==w.getTranslateX()){
                    Main.score+=5;
                }
            }
            this.setTranslateX(getTranslateX()+1);
        }
    }

    public void jump(){
        velocity = new Point2D(3,-15);
    }
}
