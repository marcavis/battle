package battle;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.component.Layer;
import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Map extends Layer {
	/** The mapTiles. */
	@Getter
	private MapTile[][] mapTiles;

	/** Constructs a new Map. */
	public Map() {
		super(new Dimension(80, 30));

		final int viewWidth = getViewWidth();
		final int viewHeight = getViewHeight();

		// Set the Layer to display all tiles as empty and black.
		for (int y = 0; y < viewHeight; y++) {
			for (int x = 0; x < viewWidth; x++) {
				final Tile tile = super.tiles.getTileAt(x, y);
				tile.setCharacter(' ');
				tile.setBackgroundColor(Color.BLACK);
			}
		}

		// Initialize the MapTiles array.
		mapTiles = new MapTile[viewHeight][viewWidth];

		for (int y = 0; y < viewHeight; y++) {
			for (int x = 0; x < viewWidth; x++) {
				mapTiles[y][x] = new MapTile();
			}
		}

		this.updateLayerTiles();
	}

	/**
	 * Updates the Map's Layer, so that any changes made to the Map's tiles are
	 * displayed on the Layer.
	 */
	public void updateLayerTiles() {
		for (int y = 0; y < getViewHeight(); y++) {
			for (int x = 0; x < getViewWidth(); x++) {
				final MapTile mapTile = mapTiles[y][x];
				final Sprite mapTileSprite = mapTile.getSprite();

				final Tile layerTile = super.tiles.getTileAt(x, y);
				layerTile.setCharacter(mapTileSprite.getCharacter());

				if (mapTile.isVisible()) {
					layerTile.setBackgroundColor(mapTileSprite.getBackgroundColor());
					layerTile.setForegroundColor(mapTileSprite.getForegroundColor());
				} else {
					layerTile.setBackgroundColor(mapTileSprite.getDarkBackgroundColor());
					layerTile.setForegroundColor(mapTileSprite.getDarkForegroundColor());
				}
			}
		}
	}

	/**
	 * Retrieves the width of the map.
	 *
	 * @return The width of the map.
	 */
	public int getMapWidth() {
		return mapTiles.length;
	}

	/**
	 * Retrieves the height of the map.
	 *
	 * @return The height of the map.
	 */
	public int getMapHeight() {
		return mapTiles[0].length;
	}

	/**
	 * Retrieves the width of the view.
	 *
	 * @return The width of the view.
	 */
	public int getViewWidth() {
		return super.tiles.getWidth();
	}

	/**
	 * Retrieves the height of the view.
	 *
	 * @return The height of the view.
	 */
	public int getViewHeight() {
		return super.tiles.getHeight();
	}
	
	/**
	 * Retrieves the neighbor cells from the current position.
	 * Doesn't even try to do it for cells in the map borders
	 * 
	 * @return A list of positions that are ensured to be inside the map bounds. 
	 */
	public ArrayList<Point> getNeighbors(Point p) {
		ArrayList<Point> neighbors = new ArrayList<Point>();
		neighbors.add(new Point(p.x-1, p.y-1));
		neighbors.add(new Point(p.x, p.y-1));
		neighbors.add(new Point(p.x+1, p.y-1));
		neighbors.add(new Point(p.x-1, p.y));
		neighbors.add(new Point(p.x+1, p.y));
		neighbors.add(new Point(p.x-1, p.y+1));
		neighbors.add(new Point(p.x, p.y+1));
		neighbors.add(new Point(p.x+1, p.y+1));
		boolean[] removalList = new boolean[8];
		for (int p1 = 0; p1 < neighbors.size(); p1++) {
			Point point = neighbors.get(p1);
			if(point.x <= 0 || point.y <= 0 || point.x >= 79 || point.y >= 29) {
				removalList[p1] = true;
			}
		}
		for (int p1 = 0; p1 < neighbors.size(); p1++) {
			if(removalList[p1]) {
				neighbors.remove(p1);
				p1--;
			}
		}
		//Collections.shuffle(neighbors);
		return neighbors;
	}	
}
