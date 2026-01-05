package minesweeper;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.junit.Test;

import static minesweeper.Main.TILE_SIZE;

public class Tile extends StackPane {
    private int x,y;
    private boolean hasBomb;
    private boolean revealed = false;

    private Rectangle border = new Rectangle(TILE_SIZE -1, TILE_SIZE -1);
    private Text text = new Text();
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

    }

    public Rectangle getTileBorder() {
        return border;
    }

    public void setBorder(Rectangle border) {
        this.border = border;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public void setBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
    }

    public String getTextString() {
        return text.getText();
    }
    public Text getText(){
        return text;
    }

    public void setText(String text) {
        this.text = new Text(text);
    }
}
