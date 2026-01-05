package minesweeper;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    public static final int TILE_SIZE = 30;
    public static final int X_TILES = 10;
    public static final int Y_TILES = 10;
    public static final int BOMBS=10;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Grid.button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(new Scene(Grid.createGrid()));
            }
        });



        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(Grid.createGrid()));
        primaryStage.setResizable(false);
        primaryStage.show();





    }


    public static void main(String[] args) {
        launch(args);
    }
}
