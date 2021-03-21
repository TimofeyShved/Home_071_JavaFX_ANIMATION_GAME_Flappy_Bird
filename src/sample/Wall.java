package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.swing.*;


public class Wall extends Pane {
    Rectangle rectangle;
    public int height;

    public Wall(int height){
        this.height = height; // положение по вертикале на панеле
        rectangle = new Rectangle(40, height); // размер
        rectangle.setFill(Color.GREEN); // цвет
        getChildren().add(rectangle); // добавить на панель
    }
}
