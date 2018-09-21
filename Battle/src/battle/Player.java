package battle;

import java.util.ArrayList;

public class Player {
	private String name;
	
	private int hp;
	private int mhp;
	private int mp;
	private int mmp;
	private ArrayList<Effect> effects;
	
	public Player(String name) {
		this.name = name;
	}
	
	private String getName() {
		return name;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public int getMhp() {
		return mhp;
	}
	
	public void setMhp(int mhp) {
		this.mhp = mhp;
	}
	
	public int getMp() {
		return mp;
	}
	
	public void setMp(int mp) {
		this.mp = mp;
	}
	
	public int getMmp() {
		return mmp;
	}
	
	public void setMmp(int mmp) {
		this.mmp = mmp;
	}
	
	public ArrayList<Effect> getEffects() {
		return effects;
	}
	
	public void setEffects(ArrayList<Effect> effects) {
		this.effects = effects;
	}
	
	@Override
	public String toString() {
		return getName() + ": HP " + getHp() + "/" + getMhp() + " MP " 
				+ getMp() + "/" + getMmp();
	}

	
}
