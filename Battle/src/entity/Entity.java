package entity;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.component.Layer;

import battle.Driver;
import battle.Map;
import battle.Sprite;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Entity extends Layer {
    /** The sprite. */
    @Getter private Sprite sprite;

    /** The name of the entity. */
    @Getter @Setter private String name;
    
    /** The manner in which the entity moves - some entities walk efficiently on woods, others in water, etc. */
    @Getter @Setter private WalkType walkType;

    /**
     * Constructs a new Entity.
     *
     * @param sprite
     *          The sprite.
     *
     *          Defaults to UNKNOWN if the sprite is null.
     *
     * @param position
     *          The position of the entity within a map.
     *
     *          Defaults to (0, 0) if the position is null or if either part of the coordinate is negative.
     *
     * @param name
     *          The name.
     *
     *          Defaults to NoNameSet if the name is null or empty.
     */
    public Entity(final Sprite sprite, final Point position, final String name) {
        super(new Dimension(1, 1));

        if (sprite == null) {
            setSprite(Sprite.UNKNOWN);
        } else {
            setSprite(sprite);
        }

        if (position == null || position.x < 0 || position.y < 0) {
            super.getTiles().setPosition(new Point(0, 0));
        } else {
            super.getTiles().setPosition(position);
        }

        if (name == null || name.isEmpty()) {
            this.name = "NoNameSet";
        } else {
            this.name = name;
        }
    }

    /**
     * Moves the entity to a new position, relative to it's current position.
     *
     * @param dx
     *          The change in x-axis position.
     *
     * @param dy
     *          The change in y-axis position.
     */
    public void move(final int dx, final int dy) {
        final int newX = dx + super.getTiles().getXPosition();
        final int newY = dy + super.getTiles().getYPosition();

        super.getTiles().setXPosition(newX);
        super.getTiles().setYPosition(newY);
    }

    /**
     * Sets a new sprite for the entity.
     *
     * @param sprite
     *          The sprite.
     */
    public void setSprite(Sprite sprite) {
        if (sprite == null) {
            sprite = Sprite.UNKNOWN;
        }

        final Tile tile = super.getTileAt(new Point(0, 0));
        tile.setCharacter(sprite.getCharacter());
        tile.setForegroundColor(sprite.getForegroundColor());
        tile.setBackgroundColor(sprite.getBackgroundColor());

        this.sprite = sprite;
    }

    /**
     * Retrieves the entity's position.
     *
     * @return
     *          The entity's position.
     */
    public Point getPosition() {
        return new Point(super.getTiles().getXPosition(), super.getTiles().getYPosition());
    }

    /**
     * Moves the entity to a new position.
     *
     * Ignores null and negative positions.
     *
     * @param position
     *          The new position.
     */
    public void setPosition(final Point position) {
        if (position == null || position.x < 0 || position.y < 0) {
            return;
        }

        super.getTiles().setXPosition(position.x);
        super.getTiles().setYPosition(position.y);
    }
    
    
    
    public ArrayList<Point> findPath(final Map map, final Point destination) {
    	ArrayList<Point> closedSet = new ArrayList<Point>();
    	ArrayList<Point> openSet = new ArrayList<Point>();
    	openSet.add(getPosition());
    	HashMap<Point, Point> cameFrom = new HashMap<Point, Point>();
    	HashMap<Point, Integer> gScore = new HashMap<Point, Integer>();
    	HashMap<Point, Integer> fScore = new HashMap<Point, Integer>();
    	
    	for(int x = 0; x < map.getMapWidth(); x++) {
			for(int y = 0; y < map.getMapHeight(); y++) {
				gScore.put(new Point(x, y), 999999);
				fScore.put(new Point(x, y), 999999);
			}
    	}
    	gScore.put(getPosition(), 0);
    	fScore.put(getPosition(), Driver.distance(getPosition(), destination));
    	while(openSet.size() > 0) {
    		openSet.sort((p1, p2) -> fScore.get(p1).compareTo(fScore.get(p2)));
    		Point current = openSet.get(0);
    		if(current.equals(destination)) {
    			System.out.println("cheguei");
    			return reconstructPath(cameFrom, current);
    		}
    		openSet.remove(current);
    		closedSet.add(current);
    		
    		for (Point neighbor : map.getNeighbors(current)) {
				if(closedSet.contains(neighbor)) {
					continue;
				}
				int tentative_gScore = gScore.get(current) + Driver.inWorldDistance(map, this, current, neighbor);
				if(!openSet.contains(neighbor)) {
					openSet.add(neighbor);
				} else if (tentative_gScore > gScore.get(neighbor)) {
					continue;
				}
				
				//this is a better path
				cameFrom.put(neighbor, current);
				gScore.put(neighbor, tentative_gScore);
				fScore.put(neighbor, gScore.get(neighbor) + Driver.distance(neighbor, destination));
			}
    	}
    	//unreachable
		return null;
    }
    
    public ArrayList<Point> reconstructPath(HashMap<Point, Point> cameFrom, Point current) {
    	ArrayList<Point> path = new ArrayList<Point>();
    	path.add(current);
    	while (cameFrom.get(current) != null) {
    		current = cameFrom.get(current);
    		path.add(current);
    		//System.out.println(current);
    		
    	}
    	return path;
    }
}