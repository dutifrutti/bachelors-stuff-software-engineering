package minesweeper;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static minesweeper.Main.*;

public class Grid {
    private boolean[][] temp1 = new boolean[X_TILES][Y_TILES];
    private Tile[][] grid = new Tile [X_TILES][Y_TILES];
    public static Button button = new Button("Play again");
    private boolean [][] bombLoc = new boolean[X_TILES][Y_TILES];

    public   AnchorPane createGrid() {

        AnchorPane root = new AnchorPane();
        root.setPrefSize(X_TILES * TILE_SIZE, Y_TILES * TILE_SIZE+40);

        //get random bomb coordinates
        generateBombCoordinates();

        // populate grid with bombs and tiles
       populateGrid(root);

        //count nearby bombs
        countNearbyBombs();

        button.setPrefWidth(300);
        root.getChildren().add(button);
        AnchorPane.setBottomAnchor(button,5.00);

        return root;
        }

    private void countNearbyBombs() {
        for (int x = 0; x < X_TILES; x++) {
            for (int y = 0; y < Y_TILES; y++) {
                Tile tile = grid[x][y];
                if(tile.hasBomb())
                    continue;
                int bombs = (int) getNeighbors(tile).stream().filter(Tile::hasBomb).count();
                if (bombs > 0)
                    tile.getText().setText(String.valueOf(bombs));
            }
        }
    }

    private void populateGrid(AnchorPane root) {
        for (int x = 0; x < X_TILES; x++) {
            for (int y = 0; y < Y_TILES; y++) {
                Tile tile = new Tile(x, y, bombLoc[x][y]);
                grid[x][y]= tile;
                root.getChildren().add(tile);

                play (tile);

            }
        }
    }
    private void play (Tile tile){
        if(tile.hasBomb()) {
            tile.setOnMouseClicked(e-> gameOver(tile));
        }
        else{
            tile.setOnMouseClicked(e->reveal(tile));
        }
    }

    private void reveal(Tile tile){
        if (tile.isRevealed())
            return;
        tile.setRevealed(true);
        tile.getTileBorder().setFill(Color.DARKGRAY);
        tile.getText().setVisible(true);
        if (tile.getTextString().isEmpty()) {
            getNeighbors(tile).forEach(this::reveal);

        }
    }

    public List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();
        int[] points = new int[]{
                -1,-1,
                -1,0,
                -1,1,
                0,-1,
                0,1,
                1,-1,
                1,0,
                1,1
        };
        for (int i=0;i<points.length;i++){
            int dx = points[i];
            int dy = points[i=i+1];

            int newX = tile.getX() + dx;
            int newY = tile.getY() + dy;
            if (newX >= 0 && newX < X_TILES &&
                    newY>=0 && newY < Y_TILES)  {
                neighbors.add(grid[newX][newY]);
            }
        }
        return neighbors;
    }

    public void generateBombCoordinates() {
        Random rand = new Random();
        for (int i = 0; i < BOMBS; i++) {
            int r1 = rand.nextInt(X_TILES);
            int r2 = rand.nextInt(Y_TILES);
            temp1[r1][r2] = true;
            if (temp1[r1][r2]!=bombLoc[r1][r2]){
                bombLoc[r1][r2]=true;

            }
            else {
                i--;
            }
        }

    }

        private void gameOver(Tile tile){
            System.out.println("game over thank you please come again");
            for (int x = 0; x < X_TILES; x++) {
                for (int y = 0; y < Y_TILES; y++) {
                    if (grid[x][y].getTextString().equals("X")){
                        grid[x][y].getTileBorder().setFill(Color.DEEPPINK);
                        grid[x][y].getText().setVisible(true);
                    }
                    grid[x][y].setOnMouseClicked(event -> {});
                }
            }
            tile.getTileBorder().setFill(Color.RED);
    }
}



