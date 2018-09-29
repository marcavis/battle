package battle;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;

import java.awt.Color;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException {
        final Screen screen = new Screen();
        screen.addCanvasToFrame();

        for(int x = 0; x < screen.getWidth(); x++) {
        	for(int y = 0; y < screen.getHeight(); y++) {
        		screen.getTileAt(x, y).setCharacter('1');
        	}
        }

        Tile[] helloTiles = screen.getTiles().getRowSubset(0, 0, 12);

        for (final Tile tile : helloTiles) {
            tile.setBackgroundColor(Color.BLACK);
            tile.setForegroundColor(Color.WHITE);
        }
        System.out.println(screen.getWidth());
        
        screen.draw();
    }
}