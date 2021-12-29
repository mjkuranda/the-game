package com.maycrawer.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.entities.Entity;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.Handler;

public class MapRenderer {

	private GraphicsMain gMain;
	private Handler handler;
	private Entity player;
	
	private byte scale; // the scale of map
	private int xpos, ypos; // in the corner
	
	private int xmap, ymap;
	private int xp, yp; // player's position
	
	public MapRenderer(GraphicsMain gMain, Handler handler, Entity player) {
		this.gMain = gMain;
		this.handler = handler;
		this.player = player;
		
		this.xmap = (gMain.getWidthScreen() / 2) - (532 / 2);
		this.ymap = (gMain.getHeightScreen() / 2) - (532 / 2);
		
		this.scale = 4;
		this.xpos = (int) (player.getX() / 32) + 1;
		this.ypos = (int) (player.getY() / 32);
	}
	
	public GraphicsMain getGraphicsMain() {
		return gMain;
	}
	
	public Handler getHandler() {
		return handler;
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(150, 50, 20));
		g.fill3DRect(xmap, ymap, 532, 532, true);
		int xsize = (512 / scale) + ((xpos < 0) ? xpos / scale : 0);
		int ysize = (512 / scale) + ((ypos < 0) ? ypos / scale : 0);
		
		int xsmap = xsize * scale;
		int ysmap = ysize * scale;
		
		g.setColor(new Color(73, 137, 255));
		g.fillRect(xmap + 10, ymap + 10, 512, 512);
		
		BufferedImage img = Assets.CURRENT_WORLD.getSubimage((xpos < 0) ? 0 : xpos, (ypos < 0) ? 0 : ypos, xsize, ysize);
		g.drawImage(img, xmap + 10 - ((xpos < 0) ? xpos * scale : 0), ymap + 10 - ((ypos < 0) ? ypos * scale : 0), xsmap, ysmap, null);
		g.setColor(new Color(150, 50, 20));
		g.draw3DRect(xmap + 10, ymap + 10, 512, 512, false);
	
		int pmx = (xp - xpos) * scale;
		int pmy = (yp - ypos) * scale;
		g.setColor(Color.RED);
		g.fillRect(xmap + 10 + pmx, ymap + 10 + pmy, scale, scale);
	}
	
	public void tick() {
//		MouseManager m = this.handler.getMouseManager();
//		
//		int xm = m.getXClicked();
//		int ym = m.getYClicked();
//		
//		if(xm >= xmap - 20 && xm < xmap + 512 - 20 &&
//			ym >= ymap - 20 && ym < ymap + 512 - 20) {
//			xpos = xm - xmap;
//			ypos = ym - ymap;
//			System.out.println(xpos + ", " + ypos);
//		}
//		
//		byte mb = (byte) m.getButtonID();
//		if(mb == 1) {
//			scale++;
//		} else if(mb == 3) {
//			scale--;
//		}
//		m.clicked();
//		if(scale < 1) scale = 1; else if(scale > 8) scale = 8;
		
		xp = (int) (player.getX() / 32);
		yp = (int) (player.getY() / 32);
		
		xpos = (int) (player.getX() / 32) - ((512 / scale) / 2);
		ypos = (int) (player.getY() / 32) - ((512 / scale) / 2);
	}
	
}
