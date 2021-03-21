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

    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();

    public  static ArrayList<Wall> walls = new ArrayList<>();
    Bird bird = new Bird();
    public static int score = 0;
    public Label scoreLable = new Label("Score - "+score);

    public Parent createContent(){
        gameRoot.setPrefSize(600,600);

        for (int i=0; i<100; i++){
            int enter = (int)(Math.random()*100+100);//50-150
            int height = new Random().nextInt(600-enter);

            Wall wall= new Wall(height);
            wall.setTranslateX(i*359+600);
            wall.setTranslateY(0);
            walls.add(wall);

            Wall wall2= new Wall(600-enter-height);
            wall2.setTranslateX(i*359+600);
            wall2.setTranslateY(height+enter);
            walls.add(wall2);

            gameRoot.getChildren().addAll(wall, wall2);
        }

        backgroundIV.setFitHeight(600); // размеры картинки на задний фон
        backgroundIV.setFitWidth(600);

        gameRoot.getChildren().addAll(bird);
        appRoot.getChildren().addAll(backgroundIV, gameRoot);
        return appRoot;
    }

    public void update(){

        if (bird.velocity.getY()<5){
            bird.velocity = bird.velocity.add(0,1);
        }

        bird.moveX((int)bird.velocity.getX());
        bird.moveY((int)bird.velocity.getY());
        scoreLable.setText("Score - "+score);

        bird.translateXProperty().addListener((observable, oldValue, newValue) -> {
            int offset = newValue.intValue();
            if(offset>200)gameRoot.setLayoutX(-(offset-200));
        });
        bird.animation.play();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(createContent());
        scene.setOnMouseClicked(event -> bird.jump());

        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setFill(Color.AQUA);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                primaryStage.setTitle("Flappy Bird Game | Score - "+(score/10));
            }
        };
        timer.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
