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

    public Bird(){
        imageView.setFitHeight(40); // размеры картинки игрока
        imageView.setFitWidth(40);
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height)); // отрисовываем

        imageView.setTranslateY(0); // место на карте
        imageView.setTranslateX(0);

        // добавляем анимацию
        animation = new SpriteAnimated(this.imageView, Duration.millis(200),count,columns,offsetX,offsetY,width,height);
        getChildren().addAll(this.imageView);// добавляем на панель
    }

    // -------------------------------------------------- передвижение по оси У --------------
    public void moveY(int value){
        boolean moveDown = value>0?true:false; // приходящее значение > 0, true - вниз, иначе вверх
        for (int i=0; i<Math.abs(value); i++){
            for(Wall w: Main.walls){

                // если наша картинка соприкасается размерами с квадратом, то...
                if(this.getBoundsInParent().intersects(w.getBoundsInParent())){
                    if (moveDown){ // если движение вниз
                        setTranslateY(getTranslateY()-1); // сдвинуть вверх на 1
                        return;  // завершить действие в этом методе
                    }else {
                        setTranslateY(getTranslateY()+1); // сдвинуть вниз на 1
                        return;  // завершить действие в этом методе
                    }
                }
            }

            if (getTranslateY()<0){ // не дайть выйти за край карты
                setTranslateY(0); // сверху
            }
            if (getTranslateY()>580){ // и снизу
                setTranslateY(580);
            }
            this.setTranslateY(getTranslateY()+(moveDown?1:-1)); // куда движение, вниз? значит падаем! =О
        }
    }

    // -------------------------------------------------- передвижение по оси Х --------------
    public void moveX(int value){
        for (int i=0; i<value; i++){
            for(Wall w: Main.walls){
                if(this.getBoundsInParent().intersects(w.getBoundsInParent())){
                    if (getTranslateX()+40==w.getTranslateX()) {
                        //setTranslateX(getTranslateX() - 1); // не даёт соприкоснуться, останавливает на месте
                        setTranslateX(200); // обнулить всё! (Т.Т) Нельзя соприкосаться!
                        setTranslateY(0);
                        Main.score=0; // сбрасывает счёт
                        return;  // завершить действие в этом методе
                    }
                }
                if(getTranslateX()+40==w.getTranslateX()){ // если прошли мимо них
                    Main.score++; // прибавить счёт
                }
            }
            this.setTranslateX(getTranslateX()+1); // двигать всё время вперед
        }
    }

    public void jump(){ // прыжок
        velocity = new Point2D(3,-20); // поднять точку вверх
    }
}
