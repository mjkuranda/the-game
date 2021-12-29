package com.maycrawer.inventory;

import com.maycrawer.gfx.Assets;

public class Items {
	
	public static final int MIN_COUNT = 0;
	public static final int MAX_COUNT = 999;
	
	public static final int ITEMS_COUNT = 8;
	
	public static final byte STACKS[] = {
			16, 1, 3, 12, 8, 8, 8, 8,
			0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 4, 4,
			4, 4, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0,
			100, 100, 100, 0, 0, 0, 0, 0,
			1, 1, 1, 1, 1
	};
	
	public static final byte SLIMEBALL_ID = 0;
	public static final byte WOOD_ID = 1;
	public static final byte STONE_ID = 2;
	public static final byte SAPLING_ID = 3;
	public static final byte GOLD_ORE_ID = 4;
	public static final byte SILVER_ORE_ID = 5;
	public static final byte BRONZE_ORE_ID = 6;
	public static final byte IRON_ORE_ID = 7;
	
	public static final byte GOLD_INGOT_ID = 30;
	public static final byte SILVER_INGOT_ID = 31;
	public static final byte BRONZE_INGOT_ID = 32;
	public static final byte IRON_INGOT_ID = 33;
	
	public static final byte GOLD_COIN_ID = 56;
	public static final byte SILVER_COIN_ID = 57;
	public static final byte BRONZE_COIN_ID = 58;
	
	public static final byte IRON_SWORD_ID = 64;
	public static final byte IRON_AXE_ID = 65;
	public static final byte IRON_PICKAXE_ID = 66;
	public static final byte IRON_HAMMER_ID = 67;
	public static final byte WOODEN_BOW_ID = 68;

	public static final Item slimeball = new Item("Slimeball", Assets.slimeball_texture,
													0.25f, SLIMEBALL_ID, Types.RESOURCE, 1, 100);
	public static final Item wood = new Item("Wood", Assets.wood_texture,
													10.5f, WOOD_ID, Types.RESOURCE, 10, 1);
	public static final Item stone = new Item("Stone", Assets.stone_texture,
													1.0f, STONE_ID, Types.RESOURCE, 7, 10);
	public static final Item sapling = new Item("Sapling", Assets.sapling_texture,
													0.05f, SAPLING_ID, Types.RESOURCE, 1, 35);
	public static final Item gold_ore = new Item("Gold Ore", Assets.gold_ore_texture,
													0.5f, GOLD_ORE_ID, Types.RESOURCE, 1, 35);
	public static final Item silver_ore = new Item("Silver Ore", Assets.silver_ore_texture,
													1.0f, SILVER_ORE_ID, Types.RESOURCE, 1, 35);
	public static final Item bronze_ore = new Item("Bronze Ore", Assets.bronze_ore_texture,
													0.4f, BRONZE_ORE_ID, Types.RESOURCE, 1, 35);
	public static final Item iron_ore = new Item("Iron Ore", Assets.iron_ore_texture,
													0.75f, IRON_ORE_ID, Types.RESOURCE, 1, 35);
	public static final Item gold_ingot = new Item("Gold Ingot", Assets.gold_ingot_texture,
													0.9f, GOLD_INGOT_ID, Types.RESOURCE, 1, 35);
	public static final Item silver_ingot = new Item("Silver Ingot", Assets.silver_ingot_texture,
													1.8f, SILVER_INGOT_ID, Types.RESOURCE, 1, 35);
	public static final Item bronze_ingot = new Item("Bronze Ingot", Assets.bronze_ingot_texture,
													0.75f, BRONZE_INGOT_ID, Types.RESOURCE, 1, 35);
	public static final Item iron_ingot = new Item("Iron Ingot", Assets.iron_ingot_texture,
													1.3f, IRON_INGOT_ID, Types.RESOURCE, 1, 35);
	
	public static final Item gold_coin = new Item("Gold Coin", Assets.gold_coin_texture,
													0.15f, GOLD_COIN_ID, Types.CASH, 0, 100);
	public static final Item silver_coin = new Item("Silver Coin", Assets.silver_coin_texture,
													0.12f, SILVER_COIN_ID, Types.CASH, 0, 100);
	public static final Item bronze_coin = new Item("Bronze Coin", Assets.bronze_coin_texture,
													0.10f, BRONZE_COIN_ID, Types.CASH, 0, 100);
	
	public static final Item iron_sword = new Item("Iron Sword", Assets.iron_sword_texture,
													5.5f, IRON_SWORD_ID, Types.TOOL, 30, 4);
	public static final Item iron_axe = new Item("Iron Axe", Assets.iron_axe_texture,
													5.0f, IRON_AXE_ID, Types.TOOL, 25, 3);
	public static final Item iron_pickaxe = new Item("Iron Pickaxe", Assets.iron_pickaxe_texture,
													7.5f, IRON_PICKAXE_ID, Types.TOOL, 25, 2);
	public static final Item iron_hammer = new Item("Iron Hammer", Assets.iron_hammer_texture,
													3.5f, IRON_HAMMER_ID, Types.TOOL, 5, 5);
	public static final Item wooden_bow = new Item("Wooden Bow", Assets.bow_texture,
													4.0f, WOODEN_BOW_ID, Types.TOOL, 2, 7);

	public static final Item[] items = {
		slimeball, wood, stone, sapling, gold_ore, silver_ore, bronze_ore
	};
	
	public static byte getStack(Item item) {
		if(item == null) return 127;
		return STACKS[item.getId()];
	}
	
}
