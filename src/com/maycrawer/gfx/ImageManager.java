package com.maycrawer.gfx;

import java.awt.image.BufferedImage;

public class ImageManager {	
	
	private int width, height, size;
	
	private ImageLoader imgLoader;
	
	private BufferedImage texs, skns, slms, itms, prts, gbns, twrs, frns, firs;

	public ImageManager() {
		initGraphicsSize();
		loadImgs();
		initAssets();
	}
	
	private void loadImgs() {
		texs = imgLoader.loadImage("textures");
		skns = imgLoader.loadImage("entities", "skins");
		slms = imgLoader.loadImage("entities", "slime");
		itms = imgLoader.loadImage("items");
		prts = imgLoader.loadImage("particles");
		gbns = imgLoader.loadImage("entities", "goblin");
		twrs = imgLoader.loadImage("tower");
		frns = imgLoader.loadImage("furnace");
		firs = imgLoader.loadImage("fire");
	}
	
	public int getSize() {
		return size * 64; // 512
	}
	
	private void initGraphicsSize() {
		imgLoader = new ImageLoader(ImageLoader.GRAPHICS_TYPE_16_BIT);
		if(imgLoader.getType() == ImageLoader.GRAPHICS_TYPE_16_BIT) {
			width = 16;
			height = 16;
			size = 4;
		}
	}
	
	private BufferedImage loadSprite(BufferedImage img, int x, int y) {
		return img.getSubimage(x * width, y * height, width, height);
	}
	
	private BufferedImage loadTexture(int x, int y) {
		return texs.getSubimage(x * width, y * height, width, height);
	}
	
	private BufferedImage loadTexture(int x, int y, int width, int height) {
		return texs.getSubimage(x * this.width, y * this.height, width, height);
	}
	
	private void initAssets() {
		Assets.mouse_texture = imgLoader.loadImage("gui", "mouse");

		Assets.player_stopped_textures = new BufferedImage[4];
		Assets.player_stopped_textures[0] = loadSprite(skns, 0, 0);
		Assets.player_stopped_textures[1] = loadSprite(skns, 1, 0);
		Assets.player_stopped_textures[2] = loadSprite(skns, 2, 0);
		Assets.player_stopped_textures[3] = loadSprite(skns, 3, 0);
		
		Assets.player_walking_textures = new BufferedImage[3];
		Assets.player_walking_textures[0] = loadSprite(skns, 0, 1);
		Assets.player_walking_textures[1] = loadSprite(skns, 1, 1);
		Assets.player_walking_textures[2] = loadSprite(skns, 2, 1);
		
		Assets.player_swimming_textures = new BufferedImage[2];
		Assets.player_swimming_textures[0] = loadSprite(skns, 0, 2);
		Assets.player_swimming_textures[1] = loadSprite(skns, 1, 2);
		
		Assets.player_damage_skin = loadSprite(skns, 7, 0);
		
		Assets.slime_stopped_textures = new BufferedImage[3];
		Assets.slime_stopped_textures[0] = loadSprite(slms, 0, 0);
		Assets.slime_stopped_textures[1] = loadSprite(slms, 1, 0);
		Assets.slime_stopped_textures[2] = loadSprite(slms, 2, 0);
		
		Assets.slime_walking_textures = new BufferedImage[3];
		Assets.slime_walking_textures[0] = loadSprite(slms, 0, 1);
		Assets.slime_walking_textures[1] = loadSprite(slms, 1, 1);
		Assets.slime_walking_textures[2] = loadSprite(slms, 2, 1);		
		
		Assets.slime_swimming_textures = new BufferedImage[6];
		Assets.slime_swimming_textures[0] = loadSprite(slms, 0, 2);
		Assets.slime_swimming_textures[1] = loadSprite(slms, 1, 2);
		Assets.slime_swimming_textures[2] = loadSprite(slms, 2, 2);
		Assets.slime_swimming_textures[3] = loadSprite(slms, 3, 2);
		Assets.slime_swimming_textures[4] = loadSprite(slms, 4, 2);
		Assets.slime_swimming_textures[5] = loadSprite(slms, 5, 2);
		
		Assets.slime_damage_skin = loadSprite(slms, 7, 0);
		
		Assets.goblin_stopped_textures = new BufferedImage[4];
		Assets.goblin_stopped_textures[0] = loadSprite(gbns, 0, 0);
		Assets.goblin_stopped_textures[1] = loadSprite(gbns, 1, 0);
		Assets.goblin_stopped_textures[2] = loadSprite(gbns, 2, 0);
		Assets.goblin_stopped_textures[3] = loadSprite(gbns, 3, 0);
		
		Assets.goblin_walking_textures = new BufferedImage[4];
		Assets.goblin_walking_textures[0] = loadSprite(gbns, 0, 1);
		Assets.goblin_walking_textures[1] = loadSprite(gbns, 1, 1);
		Assets.goblin_walking_textures[2] = loadSprite(gbns, 2, 1);
		Assets.goblin_walking_textures[3] = loadSprite(gbns, 3, 1);

		Assets.goblin_following_textures = new BufferedImage[4];
		Assets.goblin_following_textures[0] = loadSprite(gbns, 4, 1);
		Assets.goblin_following_textures[1] = loadSprite(gbns, 5, 1);
		Assets.goblin_following_textures[2] = loadSprite(gbns, 6, 1);
		Assets.goblin_following_textures[3] = loadSprite(gbns, 7, 1);
		
		Assets.goblin_swimming_textures = new BufferedImage[4];
		Assets.goblin_swimming_textures[0] = loadSprite(gbns, 0, 2);
		Assets.goblin_swimming_textures[1] = loadSprite(gbns, 1, 2);
		Assets.goblin_swimming_textures[2] = loadSprite(gbns, 2, 2);
		Assets.goblin_swimming_textures[3] = loadSprite(gbns, 3, 2);
		
		Assets.goblin_damage_skin = loadSprite(gbns, 7, 0);
		
		//Assets.tree_texture = imgLoader.loadImage("tree");
		
		Assets.death_haze = new BufferedImage[8];
		for(byte b = 0; b < 8; b++) {
			Assets.death_haze[b] = loadSprite(prts, b, 1);	
		}
		Assets.wood_prts = new BufferedImage[6];
		for(byte b = 0; b < 6; b++) {
			Assets.wood_prts[b] = loadSprite(prts, b, 2);	
		}
		Assets.stone_prts = new BufferedImage[6];
		for(byte b = 0; b < 6; b++) {
			Assets.stone_prts[b] = loadSprite(prts, b, 3);	
		}
		Assets.water_prts = new BufferedImage[6];
		for(byte b = 0; b < 6; b++) {
			Assets.water_prts[b] = loadSprite(prts, b, 4);	
		}
		Assets.leaves_prts = new BufferedImage[16];
		for(byte b = 0; b < 2; b++) {
			for(byte bb = 0; bb < 8; bb++) {
				Assets.leaves_prts[bb + (b * 8)] = loadSprite(prts, bb, 5 + b);		
			}
		}
		
		Assets.grass_textures = new BufferedImage[11];
		Assets.grass_textures[0] = loadTexture(4, 1);
		Assets.grass_textures[1] = loadTexture(3, 2);
		Assets.grass_textures[2] = loadTexture(4, 2);
		Assets.grass_textures[3] = loadTexture(3, 3);
		Assets.grass_textures[4] = loadTexture(4, 3);
		Assets.grass_textures[5] = loadTexture(3, 4);
		Assets.grass_textures[6] = loadTexture(4, 4);
		Assets.grass_textures[7] = loadTexture(3, 5);
		Assets.grass_textures[8] = loadTexture(4, 5);
		Assets.grass_textures[9] = loadTexture(3, 0);
		Assets.grass_textures[10] = loadTexture(3, 1);

		Assets.tree_textures = new BufferedImage[6];
		Assets.tree_textures[5] = loadTexture(5, 0, 16, 32);
		Assets.tree_textures[4] = loadTexture(5, 2, 16, 32);
		Assets.tree_textures[3] = loadTexture(5, 4, 16, 32);
		Assets.tree_textures[2] = loadTexture(5, 6, 16, 32);
		Assets.tree_textures[1] = loadTexture(5, 8, 16, 32);
		Assets.tree_textures[0] = loadTexture(5, 10, 16, 32);
		
		Assets.fallen_trees = new BufferedImage[6];
		Assets.fallen_trees[0] = loadTexture(6, 0, 32, 32);
		Assets.fallen_trees[1] = loadTexture(6, 2, 32, 32);
		Assets.fallen_trees[2] = loadTexture(6, 4, 32, 32);
		Assets.fallen_trees[3] = loadTexture(6, 6, 32, 32);
		Assets.fallen_trees[4] = loadTexture(6, 8, 32, 32);
		Assets.fallen_trees[5] = loadTexture(6, 10, 32, 32);
		
		Assets.stone_textures = new BufferedImage[3];
		Assets.stone_textures[0] = loadTexture(2, 2);
		Assets.stone_textures[1] = loadTexture(2, 3);
		Assets.stone_textures[2] = loadTexture(2, 4);
		
		Assets.gold_ore_textures = new BufferedImage[3];
		Assets.gold_ore_textures[0] = loadTexture(2, 5);
		Assets.gold_ore_textures[1] = loadTexture(2, 6);
		Assets.gold_ore_textures[2] = loadTexture(2, 7);
		
		Assets.silver_ore_textures = new BufferedImage[3];
		Assets.silver_ore_textures[0] = loadTexture(1, 5);
		Assets.silver_ore_textures[1] = loadTexture(1, 6);
		Assets.silver_ore_textures[2] = loadTexture(1, 7);
		
		Assets.bronze_ore_textures = new BufferedImage[3];
		Assets.bronze_ore_textures[0] = loadTexture(2, 8);
		Assets.bronze_ore_textures[1] = loadTexture(2, 9);
		Assets.bronze_ore_textures[2] = loadTexture(2, 10);
		
		Assets.iron_ore_textures = new BufferedImage[3];
		Assets.iron_ore_textures[0] = loadTexture(1, 8);
		Assets.iron_ore_textures[1] = loadTexture(1, 9);
		Assets.iron_ore_textures[2] = loadTexture(1, 10);
		
		Assets.sea_textures = new BufferedImage[3];
		Assets.sea_textures[0] = loadTexture(1, 1);
		Assets.sea_textures[1] = loadTexture(1, 2);
		Assets.sea_textures[2] = loadTexture(1, 3);

		Assets.slimeball_texture = loadSprite(itms, 0, 0);
		Assets.wood_texture = loadSprite(itms, 1, 0);
		Assets.stone_texture = loadSprite(itms, 1, 1);
		Assets.sapling_texture = loadSprite(itms, 2, 0);
		Assets.mushroom_texture = loadTexture(4, 0);
		Assets.gold_ore_texture = loadSprite(itms, 3, 0);
		Assets.silver_ore_texture = loadSprite(itms, 3, 1);
		Assets.bronze_ore_texture = loadSprite(itms, 3, 2);
		Assets.iron_ore_texture = loadSprite(itms, 3, 3);
		Assets.gold_ingot_texture = loadSprite(itms, 4, 0);
		Assets.silver_ingot_texture = loadSprite(itms, 4, 1);
		Assets.bronze_ingot_texture = loadSprite(itms, 4, 2);
		Assets.iron_ingot_texture = loadSprite(itms, 4, 3);
		Assets.bow_texture = loadSprite(itms, 14, 0);
		Assets.arrow_texture = loadSprite(itms, 14, 1);
		
		Assets.iron_sword_texture = loadSprite(itms, 15, 0);
		Assets.iron_axe_texture = loadSprite(itms, 15, 1);
		Assets.iron_pickaxe_texture = loadSprite(itms, 15, 2);
		Assets.iron_hammer_texture = loadSprite(itms, 15, 3);
		
		Assets.gold_coin_texture = loadSprite(itms, 13, 0);
		Assets.silver_coin_texture = loadSprite(itms, 13, 1);
		Assets.bronze_coin_texture = loadSprite(itms, 13, 2);
		
		Assets.tower_textures = new BufferedImage[12]; // the first is empty :)
		Assets.tower_textures[1] = loadSprite(twrs, 0, 0);
		Assets.tower_textures[2] = loadSprite(twrs, 1, 0);
		Assets.tower_textures[3] = loadSprite(twrs, 2, 0);
		Assets.tower_textures[4] = loadSprite(twrs, 3, 0);
		Assets.tower_textures[5] = loadSprite(twrs, 4, 0);
		Assets.tower_textures[6] = loadSprite(twrs, 5, 0);
		Assets.tower_textures[7] = loadSprite(twrs, 6, 0);
		Assets.tower_textures[8] = loadSprite(twrs, 7, 0);
		Assets.tower_textures[9] = loadSprite(twrs, 0, 1);
		Assets.tower_textures[10] = loadSprite(twrs, 0, 2);
		Assets.tower_textures[11] = loadSprite(twrs, 0, 3);
		
		Assets.furnace_textures = new BufferedImage[10];
		Assets.furnace_textures[0] = loadSprite(frns, 0, 0);
		Assets.furnace_textures[1] = loadSprite(frns, 1, 0);
		Assets.furnace_textures[2] = loadSprite(frns, 2, 0);
		Assets.furnace_textures[3] = loadSprite(frns, 3, 0);
		Assets.furnace_textures[4] = loadSprite(frns, 4, 0);
		Assets.furnace_textures[5] = loadSprite(frns, 5, 0);
		Assets.furnace_textures[6] = loadSprite(frns, 6, 0);
		Assets.furnace_textures[7] = loadSprite(frns, 7, 0);
		Assets.furnace_textures[8] = loadSprite(frns, 0, 1);
		Assets.furnace_textures[9] = loadSprite(frns, 1, 1);
		
		Assets.firecamp_texture = loadTexture(1, 4);
		
		Assets.fire_textures = new BufferedImage[5];
		Assets.fire_textures[0] = loadSprite(firs, 0, 0);
		Assets.fire_textures[1] = loadSprite(firs, 1, 0);
		Assets.fire_textures[2] = loadSprite(firs, 2, 0);
		Assets.fire_textures[3] = loadSprite(firs, 3, 0);
		Assets.fire_textures[4] = loadSprite(firs, 4, 0);
		
		Assets.inventory_texture = imgLoader.loadImage("gui", "inventory");
		Assets.buildings_texture = imgLoader.loadImage("gui", "buildings");
		
		Assets.button_possible = imgLoader.loadImage("gui", "button_possible");
		Assets.button_impossible = imgLoader.loadImage("gui", "button_impossible");
		
		Assets.background_0_texture = imgLoader.loadImage("bckgs", "bckg0");
		Assets.background_1_texture = imgLoader.loadImage("bckgs", "bckg1");
	}

}
