package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.swing.*;


public class Wall extends Pane {
    Rectangle rectangle;
    public int height;

    public Wall(int height){
        this.height = height;
        rectangle = new Rectangle(40, height);
        rectangle.setFill(Color.GREEN);
        getChildren().add(rectangle);
    }
}
