package sample;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Bird extends Pane {

    // загружаем картинку игрока
    Image Img = new Image(getClass().getResourceAsStream("image/Bird.png"));
    ImageView imageView = new ImageView(Img);

    int count = 3; // количество спрайтов
    int columns = 3; // сколько вообще колонок
    int offsetX = 0; // откуда по осям
    int offsetY = 0;
    int width = 92; // размеры
    int height = 64;

    public SpriteAnimated animation; // ссылка для будущей анимации

    public Point2D velocity = new Point2D(0,0);
    //Rectangle rectangle;

    public Bird(){
        //rectangle = new Rectangle(20, 20, Color.RED);

        imageView.setFitHeight(40); // размеры картинки игрока
        imageView.setFitWidth(40);
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height)); // отрисовываем

        imageView.setTranslateY(0);
        imageView.setTranslateX(0);

        // добавляем анимацию
        animation = new SpriteAnimated(this.imageView, Duration.millis(200),count,columns,offsetX,offsetY,width,height);
        getChildren().addAll(this.imageView);// добавляем на панель
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
            System.out.println(getTranslateY());
            this.setTranslateY(getTranslateY()+(moveDown?1:-1));
        }
    }

    public void moveX(int value){
        for (int i=0; i<value; i++){
            for(Wall w: Main.walls){
                if(this.getBoundsInParent().intersects(w.getBoundsInParent())){
                    if (getTranslateX()+40==w.getTranslateX()) {
                        //setTranslateX(getTranslateX() - 1);
                        setTranslateX(200);
                        setTranslateY(0);
                        return;
                    }
                }
                if(getTranslateX()+40==w.getTranslateX()){
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
