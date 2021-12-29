package com.maycrawer.entities;

public class Entities {
	
	public static final byte PLAYER_ID = 0, SLIME_ID = 1, GOBLIN_ID = 2;

	public static final Entity PLAYER = new Player();
	public static final Entity SLIME = new Slime();
	public static final Entity GOBLIN = new Goblin();
	
	public static final Entity[] entities = {
			PLAYER, SLIME, GOBLIN
	};
	
}
