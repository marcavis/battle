package entity;

import battle.Sprite;

import java.awt.*;

public class Player extends Entity {
    /**
     * Constructs a new Player.
     *
     * @param position
     *          The position of the entity within a map.
     *
     *          Defaults to (0, 0) if the position is null or if either part of the coordinate is negative.
     *
     * @param name
     *          The name.
     */
    public Player(final Point position, final String name) {
        super(Sprite.PLAYER, position, name);
    }
}