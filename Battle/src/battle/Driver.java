package battle;

import com.valkryst.VTerminal.Screen;

import java.awt.Point;
import java.io.IOException;
import java.util.Random;

public class Driver {
	public static void main(String[] args) throws IOException {
		Random r = new Random();
		final Screen screen = new Screen(80, 30);
		screen.addCanvasToFrame();

		final Map map = new Map();
		screen.addComponent(map);
		
		for(int x = 0; x < map.getMapWidth(); x++) {
			for(int y = 0; y < map.getMapHeight(); y++) {
				MapTile thisTile = map.getMapTiles()[x][y];
				//thisTile.setTerrainType(Main.dice(1, 5, 0));
				thisTile.setTerrainType(3);
				thisTile.setVisible(true);
			}
		}
		
		//randomize the map
		int iterations = 35;
		do {
			Point target = new Point(Main.dice(1, 24, 3),Main.dice(1, 60, 10));
			System.out.println(target);
			System.out.println(map.getMapWidth());
			int radius = Main.dice(1, 4, 4);
			int effect = Main.dice(1, 3, -2);
			for(int x = 0; x < map.getMapWidth(); x++) {
				for(int y = 0; y < map.getMapHeight(); y++) {
					if (distance(new Point(x, y), target) < (radius * radius)) {
						MapTile thisTile = map.getMapTiles()[x][y];
						int newTerrainType = thisTile.getTerrainType();
						if(newTerrainType >= 5) {
							thisTile.setTerrainType(5);
						} else if(newTerrainType <= 1) {
							thisTile.setTerrainType(1);
						} else {
							thisTile.setTerrainType(thisTile.getTerrainType() + effect);
						}
					}
					
				}
			}
			iterations--;
		} while (iterations > 0);
		
		for(int x = 0; x < map.getMapWidth(); x++) {
			for(int y = 0; y < map.getMapHeight(); y++) {
				MapTile thisTile = map.getMapTiles()[x][y];
				thisTile.setSprite(applyTerrain(thisTile.getTerrainType()));
			}
		}
		
		map.updateLayerTiles();
		screen.draw();
	}
	
	public static double distance(Point a, Point b) {
		double deltaX = a.getX() - b.getX();
		double deltaY = a.getY() - b.getY();
		return deltaX * deltaX + deltaY * deltaY;
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