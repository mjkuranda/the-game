package com.maycrawer.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.Button;
import com.maycrawer.handlers.Handler;
import com.maycrawer.handlers.KeyManager;
import com.maycrawer.handlers.MouseManager;
import com.maycrawer.tiles.Tile;
import com.maycrawer.utils.Generator;
import com.maycrawer.utils.MapWriter;

public class GeneratorScreen extends Screen {

	private GraphicsMain gMain;
	private Handler handler;
	private Button bMenu, bPlay, bCreate, bIssAdd, bIssRem, bTornAdd, bTornRem;
	
	private Generator generator;
	private MapWriter mapWriter;
	private Tile[][] tiles;
	
	private BufferedImage world_map_image;
	private int extraIslands;
	private boolean torn; // czy wyspy sa poszarpane
	private byte torn_frequency; // czestotliwosc poszarpania
	
	private int map_size;
	
	public GeneratorScreen(GraphicsMain gMain, Handler handler) {
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
		
		///////////////////////-BUTTONS-//////////////////////////////////////////////
		int xb = getCenterX() + (getCenterX() / 2);
		
		this.bPlay = new Button("Start a new adventure!", xb - 250, 412, 500, 40);
		this.bMenu = new Button("Back to the Menu", xb - 150, 512, 300, 40);
		this.bCreate = new Button("Generate", xb - 100, 302, 200, 40);
		
		this.bIssAdd = new Button("Increase", xb - 128 - 60, 172, 120, 40);
		this.bIssRem = new Button("Decrease", xb - 128 - 60, 222, 120, 40);
		
		this.bTornAdd = new Button("Increase", xb + 128 - 60, 172, 120, 40);
		this.bTornRem = new Button("Decrease", xb + 128 - 60, 222, 120, 40);
	}

	@Override
	public void render(Graphics g) {
		////////////////////////-BACKGROUND-//////////////////////////////////
		g.setColor(new Color(100, 50, 20));
		g.fill3DRect(0, 0, gMain.getWidthScreen(), gMain.getHeightScreen(), true);
		//////////////////////////////////////////////////////////////////////
		
		//////////////////////////-TITLE-/////////////////////////////////////
		g.setFont(Assets.font48);
		g.setColor(new Color(200, 200, 0).brighter());
		String title = "Create your new world";
		int tw = g.getFontMetrics().stringWidth(title);
		int tx = gMain.getWidth() / 2 - (tw / 2);
		g.drawString(title, tx, 64);
		//////////////////////////////////////////////////////////////////////
		
		////////////////////////-RENDER-WORLD-MAP-////////////////////////////
		int map_x = getCenterX() - map_size;
		int map_y = 96;
		if(this.world_map_image != null) {
			g.drawImage(world_map_image, map_x, map_y, null);
		}
		g.setColor(Color.YELLOW);
		g.draw3DRect(map_x, map_y, map_size, map_size, true);
		//////////////////////////////////////////////////////////////////////
		
		/////////////////////////-BUTTONS-TEXTS-//////////////////////////////
		g.setFont(Assets.font24);
		int xt = (getCenterX() + (getCenterX() / 2));
		
		g.setColor(new Color(200, 200, 0).darker());
		g.drawString("" + extraIslands, xt - 128 - (g.getFontMetrics().stringWidth("" + extraIslands) / 2), map_y + 64);
		g.drawString(getTornFrequency() + "%", xt + 128 - (g.getFontMetrics().stringWidth(getTornFrequency() + "%") / 2), map_y + 64);
		
		g.setColor(new Color(200, 200, 0).brighter());
		renderText(g, "Island", "Amount", xt - 128, map_y + 18, 16);
		renderText(g, "Torns", "Frequency", xt + 128, map_y + 18, 16);
		//////////////////////////////////////////////////////////////////////
		
		////////////////////////////-BUTTONS-/////////////////////////////////
		bIssAdd.render(g, this.handler);
		bIssRem.render(g, this.handler);
		
		bTornAdd.render(g, this.handler);
		bTornRem.render(g, this.handler);
		
		bMenu.render(g, this.handler);
		bCreate.render(g, this.handler);
		bPlay.render(g, this.handler);
		//////////////////////////////////////////////////////////////////////
	}

	@Override
	public void tick() {
		KeyManager key = handler.getKeyManager();
		
		MouseManager m = this.handler.getMouseManager();
		int xm = m.getXClicked();
		int ym = m.getYClicked();
		
		bCreate.tick(xm, ym);
		bIssAdd.tick(xm, ym);
		bIssRem.tick(xm, ym);
		bTornAdd.tick(xm, ym);
		bTornRem.tick(xm, ym);
		bPlay.tick(xm, ym);
		bMenu.tick(xm, ym);
		
		// Create new map
		if(bCreate.isClicked()) {
			generateNewMap();
			this.world_map_image = this.mapWriter.createImg();
		}
		

		// Increase map size
		if(bIssAdd.isClicked()) {
			extraIslands += 256;
		}
				
		// Decrease map size
		if(bIssRem.isClicked()) {
			extraIslands -= 256;
		}
				
		// Increase torn frequency
		if(bTornAdd.isClicked()) {
			torn_frequency += 1;
		}
				
		// Decrease torn frequency
		if(bTornRem.isClicked()) {
			torn_frequency -= 1;
		}
				
		// Torn manager
		if(key.getKey(KeyEvent.VK_T)) {
			torn = !torn;
		}
				
		// Cross to game on your new world
		if(bPlay.isClicked()) {
			gMain.getGameScreen().getWorld().setTiles(tiles);
			gMain.getGameScreen().getWorld().reset();
			gMain.nextScreen();
			gMain.getGameScreen().getWorld().getPlayer().resetInventory(gMain, handler, 
					gMain.getGameScreen().getWorld(), 100 * 32, 100 * 32);
		}
		
		// To back to the menu
		if(bMenu.isClicked()) {
			this.map_size = 512;
			this.torn = true;
			this.torn_frequency = 5;
			this.extraIslands = map_size * 8;
			gMain.setScreen("MENU");
			
		}
			
		m.clicked();
		
		if(extraIslands < 0) extraIslands = 0;
		if(extraIslands > map_size * 16) extraIslands = map_size * 16;
		
		if(torn_frequency > 10) torn_frequency = 10;
		if(torn_frequency < 0) torn_frequency = 0;
	}
	
	//====================================================================================================
	
	public void generateNewMap() {
		this.mapWriter.resetWorld(tiles);
		this.generator.generateSea(tiles);
		this.generator.generate(tiles, extraIslands, isTorn(), torn_frequency);
		this.mapWriter = new MapWriter(tiles, "world", map_size, map_size);
		this.mapWriter.write();
		Assets.CURRENT_WORLD = this.mapWriter.createImg();
	}
	
	private void renderText(Graphics g, String text, String text2, int x, int y, int diff) {
		int cenT = g.getFontMetrics().stringWidth(text);
		g.drawString(text, x - (cenT / 2), y);
		int cenT2 = g.getFontMetrics().stringWidth(text2);
		g.drawString(text2, x - (cenT2 / 2), y + diff);
	}
	
	public int getCenterX() {
		return (gMain.getWidthScreen() / 2);
	}
	
	public int getCenterY() {
		return (gMain.getHeightScreen() / 2);
	}
	
	private boolean isTorn() {
		return torn;
	}
	
	private int getTornFrequency() {
		return torn_frequency * 10;
	}

}
