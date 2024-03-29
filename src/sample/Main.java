package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    // картинка на задний фон
    Image backgroundImg = new Image(getClass().getResourceAsStream("image/background.png"));
    // выбор картинки на задний фон (отображение)
    ImageView backgroundIV = new ImageView(backgroundImg);

    // главная панель игры
    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane(); // игровая панель

    // коллекция труб (стен)
    public  static ArrayList<Wall> walls = new ArrayList<>();

    // создаём птичку
    Bird bird = new Bird();
    public static int score = 0; // пройденный результат
    public Label scoreLable = new Label("Score - "+score);

    // ------------------------------------------------------------ иницализация, при создании панели ----------------------
    public Parent createContent(){
        gameRoot.setPrefSize(600,600);

        // создание стен (труб)
        for (int i=0; i<100; i++){
            int enter = (int)(Math.random()*100+100);// рандом от 150-300
            int height = new Random().nextInt(600-enter); // его место на панели

            Wall wall= new Wall(height); // его место на панели
            wall.setTranslateX(i*359+600); // растояние между ними
            wall.setTranslateY(0); // от куда? сверху вниз
            walls.add(wall);

            Wall wall2= new Wall(600-enter-height);
            wall2.setTranslateX(i*359+600);
            wall2.setTranslateY(height+enter);
            walls.add(wall2);

            gameRoot.getChildren().addAll(wall, wall2); // добавление на форму
        }

        backgroundIV.setFitHeight(600); // размеры картинки на задний фон
        backgroundIV.setFitWidth(600);

        gameRoot.getChildren().addAll(bird);
        appRoot.getChildren().addAll(backgroundIV, gameRoot);
        return appRoot;
    }

    // -------------------------------------------------------------------- обработка действий ------------------------------
    public void update(){

        // если его вектора по высоте меньше 5, присвоить 1, что бы движение было вниз
        if (bird.velocity.getY()<5){
            bird.velocity = bird.velocity.add(0,1);
        }

        // все движения
        bird.moveX((int)bird.velocity.getX());
        bird.moveY((int)bird.velocity.getY());
        scoreLable.setText("Score - "+score);

        //добавляем действие птичку, если изменяется её занчение по оси Х
        bird.translateXProperty().addListener((observable, oldValue, newValue) -> {
            int offset = newValue.intValue(); // принимаем новое значение Х
            if(offset>200)gameRoot.setLayoutX(-(offset-200)); // если Х больше 200, заставляем всю панель двигаться налево
        });

        // анимация взмаха крыльев
        bird.animation.play();
    }

    // -------------------------------------------------------------------- запуск программы----------------------------
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(createContent()); // наша сцена и инициализация контента
        scene.setOnMouseClicked(event -> bird.jump()); // обработка событий по нажатию

        primaryStage.setScene(scene); // загружаем сцены на форму
        primaryStage.show(); // отображаем

        // запуск таймера
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(); // обновление, это метод выше
                primaryStage.setTitle("Flappy Bird Game | Score - "+(score));
            }
        };
        timer.start(); // запуск таймера
    }


    public static void main(String[] args) {
        launch(args);
    }
}
