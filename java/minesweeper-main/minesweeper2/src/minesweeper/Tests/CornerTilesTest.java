package minesweeper.Tests;

import javafx.embed.swing.JFXPanel;
import minesweeper.Grid;
import minesweeper.Tile;
import org.junit.Test;

import java.util.*;

import static minesweeper.Main.BOMBS;
import static org.junit.Assert.assertEquals;

public class CornerTilesTest {

    @Test
    public void getNeighborsReturnsCorrectNeighborsForTile () throws Exception{
        JFXPanel jfxPanel = new JFXPanel();

        Grid grid = new Grid();
        grid.createGrid();
        List<Tile> neighbors = new ArrayList<Tile>();

        for (int i=1; i<=BOMBS-2; i++) {
            for (int j=1;j<=BOMBS-2;j++) {

                System.out.println("Should find neighbors: 8" + "  Found neighbors: " + grid.getNeighbors(new Tile (i,j,false)).size());
                assertEquals(grid.getNeighbors(new Tile (i,j,false)).size(), 8);
            }
        }
    }
}