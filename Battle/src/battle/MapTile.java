package battle;

import lombok.Data;

@Data
public class MapTile {
	/** The sprite. */
	private Sprite sprite = Sprite.WALL;

	/** The type of terrain, which is a numeric value to help with the map randomization */
	private int terrainType = 3;
	
	/** The cost for an entity to move across the tile. */
	private int movementCost = 1;

	/** Whether or not the tile is solid. */
	private boolean solid = true;
	/** Whether or not the tile has been seen before. */
	private boolean visited = false;
	/** Whether or not the tile is visible. */
	private boolean visible = false;

	/** Constructs a new MapTile. */
	public MapTile() {
	}

	/**
	 * Constructs a new MapTile.
	 *
	 * @param sprite
	 *            The sprite.
	 */
	public MapTile(final Sprite sprite) {
		if (sprite == null) {
			this.sprite = Sprite.UNKNOWN;
		}

		this.sprite = sprite;
	}
}