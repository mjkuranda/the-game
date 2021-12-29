package com.maycrawer.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.Handler;

public class EndScreen extends Screen {

	private GraphicsMain gMain;
	private Handler handler;
	
	public EndScreen(GraphicsMain gMain, Handler handler) {
		this.gMain = gMain;
		this.handler = handler;
	}

	@Override
	public void render(Graphics g) {
		/** BACKGROUND COLOR **/
		g.setColor(new Color(100, 50, 20));
		g.fill3DRect(0, 0, gMain.getWidthScreen(), gMain.getHeightScreen(), true);
		
		/** TITLE **/
		g.setFont(Assets.font96);
		g.setColor(new Color(200, 200, 0).brighter());
		String title = "Game Over!";
		int tw = g.getFontMetrics().stringWidth(title);
		int tx = gMain.getWidth() / 2 - (tw / 2);
		g.drawString(title, tx, 88);
	}

	@Override
	public void tick() {
		
	}
	
	public Handler getHandler() {
		return handler;
	}

}
