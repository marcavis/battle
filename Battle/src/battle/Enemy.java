package battle;

import java.awt.Point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data @AllArgsConstructor
public class Enemy {
	
	private Sprite sprite;
	private Point position;
	
}
