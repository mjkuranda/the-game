package com.maycrawer.tiles;

public class Tiles {

	public static final byte ID_SEA = 0;
	public static final byte ID_GRASS = 1;
	public static final byte ID_FOREST = 2;
	public static final byte ID_MOUNTAIN = 3;
	
	public static final byte ID_TOWER = 10;
	public static final byte ID_FIRECAMP = 11;
	public static final byte ID_FURNACE = 12;
	
	public static final Tile SEA_TILE = new TileSea();
	public static final Tile GRASS_TILE = new TileGrass();
	public static final Tile FOREST_TILE = new TileForest();
	public static final Tile MOUNTAIN_TILE = new TileMountain();
	
	public static final Tile TOWER_TILE = new TileTower();
	public static final Tile FIRECAMP_TILE = new TileFirecamp();
	public static final Tile FURNACE_TILE = new TileFurnace();
	
}
