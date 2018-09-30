package battle;

import com.valkryst.VTerminal.Screen;

import java.io.IOException;

public class Driver {
	public static void main(String[] args) throws IOException {
		final Screen screen = new Screen(80, 30);
		screen.addCanvasToFrame();

		final Map map = new Map();
		screen.addComponent(map);
		
		for(int x = 0; x < map.getMapWidth(); x++) {
			for(int y = 0; y < map.getMapHeight(); y++) {
				MapTile thisTile = map.getMapTiles()[x][y];
				thisTile.setTerrainType(Main.dice(1, 5, 0));
				thisTile.setSprite(applyTerrain(thisTile.getTerrainType()));
				thisTile.setVisible(true);
			}
		}
		map.updateLayerTiles();
		screen.draw();
	}
	
	public static Sprite applyTerrain(int terrain) {
		switch (terrain) {
		case 1:
			return Sprite.WATER;
		case 2:
			return Sprite.SWAMP;
		case 3:
			return Sprite.GRASS;
		case 4:
			return Sprite.HILL;
		case 5:
			return Sprite.MOUNTAIN;

		default:
			return Sprite.DIRT;
		}
	}
}