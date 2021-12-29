package com.maycrawer.utils;

import java.util.Random;

import com.maycrawer.tiles.Tile;
import com.maycrawer.tiles.TileForest;
import com.maycrawer.tiles.TileGrass;
import com.maycrawer.tiles.TileMountain;

public class GeneratorIsland {
	
	private int x, y;
	private byte size;
	
	public GeneratorIsland(int x, int y, byte size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public void createIsland(Tile[][] tiles, boolean torn, byte torn_frequency) {
		Random r = new Random();
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				Tile t = null;
				int xx = x + i;
				int yy = y + j;
				
				switch(r.nextInt(4)) {
				case 0:
					t = new TileGrass(xx, yy);
					break;
				case 1:
					t = new TileGrass(xx, yy);
					break;
				case 2:
					t = new TileForest(xx, yy);
					break;
				case 3:
					t = new TileMountain(xx, yy);
					break;
				}
				if((!torn) || (torn && torn_frequency != 10 &&
						r.nextInt(11 - torn_frequency) != 0)) tiles[xx][yy] = t;
			}
		}
	}
	
	/**---------------------GETTERS-AND-SETTERS-------------------------------**/

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public byte getSize() {
		return size;
	}

	public void setSize(byte size) {
		this.size = size;
	}
	
}