package minesweeper.Tests;

import javafx.embed.swing.JFXPanel;
import minesweeper.Grid;
import minesweeper.Tile;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static minesweeper.Main.BOMBS;
import static org.junit.Assert.assertEquals;

public class CornerTest {

    @Test
    public void getNeighborsReturnsCorrectNeighborsForCornerTile () throws Exception{
        JFXPanel jfxPanel = new JFXPanel();

        Grid grid = new Grid();
        grid.createGrid();
        List<List<Tile>> neighbors = new ArrayList<List<Tile>>();
        neighbors.add(grid.getNeighbors(new Tile(BOMBS-BOMBS, BOMBS-BOMBS, false))); //top left
        neighbors.add(grid.getNeighbors(new Tile(BOMBS-BOMBS, BOMBS-1, false))); //top right
        neighbors.add(grid.getNeighbors(new Tile(BOMBS-1, BOMBS-1, false))); //bottom right
        neighbors.add(grid.getNeighbors(new Tile(BOMBS-1, BOMBS-BOMBS, false))); // bottom left
        for (int i=0; i<=3; i++){
            System.out.println("Should find neighbors: 3" +"  Found neighbors: " + neighbors.get(i).size());
            assertEquals(neighbors.get(i).size(), 3);}

    }
}