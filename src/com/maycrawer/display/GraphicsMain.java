package com.maycrawer.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.maycrawer.ffx.FontLoader;
import com.maycrawer.gfx.ImageManager;
import com.maycrawer.handlers.Handler;
import com.maycrawer.screens.AboutGameScreen;
import com.maycrawer.screens.CreatorWorldScreen;
import com.maycrawer.screens.GameScreen;
import com.maycrawer.screens.GeneratorScreen;
import com.maycrawer.screens.MenuScreen;
import com.maycrawer.screens.Screen;
import com.maycrawer.sfx.AudioPlayer;

public class GraphicsMain extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width, height;
	
//	private boolean running;
	
	private ImageManager imgManager;
	private List<Screen> screens;
	private Handler handler;
	
	private Dimension world_dimension;
	private byte current_screen = 2;
	
	private byte id_creating_world_screen = 0;
	private byte id_game_screen = 1;
	private byte id_menu_screen = 2;
	private byte id_about_game = 3;
	
//	double target = 60.0;
//    double nsPerTick = 1000000000.0 / target;
//    long lastTime = System.nanoTime();
//    long timer = System.currentTimeMillis();
//    double unprocessed = 0.0;
//    public static int fps = 0;
//    public static int tps = 0;
//    boolean canRender = false;
	
	long lastTime = System.nanoTime();
	final double amountOfTicks = 60.0;
	double ns = 1000000000 / amountOfTicks;
	double delta = 0;
	
	int updates = 0;
	int frames = 0;
	long timer = System.currentTimeMillis();

	int fps, ups;
	
	public GraphicsMain(JFrame frame, Handler handler, int width, int height) {
		this.handler = handler;
		
		this.width = width;
		this.height = height;
		
		this.world_dimension = new Dimension(512, 512);
		
//		this.running = true;
		
		FontLoader.init();
		
		this.imgManager = new ImageManager();
		 
		screens = new ArrayList<Screen>();
		screens.add(new GeneratorScreen(this, handler));
		screens.add(new GameScreen(this, handler, world_dimension));
		screens.add(new MenuScreen(this, handler));
		screens.add(new AboutGameScreen(this, handler));
		
		setLayout(null);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render(g);
			frames++;
			
//			g.setColor(Color.WHITE);
//			g.setFont(new Font("Consolas", Font.PLAIN, 18));
//			g.drawString("FPS: " + fps, 500, 20);
//			g.drawString("UPS: " + ups, 500, 40);
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				
				ups = updates;
				fps = frames;
				
				System.out.println("Frames: " + frames + ", Ticks: " + updates);
				updates = 0;
				frames = 0;
			}
			
			AudioPlayer.update();
			
			repaint();
	//}
		
		//System.exit(0);
	}
	
	private void tick() {
		screens.get(current_screen).tick();
	}
	
	private void render(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);
		
		screens.get(current_screen).render(g);
	}
	
	public GameScreen getGameScreen() {
		return (GameScreen) screens.get(1);
	}
	
	public ImageManager getImageManager() {
		return imgManager;
	}
	
	public Handler getHandler() {
		return handler;
	}
	
	public Dimension getWorldDimension() {
		return world_dimension;
	}
	
	public int getMaxTilesX() {
		return width / 32;
	}
	
	public int getMaxTilesY() {
		return height / 32;
	}
	
	public int getWidthScreen() {
		return width;
	}
	
	public int getHeightScreen() {
		return height;
	}
	
//	private void calculateFPS() {
//		while (running) {
//            long now = System.nanoTime();
//            unprocessed += (now - lastTime) / nsPerTick;
//            lastTime = now;
//
//            if (unprocessed >= 1.0) {
//                tick();
//                unprocessed--;
//                tps++;
//                canRender = true;
//            } else canRender = false;
//
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            if (System.currentTimeMillis() - 1000 > timer) {
//                timer += 1000;
//                System.out.printf("FPS: %d | TPS: %d\n", fps, tps);
//                fps = 0;
//                tps = 0;
//            }
//        }
//	}
	
	public void nextScreen() {
		 this.current_screen++;
	}
	
//	public CreatorWorldScreen getCreatorWorldScreen() {
//		return (CreatorWorldScreen) screens.get(id_creating_world_screen);
//	}
	
	public GeneratorScreen getCreatorWorldScreen() {
		return (GeneratorScreen) screens.get(id_creating_world_screen);
	}

	public void setScreen(String screen) {
		if(screen == "CREATING WORLD") current_screen = id_creating_world_screen;
		if(screen == "GAME") current_screen = id_game_screen;
		if(screen == "MENU") current_screen = id_menu_screen;
		if(screen == "ABOUT GAME") current_screen = id_about_game;
	}

}
