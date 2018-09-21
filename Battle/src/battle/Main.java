package battle;

import java.util.Random;

public class Main {
	public static void main(String[] args) {
		Player warrior = new Player("Gort");
		Player elf = new Player("Berzeron");
		warrior.setMhp(dice(2,4,12));
		warrior.setHp(warrior.getMhp());
		warrior.setMmp(dice(1,4,4));
		warrior.setMp(warrior.getMmp());
		
		elf.setMhp(dice(1,6,8));
		elf.setHp(elf.getMhp());
		elf.setMmp(dice(2,4,6));
		elf.setMp(elf.getMmp());
		System.out.println(warrior);
		System.out.println(elf);
	}
	
	public static int dice(int count, int die, int constant) {
		Random r = new Random();
		int result = 0;
		for(int i = 0; i < count; i++)
			result += r.nextInt(die) + 1; 
		return result + constant;
	}
}
