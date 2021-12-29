package com.maycrawer.utils;

import java.util.Random;

import com.maycrawer.gfx.Assets;
import com.maycrawer.tiles.Tile;
import com.maycrawer.tiles.TileSea;
import com.maycrawer.tiles.Tiles;

public class Generator {

	private int width, height;
	
	public Generator(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void generateSea(Tile[][] tiles) {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				tiles[x][y] = new TileSea(x, y);
			}
		}
	}
	
	public void generate(Tile[][] tiles, int extraIslands, boolean torn, byte torn_frequency) {
		Random r = new Random();

		// count of islands = tiles's length
		// maybe extra islands
		
		for(int i = 0; i < tiles.length + extraIslands; i++) {
			GeneratorIsland genIs = new GeneratorIsland(r.nextInt(this.width - 24) + 8, r.nextInt(this.height - 24) + 8, (byte) ((2 * r.nextInt(5)) + 1));
			genIs.createIsland(tiles, torn, torn_frequency);
		}
		
		setBorders(tiles);
	}
	
	private void setBorders(Tile[][] tiles) {
		for(int y = 0; y < this.height; y++) {
			for(int x = 0; x < this.width; x++) {
				
				boolean w = (y > 0 && y < this.height) ? isTile(tiles, Tiles.ID_SEA, x, y - 1) : false;
				boolean a = (x > 0 && x < this.width) ? isTile(tiles, Tiles.ID_SEA, x - 1, y) : false;
				boolean s = (y > 0 && y < this.height - 1) ? isTile(tiles, Tiles.ID_SEA, x, y + 1) : false;
				boolean d = (x > 0 && x < this.width - 1) ? isTile(tiles, Tiles.ID_SEA, x + 1, y) : false;
				
				if(tiles[x][y].getId() != Tiles.ID_SEA) {
					if(w && a) tiles[x][y].setBackground(Assets.grass_textures[3]);
					if(w && d) tiles[x][y].setBackground(Assets.grass_textures[1]);
					if(s && a) tiles[x][y].setBackground(Assets.grass_textures[5]);
					if(s && d) tiles[x][y].setBackground(Assets.grass_textures[7]);
					if(w && d && s) tiles[x][y].setBackground(Assets.grass_textures[2]);
					if(a && s && d) tiles[x][y].setBackground(Assets.grass_textures[4]);
					if(w && a && s) tiles[x][y].setBackground(Assets.grass_textures[6]);
					if(a && w && d) tiles[x][y].setBackground(Assets.grass_textures[8]);
					if(w && a && s && d) tiles[x][y].setBackground(Assets.grass_textures[10]);
				} else {
					boolean ww = ((y > 0 && y < this.height) ? isTile2(tiles, x, y - 1) : false);
					boolean aa = ((x > 0 && x < this.width) ? isTile2(tiles, x - 1, y) : false);
					boolean ss = ((y > 0 && y < this.height - 1) ? isTile2(tiles, x, y + 1) : false);
					boolean dd = ((x > 0 && x < this.width - 1) ? isTile2(tiles, x + 1, y) : false);
					
					boolean wl = ((y > 0 && y < this.height
								&& x > 0 && x < this.width) ? !isTile(tiles, Tiles.ID_SEA, x - 1, y - 1) : false);
					boolean wr =  ((y > 0 && y < this.height
							&& x > 0 && x < this.width - 1) ? !isTile(tiles, Tiles.ID_SEA, x + 1, y - 1) : false);
					boolean dl = ((y > 0 && y < this.height - 1
							&& x > 0 && x < this.width) ? !isTile(tiles, Tiles.ID_SEA, x - 1, y + 1) : false);
					boolean dr = ((y > 0 && y < this.height - 1
							&& x > 0 && x < this.width - 1) ? !isTile(tiles, Tiles.ID_SEA, x + 1, y + 1) : false);

					
					if(ww && aa && ss && dd && wl && wr && dl && dr) tiles[x][y].setBackground(Assets.grass_textures[0]);
				}
			}
		}
	}
	
	private boolean isTile(Tile[][] tiles, byte id, int x, int y) {
		return tiles[x][y].getId() == id;
	}
	
	private boolean isTile2(Tile[][] tiles, int x, int y) {
		return tiles[x][y].getId() != Tiles.ID_SEA;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
