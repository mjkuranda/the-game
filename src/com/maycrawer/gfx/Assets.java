package com.maycrawer.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage mouse_texture;
	
	/** TILE TEXTURES **/
	public static BufferedImage forest_texture, mountain_texture/*,
								tree_texture*/;
	
	/** OBJECTS **/
	public static BufferedImage[] grass_textures, sea_textures, stone_textures, tree_textures,
								gold_ore_textures, silver_ore_textures, bronze_ore_textures, iron_ore_textures,
								fire_textures, fallen_trees;
	
	/** PLAYER **/
	public static BufferedImage[] player_stopped_textures, player_walking_textures,
								player_swimming_textures;
	public static BufferedImage player_damage_skin;
	
	/** SLIME **/
	public static BufferedImage[] slime_stopped_textures, slime_walking_textures,
								slime_swimming_textures;
	public static BufferedImage slime_damage_skin;
	
	/** GOBLIN**/
	public static BufferedImage[] goblin_stopped_textures, goblin_walking_textures,
								goblin_swimming_textures, goblin_following_textures;
	public static BufferedImage goblin_damage_skin;
	
	/** PARTICLES **/
	public static BufferedImage[] death_haze, wood_prts, stone_prts, water_prts, leaves_prts;
	
	/** TEXTURES **/
	// items
	public static BufferedImage slimeball_texture, wood_texture, stone_texture,
								sapling_texture, gold_coin_texture, silver_coin_texture,
								bronze_coin_texture, mushroom_texture, bow_texture, arrow_texture,
								gold_ingot_texture, silver_ingot_texture, bronze_ingot_texture, iron_ingot_texture,
								gold_ore_texture, silver_ore_texture, bronze_ore_texture, iron_ore_texture;
	// tools
	public static BufferedImage iron_sword_texture, iron_axe_texture, iron_pickaxe_texture, iron_hammer_texture;
	
	// buildings
	public static BufferedImage[] tower_textures, furnace_textures;
	public static BufferedImage firecamp_texture;
	
	/** GUI's **/
	public static BufferedImage inventory_texture, buildings_texture,
								button_possible, button_impossible;
	
	/** FONTS **/
	public static Font font12, font14, font16, font18, font20, font22, font24, font26, font28, font36, font48, font96;
	
	/** BACKGROUNDS **/
	public static BufferedImage background_0_texture, background_1_texture;
	
	
	
	public static BufferedImage CURRENT_WORLD;
  
	public Assets() {}
	
}