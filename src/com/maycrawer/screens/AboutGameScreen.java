package com.maycrawer.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.Button;
import com.maycrawer.handlers.Handler;
import com.maycrawer.handlers.MouseManager;

public class AboutGameScreen extends Screen {

	private GraphicsMain gMain;
	private Handler handler;
	private Button bBack;
	
	public AboutGameScreen(GraphicsMain gMain, Handler handler) {
		this.gMain = gMain;
		this.handler = handler;
		
		this.bBack = new Button("Back to the Menu", (gMain.getWidthScreen() / 2) - 160, 718, 320, 40);
	}
	
	public GraphicsMain getGraphicsMain() {
		return gMain;
	}
	
	public Handler getHandler() {
		return handler;
	}
	
	/** --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- **/

	@Override
	public void render(Graphics g) {
		/** BACKGROUND COLOR **/
		g.setColor(new Color(100, 50, 20));
		g.fill3DRect(0, 0, gMain.getWidthScreen(), gMain.getHeightScreen(), true);
		
		String title = "Welcome in The Game World!";
		String[] strs = new String[] {
			"In this world are waiting for you angry besties and many land to explore!",
			"Your task, it defence before enemies and prospering your character.",
			"Resources, adventures and to facing monsters it is inseparable part of the game.",
			"It depends on you whether win or dead.",
			"Good luck and have fun!"
		};

		g.setFont(Assets.font48);
		g.setColor(new Color(250, 130, 50));
		int wd = g.getFontMetrics().stringWidth(title);
		g.drawString(title, (gMain.getWidth() / 2) - (wd / 2), 64);
		
		g.setFont(Assets.font24);
		g.setColor(new Color(200, 100, 40));
		
		for(int t = 0; t < 5; t++) {
			int x = g.getFontMetrics().stringWidth(strs[t]);
			g.drawString(strs[t], (gMain.getWidth() / 2) - (x / 2), 108 + (t * 27));	
		}
		
		g.drawImage(Assets.background_1_texture, (gMain.getWidthScreen() / 2) - 488, 234, null);
		g.draw3DRect((gMain.getWidthScreen() / 2) - 488, 234, 976, 472, true);
		
		this.bBack.render(g, handler);
	}

	@Override
	public void tick() {
		MouseManager m = this.handler.getMouseManager();
		int xm = m.getXClicked();
		int ym = m.getYClicked();
		bBack.tick(xm, ym);
		
		if(bBack.isClicked()) {
			gMain.setScreen("MENU");
		}
		m.clicked();
	}

}
