package com.maycrawer.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.maycrawer.tiles.Tile;
import com.maycrawer.tiles.Tiles;

public class MapWriter {

	private Tile[][] tiles;
	private String name_file;
	private int width, height;
	
	public MapWriter(Tile[][] tiles, String name_file, int width, int height) {
		this.tiles = tiles;
		this.name_file = name_file;
		
		this.width = width;
		this.height = height;
	}
	
	public void write() {
		File file = new File("dat/imgs/maps/" + name_file + ".png");
		BufferedImage img = createImg();
		try {
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage createImg() {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				img.setRGB(i, j, getRGB(i, j));
			}
		}
		return img;
	}
	
	private int getRGB(int x, int y) {
		byte id = tiles[x][y].getId();
		if(id == Tiles.ID_GRASS)
			return new Color(0, 187, 0).getRGB();
		else if(id == Tiles.ID_SEA)
			return new Color(73, 137, 255).getRGB();
		else if(id == Tiles.ID_FOREST)
			return new Color(0, 119, 0).getRGB();
		else if(id == Tiles.ID_MOUNTAIN)
			return new Color(109, 109, 109).getRGB();
		return 0;
	}
	
	public void resetWorld(Tile[][] tiles) {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				tiles[x][y] = null;
			}
		}
	}

}
