package minesweeper.Tests;

        import javafx.embed.swing.JFXPanel;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.scene.Scene;
        import minesweeper.Grid;
        import minesweeper.Tile;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import static minesweeper.Main.BOMBS;

        import java.sql.SQLOutput;
        import java.util.List;

        import static org.junit.Assert.*;
public class BombTest {

    @Test
    public void checkBombCountReturnsCorrectBombCount () throws Exception{
        JFXPanel jfxPanel = new JFXPanel();

    assertEquals (BOMBS,10);
        System.out.println("test passed, bombs:" + BOMBS);
    }
}