package minesweeper;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static minesweeper.Main.*;

public class Grid {
    private static boolean[][] temp1 = new boolean[BOMBS][BOMBS];
    private static Tile[][] grid = new Tile [X_TILES][Y_TILES];
    public static Button button = new Button("Play again");

    public  static AnchorPane createGrid() {
        boolean [][] bombLoc = new boolean[BOMBS][BOMBS];
        AnchorPane root = new AnchorPane();
        root.setPrefSize(X_TILES * TILE_SIZE, Y_TILES * TILE_SIZE+40);
        Random rand = new Random();
        //get random bomb coordinates
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
        // populate grid with bombs and tiles
        for (int x = 0; x < X_TILES; x++) {
            for (int y = 0; y < Y_TILES; y++) {
                Tile tile = new Tile(x, y, bombLoc[x][y]);
                grid[x][y]= tile;
                root.getChildren().add(tile);

            }
        }
        //count nearby bombs
        for (int x = 0; x < X_TILES; x++) {
            for (int y = 0; y < Y_TILES; y++) {
                Tile tile = grid[x][y];
                if(tile.hasBomb)
                    continue;

            int bombs = (int) getNeighbors(tile).stream().filter(t -> t.hasBomb).count();
            if (bombs > 0)
            tile.text.setText(String.valueOf(bombs));

            }
        }


        button.setPrefWidth(300);
        root.getChildren().add(button);
        AnchorPane.setBottomAnchor(button,5.00);


        return root;
        }

    public static List<Tile> getNeighbors(Tile tile) {
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

            int newX = tile.x + dx;
            int newY = tile.y + dy;
            if (newX >= 0 && newX < X_TILES &&
                    newY>=0 && newY < Y_TILES)  {
                neighbors.add(grid[newX][newY]);
            }
        }
        return neighbors;}

        public static void gameOver (){
            for (int x = 0; x < X_TILES; x++) {
                for (int y = 0; y < Y_TILES; y++) {
                    if (grid[x][y].text.getText().equals("X")){
                        grid[x][y].text.setVisible(true);
                                            }
                    grid[x][y].gameOver(grid[x][y]);


                }
            }

        }



    }



