package com.maycrawer.screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.entities.Entity;
import com.maycrawer.entities.Player;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.Button;
import com.maycrawer.handlers.CameraManager;
import com.maycrawer.handlers.Handler;
import com.maycrawer.handlers.KeyManager;
import com.maycrawer.handlers.MouseManager;
import com.maycrawer.sfx.Music;
import com.maycrawer.utils.GameInfo;
import com.maycrawer.utils.MapWriter;
import com.maycrawer.world.World;

public class GameScreen extends Screen {
	
	public static int score, total_score, arrows = 10, size = 32;

	private Handler handler;
	private World world;
	private CameraManager camera;
	private GraphicsMain gMain;
	private GameInfo gameInfo;
	private MapWriter mapWriter;
	private Entity player;
	
	private Music theme;
	
	private Button bExit, bBack;
	private int xb, yb;
	
	private Random r;
	
	public GameScreen(GraphicsMain gMain, Handler handler, Dimension world_dimension) {
		this.handler = handler;
		this.gMain = gMain;
		
		this.r = new Random();
		
		this.theme = new Music(null, "theme", 168 * 1000);
		
		this.xb = (gMain.getWidthScreen() / 2) - (330 / 2);
		this.yb = (gMain.getHeightScreen() / 2);
		this.bBack = new Button("Resume", xb, yb - 20, 330, 32);
		this.bExit = new Button("Back to the Menu", xb, yb + 20, 330, 32);
		
		this.camera = new CameraManager(gMain, 0, 0);
		this.world = new World(gMain, camera, world_dimension.width, world_dimension.height);
		this.player = new Player(gMain, world, handler, camera, 50 * 32, 50 * 32);
		this.world.addPlayer(player);
		this.gameInfo = new GameInfo(gMain, handler, player, world);
	}

	@Override
	public void render(Graphics g) {
		this.world.render(g);
		this.gameInfo.render(g);
		
		if(!this.world.isActive()) {
			this.renderEscapeMode(g);
		}
	}

	@Override
	public void tick() {
		this.world.tick();
		this.camera.tick();
		this.gameInfo.tick();
		
		if(theme.getSound().isFinished()) {
			this.theme = new Music(null, "theme", 168 * 1000);
		}
		
		KeyManager key = handler.getKeyManager();
		
		if(!world.isActive()) {
			MouseManager m = this.handler.getMouseManager();
			this.bBack.tick(m.getXClicked(), m.getYClicked());
			this.bExit.tick(m.getXClicked(), m.getYClicked());
			if(bExit.isClicked()) {
				world.changeActivity();
				this.gMain.setScreen("MENU");
			} else
			if(bBack.isClicked()) {
				world.changeActivity();
			} m.clicked();	
		}
		
		MouseManager mouse = handler.getMouseManager();
		
		//if(mouse.getWheelValue() != 0) {
		//	size += mouse.getWheelValue();
		//}
		
		if(key.getKey(KeyEvent.VK_Z)) {
			size += 4;
			key.dropKey(KeyEvent.VK_Z);
		}
		if(key.getKey(KeyEvent.VK_X)) {
			size -= 4;
			key.dropKey(KeyEvent.VK_X);
		}
		
		if(key.getKey(KeyEvent.VK_P)) {
			this.mapWriter = new MapWriter(world.getTiles(), "world_map_" + r.nextInt(128), 128, 128);
			this.mapWriter.write();
			key.dropKey(KeyEvent.VK_P);
		}
		
		if(player.getLife() <= 0) {
			gMain.setScreen("CREATING WORLD");
			this.player = new Player(gMain, world, handler, camera, 60 * 32, 60 * 32);
			world.addPlayer(player);
			gameInfo.addPlayer(player);
		}
	}
	
	public void renderEscapeMode(Graphics g) {
		g.setColor(new Color(125, 125, 125, 125));
		g.fillRect(0, 0, this.gMain.getWidthScreen(), this.gMain.getHeightScreen());
	
		g.setColor(new Color(150, 50, 20));
		int xe = (gMain.getWidthScreen() / 2) - 200;
		g.fillRect(xe, yb - 64, 400, 128);
		g.draw3DRect(xe, yb - 64, 400, 128, true);
		
		g.setColor(Color.GREEN.darker());
		g.setFont(Assets.font36);
		String text = "Game Paused...";
		int spc = g.getFontMetrics().stringWidth(text);
		g.drawString(text, (gMain.getWidthScreen() / 2) - (spc / 2), this.yb - 36);
		
		bBack.render(g, handler);
		bExit.render(g, handler);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public World getWorld() {
		return world;
	}
	
	public static void addScore(int score) {
		GameScreen.score += score;
		GameScreen.total_score += score;
	}
	
	public static void addArrow(int arrow) {
		GameScreen.arrows += arrow;
	}
	
	public Music getTheme() {
		return theme;
	}
	
	public void setNewPlayer() {
		this.player.resetInventory(gMain, handler, world, 100 * 32, 100 * 32);
	}
	
	public void resetMusic() {
		this.theme = new Music(null, "theme", 168 * 1000);
	}

}
