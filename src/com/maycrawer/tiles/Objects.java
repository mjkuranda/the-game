package com.maycrawer.tiles;

import com.maycrawer.entities.Particle;
import com.maycrawer.entities.Particles;
import com.maycrawer.gfx.Assets;
import com.maycrawer.inventory.Item;
import com.maycrawer.inventory.Items;
import com.maycrawer.sfx.Sounds;

public class Objects {

	public static final byte ID_TREE = 0;
	public static final byte ID_STONE = 1;
	public static final byte ID_GOLD = 2;
	public static final byte ID_SILVER = 3;
	public static final byte ID_BRONZE = 4;
	public static final byte ID_IRON = 5;
	
	public static final Object TREE = new Object(Assets.tree_textures, Items.wood, Items.sapling, Sounds.WOOD_HIT, (short) 32, (short) 64, (byte) 1, ID_TREE);
	public static final Object STONE = new Object(Assets.stone_textures, Items.stone, null, Sounds.STONE_HIT, (short) 32, (short) 32, (byte) 1, ID_STONE);
	public static final Object GOLD = new Object(Assets.gold_ore_textures, Items.stone, Items.gold_ore, Sounds.STONE_HIT, (short) 32, (short) 32, (byte) 1, ID_GOLD);
	public static final Object SILVER = new Object(Assets.silver_ore_textures, Items.stone, Items.silver_ore, Sounds.STONE_HIT, (short) 32, (short) 32,  (byte) 1, ID_SILVER);
	public static final Object BRONZE = new Object(Assets.bronze_ore_textures, Items.stone, Items.bronze_ore, Sounds.STONE_HIT, (short) 32, (short) 32,  (byte) 1, ID_BRONZE);
	public static final Object IRON = new Object(Assets.iron_ore_textures, Items.stone, Items.iron_ore, Sounds.STONE_HIT, (short) 32, (short) 32,  (byte) 1, ID_IRON);
	
	
	public static final Particle[] PARTICLES = new Particle[] {
			null, null, Particles.PARTICLE_WOOD_HIT, Particles.PARTICLE_STONE_HIT,
			Particles.PARTICLE_STONE_HIT, Particles.PARTICLE_STONE_HIT,
			Particles.PARTICLE_STONE_HIT, Particles.PARTICLE_STONE_HIT
	};
	
	public static final Item[] TOOLS = new Item[] {
			null, null, Items.iron_axe, Items.iron_pickaxe,
			Items.iron_pickaxe, Items.iron_pickaxe, Items.iron_pickaxe, Items.iron_pickaxe
	};
}
