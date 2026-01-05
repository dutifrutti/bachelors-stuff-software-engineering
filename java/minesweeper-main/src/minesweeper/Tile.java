package minesweeper;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static minesweeper.Main.TILE_SIZE;

public class Tile extends StackPane {
    public int x,y;
    public boolean hasBomb;
    public boolean isOpen = false;

    private Rectangle border = new Rectangle(TILE_SIZE -1, TILE_SIZE -1);
    public Text text = new Text();
    public Tile(int x, int y, boolean hasBomb) {
        this.x = x;
        this.y = y;
        this.hasBomb = hasBomb;


        border.setStroke(Color.WHITE);
        border.setFill(Color.LIGHTGRAY);
        text.setFont(Font.font(20));
        text.setFill(Color.BLACK);
        text.setVisible(false);

        if (hasBomb){
            text.setText("X");
        }
        else{
            text.setText("");
        }
        getChildren().addAll(border,text);

        setTranslateX(x*TILE_SIZE);
        setTranslateY(y*TILE_SIZE);

        setOnMouseClicked(event -> open());

    }

    public void open (){

        if (isOpen)
            return;

            else {
            isOpen = true;
            text.setVisible(true);
            border.setFill(Color.DARKGRAY);
            if(text.getText().equals("X")) {

                System.out.println("gameover");
                Grid.gameOver();
            }
            if (text.getText().isEmpty()) {
                Grid.getNeighbors(this).forEach(Tile::open);
            }
            }

    }

    public void gameOver(Tile tile){
        tile.isOpen=true;
    }

}
