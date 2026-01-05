package minesweeper;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import minesweeper.Tests.BombTest;

import java.sql.SQLOutput;


public class Main extends Application {
    public static final int TILE_SIZE = 30;
    public static final int X_TILES = 10;
    public static final int Y_TILES = 10;
    public static  final int BOMBS=10;
    Grid grid = new Grid();
    @Override
    public void start(Stage primaryStage) {

        Grid.button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                grid = new Grid();
                primaryStage.setScene(new Scene(grid.createGrid()));

                BombTest bombTest = new BombTest();
                 try {
                    bombTest.checkBombCountReturnsCorrectBombCount();
                } catch (Exception exception) {
                    System.out.println("failed bomb count test, bombs:"+BOMBS );
                }
            }
        });

        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(grid.createGrid()));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
