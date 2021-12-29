package com.maycrawer.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.Handler;
import com.maycrawer.handlers.KeyManager;
import com.maycrawer.tiles.Tile;
import com.maycrawer.utils.Generator;
import com.maycrawer.utils.MapWriter;

public class CreatorWorldScreen extends Screen {
	
	private GraphicsMain gMain;
	private Handler handler;
	
	private Generator generator;
	private MapWriter mapWriter;
	private Tile[][] tiles;
	
	private BufferedImage world_map_image;
	private int extraIslands;
	private boolean torn; // czy wyspy sa poszarpane
	private byte torn_frequency; // czestotliwosc poszarpania
	
	private int map_size;
	
	public CreatorWorldScreen(GraphicsMain gMain, Handler handler) {
		this.gMain = gMain;
		this.handler = handler;
		
		this.map_size = 512;
		this.torn = true;
		this.torn_frequency = 5;
		this.extraIslands = map_size * 8;
		
		this.generator = new Generator(map_size, map_size);
		this.tiles = new Tile[map_size][map_size];
//		this.generator.generateSea(tiles);
		
		this.mapWriter = new MapWriter(tiles, "world", map_size, map_size);
		generateNewMap();
		this.world_map_image = this.mapWriter.createImg();
		Assets.CURRENT_WORLD = this.world_map_image;
	}

	@Override
	public void render(Graphics g) {
		///////////////////////-RENDER-BACKGROUND-////////////////////////////
		g.setColor(new Color(100, 50, 25));
		g.fillRect(0, 0, gMain.getWidth(), gMain.getHeight());
		//////////////////////////////////////////////////////////////////////
		
		////////////////////////-RENDER-WORLD-MAP-////////////////////////////
		int map_x = getCenterX() - (map_size / 2);
		int map_y = 64;
		if(this.world_map_image != null) {
			g.drawImage(world_map_image, map_x, map_y, null);
		}
		g.setColor(Color.YELLOW);
		g.draw3DRect(map_x, map_y, map_size, map_size, true);
		//////////////////////////////////////////////////////////////////////
		
		//////////////////////-RENDER-TITLE-TEXT-/////////////////////////////
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Consolas", Font.PLAIN, 36));
		g.drawString("Create your new world!", gMain.getWidth() / 2 - 222, 40);
		//////////////////////////////////////////////////////////////////////
		
		////////////////////-RENDER-GENERATOR-OPTIONS-////////////////////////
		int text_x = getCenterX() - map_size / 2;
		int text_y = 64 + map_size;
		g.setColor(Color.WHITE);
		g.setFont(new Font("Consolas", Font.PLAIN, 24));
		g.drawString("Map Size      : " + map_size, text_x, text_y + 20);
		g.drawString("Torn          : " + isTorn(), text_x, text_y + 40);
		g.drawString("Torn frequency: " + getTornFrequency() + "%", text_x, text_y + 60);
		g.drawString("Extra Islands : " + extraIslands, text_x, text_y + 80);
		g.drawString("Press P to play on this world!", text_x, text_y + 120);
		//////////////////////////////////////////////////////////////////////
		
		///////////////////-RENDER-AUXILIARY-KEYS-TEXT-///////////////////////
		int aux_x = getCenterX() + map_size / 2 + 10;
		int aux_y = 82;
		g.drawString("Auxiliary Keys below:", aux_x, aux_y);
		
		g.drawString("Torn frequency:", aux_x, aux_y + 40);
		g.drawString("- Increase: Key A", aux_x, aux_y + 60);
		g.drawString("- Decrease: Key D", aux_x, aux_y + 80);
		
		g.drawString("Extra Islands:", aux_x, aux_y + 120);
		g.drawString("- Increase: Key W", aux_x, aux_y + 140);
		g.drawString("- Decrease: Key S", aux_x, aux_y + 160);
		
		g.drawString("- Generate new Map: Key C", aux_x, aux_y + 200);
		//////////////////////////////////////////////////////////////////////
	}

	@Override
	public void tick() {
		KeyManager key = handler.getKeyManager();
		
		// Create new map
		if(key.getKey(KeyEvent.VK_C)) {
			generateNewMap();
			key.dropKey(KeyEvent.VK_C);
			
			this.world_map_image = this.mapWriter.createImg();
		}
		
		// Increase map size
		if(key.getKey(KeyEvent.VK_W)) {
			extraIslands += 64;
			key.dropKey(KeyEvent.VK_W);
		}
		
		// Decrease map size
		if(key.getKey(KeyEvent.VK_S)) {
			extraIslands -= 64;
			key.dropKey(KeyEvent.VK_S);
		}
		
		// Increase torn frequency
		if(key.getKey(KeyEvent.VK_A)) {
			torn_frequency += 1;
			key.dropKey(KeyEvent.VK_A);
		}
		
		// Decrease torn frequency
		if(key.getKey(KeyEvent.VK_D)) {
			torn_frequency -= 1;
			key.dropKey(KeyEvent.VK_D);
		}
		
		// Torn manager
		if(key.getKey(KeyEvent.VK_T)) {
			torn = !torn;
			key.dropKey(KeyEvent.VK_T);
		}
		
		// Cross to game on your new world
		if(key.getKey(KeyEvent.VK_P)) {
			gMain.getGameScreen().getWorld().setTiles(tiles);
			gMain.nextScreen();
			key.dropKey(KeyEvent.VK_P);
		}
		
		if(extraIslands < 0) extraIslands = 0;
		if(extraIslands > map_size * 16) extraIslands = map_size * 16;
		
		if(torn_frequency > 10) torn_frequency = 10;
		if(torn_frequency < 0) torn_frequency = 0;
	}
	
	public void generateNewMap() {
		this.mapWriter.resetWorld(tiles);
		this.generator.generateSea(tiles);
		this.generator.generate(tiles, extraIslands, isTorn(), torn_frequency);
		this.mapWriter = new MapWriter(tiles, "world", map_size, map_size);
		this.mapWriter.write();
		Assets.CURRENT_WORLD = this.mapWriter.createImg();
	}
	
	
	public int getCenterX() {
		return (gMain.getWidth() / 2);
	}
	
	public int getCenterY() {
		return (gMain.getHeight() / 2);
	}
	
	private boolean isTorn() {
		return torn;
	}
	
	private int getTornFrequency() {
		return torn_frequency * 10;
	}

}
