package com.maycrawer.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.display.Main;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.Button;
import com.maycrawer.handlers.Handler;
import com.maycrawer.handlers.MouseManager;

public class MenuScreen extends Screen {

	private GraphicsMain gMain;
	private Handler handler;
	private Button bStart, bAbout, bExit;
	
	private long maxTime, time, curr;
	private int hue;
	
	private int xb, yb1, yb2, yb3;
	
	public MenuScreen(GraphicsMain gMain, Handler handler) {
		this.gMain = gMain;
		this.handler = handler;
		
		this.xb = (gMain.getWidthScreen() / 2) - (330 / 2);
		this.yb1 = 640;
		this.yb2 = 680;
		this.yb3 = 720;
		
		this.bStart = new Button("Start Game", xb, yb1, 330, 32);
		this.bAbout = new Button("About Game", xb, yb2, 330, 32);
		this.bExit = new Button("Exit", xb, yb3, 330, 32);
		
		this.handler.getMouseManager().clicked();
		
		this.maxTime = 1000;
		this.time = this.maxTime;
		this.hue = (int) ((time * 255) / maxTime);
		
		this.curr = System.currentTimeMillis();
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
		
		/** BACKGROUND IMAGE **/
		int ix = Assets.background_0_texture.getWidth();
		int bx = (gMain.getWidthScreen() - ix) / 2;
		int by = 118;
		g.drawImage(Assets.background_0_texture, bx, by, null);
		/** BACKGROUND LINE **/
		int iw = Assets.background_0_texture.getWidth();
		int ih = Assets.background_0_texture.getHeight();
		g.draw3DRect(bx, by, iw, ih, false);
		
		/** TITLE **/
		g.setFont(Assets.font96);
		g.setColor(new Color(200, 200, 0).brighter());
		String title = "The Game";
		int tw = g.getFontMetrics().stringWidth(title);
		int tx = gMain.getWidth() / 2 - (tw / 2);
		g.drawString(title, tx, 88);
		
		/** AUTHOR **/
		g.setFont(Assets.font36);
		g.setColor(new Color(200, 200, 0).darker());
		String author = "by " + Main.game_author;
		int tw2 = g.getFontMetrics().stringWidth(author);
		int tx2 = gMain.getWidth() / 2 - (tw2 / 2);
		g.drawString(author, tx2, by + 32);
		
		/** OPTIONS **/
		this.bStart.render(g, this.handler);
		this.bAbout.render(g, this.handler);
		this.bExit.render(g, this.handler);
		
		/** RENDER_ANIM **/
		g.setColor(new Color(0, 0, 0, hue));
		g.fillRect(0, 0, gMain.getWidthScreen(), gMain.getHeightScreen());
	}

	@Override
	public void tick() {
		if(this.time > 0 && System.currentTimeMillis() >= this.curr + 3) {
			this.curr = System.currentTimeMillis();
			this.time -= 5;
			this.hue = (int) ((time * 255) / maxTime);
		}
		
		MouseManager m = this.handler.getMouseManager();
		int xm = m.getXClicked();
		int ym = m.getYClicked();
		bStart.tick(xm, ym);
		bAbout.tick(xm, ym);
		bExit.tick(xm, ym);
		
		if(bStart.isClicked()) {
			time = this.maxTime;
			gMain.setScreen("CREATING WORLD");
			gMain.getCreatorWorldScreen().generateNewMap();
			gMain.getGameScreen().setNewPlayer();
		}
		else
		if(bAbout.isClicked()) {
			time = this.maxTime;
			gMain.setScreen("ABOUT GAME");
		}
		else
		if(bExit.isClicked()) System.exit(0);
		m.clicked();
	}

}
