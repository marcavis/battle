package battle;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import entity.Entity;
import entity.WalkType;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Driver {
    public static void main(String[] args) throws IOException, InterruptedException {
    	Random r = new Random();
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);
        final Screen screen = new Screen(80, 30, font);
        screen.addCanvasToFrame();

        final Map map = new Map();
        screen.addComponent(map);

//        final Point position = new Point(10, 10);
//        final Dimension dimensions = new Dimension(10, 5);
//        final Room room = new Room(position, dimensions);
//        room.carve(map);

        screen.draw();

		for(int x = 0; x < map.getMapWidth(); x++) {
			for(int y = 0; y < map.getMapHeight(); y++) {
				MapTile thisTile = map.getMapTiles()[x][y];
				//thisTile.setTerrainType(Main.dice(1, 5, 0));
				thisTile.setTerrainType(3);
				thisTile.setVisible(true);
			}
		}
		
		//randomize the map
		int iterations = 48;
		do {
			Point target = new Point(Main.dice(1, 24, 3),Main.dice(1, 60, 10));
			int radius = Main.dice(1, 4, 4);
			int effect = Main.dice(1, 3, -2);
			for(int x = 0; x < map.getMapWidth(); x++) {
				for(int y = 0; y < map.getMapHeight(); y++) {
					if (distance(new Point(x, y), target) < (radius * radius)) {
						MapTile thisTile = map.getMapTiles()[x][y];
						int newTerrainType = thisTile.getTerrainType() + effect;
						if(newTerrainType > 5) {
							thisTile.setTerrainType(5);
						} else if(newTerrainType < 1) {
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
		
		Entity orc = new Entity(Sprite.ORC, new Point(75, 8), "Orc");
		orc.setWalkType(WalkType.HUMANOID);
		Entity ghost = new Entity(Sprite.GHOST, new Point(75, 16), "Ghost");
		ghost.setWalkType(WalkType.ETHEREAL);
		Entity naga = new Entity(Sprite.NAGA, new Point(75, 20), "Naga");
		naga.setWalkType(WalkType.AMPHIBIOUS);
		Entity dwarf = new Entity(Sprite.DWARF, new Point(75, 12), "Dwarf");
		dwarf.setWalkType(WalkType.HILLDWELLER);
		Entity target = new Entity(Sprite.PLAYER, new Point(5, 22), "Target");
		
		map.addComponent(orc);
		map.addComponent(ghost);
		map.addComponent(naga);
		map.addComponent(dwarf);
		map.addComponent(target);
		
		ArrayList<Point> orcPath = orc.findPath(map, target.getPosition());
		ArrayList<Point> ghostPath = ghost.findPath(map, target.getPosition());
		ArrayList<Point> nagaPath = naga.findPath(map, target.getPosition());
		ArrayList<Point> dwarfPath = dwarf.findPath(map, target.getPosition());
		
		map.updateLayerTiles();
		screen.draw();
		
		int longWait = 3000;
		
		do {
		ArrayList<Entity> orcMarkers = plotPath(map, orcPath, Sprite.MARKER);
		map.updateLayerTiles();
		screen.draw();
		Thread.sleep(longWait);
		cleanPath(map, orcMarkers);
		map.updateLayerTiles();
		screen.draw();
		Thread.sleep(300);
		ArrayList<Entity> dwarfMarkers = plotPath(map, dwarfPath, Sprite.MARKER);
		map.updateLayerTiles();
		screen.draw();
		Thread.sleep(longWait);
		cleanPath(map, dwarfMarkers);
		map.updateLayerTiles();
		screen.draw();
		Thread.sleep(300);
		ArrayList<Entity> ghostMarkers = plotPath(map, ghostPath, Sprite.MARKER);
		map.updateLayerTiles();
		screen.draw();
		Thread.sleep(longWait);
		cleanPath(map, ghostMarkers);
		map.updateLayerTiles();
		screen.draw();
		Thread.sleep(300);
		ArrayList<Entity> nagaMarkers = plotPath(map, nagaPath, Sprite.MARKER);
		map.updateLayerTiles();
		screen.draw();
		Thread.sleep(longWait);
		cleanPath(map, nagaMarkers);
		map.updateLayerTiles();
		screen.draw();
		Thread.sleep(300);
		} while (true);
    }
    
    public static void cleanPath(Map map, ArrayList<Entity> path) {
    	for (Entity entity : path) {
			map.removeComponent(entity);
		}
    }
    
    public static ArrayList<Entity> plotPath(Map map, ArrayList<Point> path, Sprite sign) {
    	ArrayList<Entity> markers = new ArrayList<Entity>();
    	for (Point point : path) {
			Entity marker = new Entity(sign, point, "X");
			map.addComponent(marker);
			markers.add(marker);
		}
    	return markers;
    }
    
    public static int distance(Point a, Point b) {
		int deltaX = a.x - b.x;
		int deltaY = a.y - b.y;
		
		return deltaX * deltaX + deltaY * deltaY; 
		
	}
    
    //distance function used in the pathfinding algorithm since walking
    //diagonally incurs no extra cost
    public static int roguelikeDistance(Point a, Point b) {
    	int deltaX = a.x - b.x;
		int deltaY = a.y - b.y;
    	return Math.max(Math.abs(deltaX), Math.abs(deltaY));
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

	/**
	 * 
	 * @param entity
	 * 			The entity that wants to know the cost to move to "neighbor".
	 * @param current
	 * 			The current position of the entity.
	 * @param neighbor
	 * 			The destination.
	 * @return
	 */
	public static Integer inWorldDistance(Map map, Entity entity, Point current, Point neighbor) {
		int s = 0;
		if(s < 0)
			return 1;
		Integer terrain = map.getMapTiles()[neighbor.y][neighbor.x].getTerrainType();
		switch (entity.getWalkType()) {
		//prefers walking on wet terrain
		case AMPHIBIOUS:
			switch (terrain) {
			case 1: return 6;
			case 2: return 10;
			case 3: return 20;
			case 4: return 40;
			case 5: return 100;
			}
		//ghosts ignore terrain
		case ETHEREAL:
			return 10;
		//prefers walking on dry, flat land
		case HUMANOID:
			switch (terrain) {
			case 1: return 60;
			case 2: return 20;
			case 3: return 6;
			case 4: return 15;
			case 5: return 100;
			}
		case HILLDWELLER:
			switch (terrain) {
			case 1: return 70;
			case 2: return 35;
			case 3: return 10;
			case 4: return 10;
			case 5: return 20;
			}
		default:
			return 10;
		}
	}
}