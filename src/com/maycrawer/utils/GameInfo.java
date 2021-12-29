package com.maycrawer.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.display.Main;
import com.maycrawer.entities.Entity;
import com.maycrawer.entities.Player;
import com.maycrawer.handlers.Handler;
import com.maycrawer.handlers.KeyManager;
import com.maycrawer.inventory.Item;
import com.maycrawer.screens.GameScreen;
import com.maycrawer.world.World;

public class GameInfo {

	private Entity player;
	private World world;
	private Handler handler;
	private GraphicsMain gMain;
	
	private boolean showGameInfo;
	
	public GameInfo(GraphicsMain gMain, Handler handler, Entity player, World world) {
		this.handler = handler;
		this.player = player;
		this.world = world;
		this.gMain = gMain;
		
		this.showGameInfo = false;
	}

	public void render(Graphics g) {
		g.setFont(new Font("Consolas", Font.PLAIN, 24));
		g.setColor(Color.WHITE);
		g.drawString(Main.game_title + ", v." + Main.game_version, 6, 20);
		g.drawString("Creator: " + Main.game_author, 6, 40);
		
		if(showGameInfo) {
			g.drawString("Player's X: " + player.getX() + " (" + (player.getX() / 32) + ")", 6, 80);
			g.drawString("Player's Y: " + player.getY() + " (" + (player.getY() / 32) + ")", 6, 100);
			
			g.drawString("Current Tile: " + world.getCurrentTile().getName(), 6, 140);
			g.drawString("Player's Action: " + getAction(), 6, 160);	
		}
		
		g.setColor(Color.WHITE);
		g.drawString("Main Hand: " + ((isMainRightHand()) ? "Right" : "Left") + " - " + getItemName(), 10, (showGameInfo) ? 200 : 80);
		
		g.setColor(new Color(0, 255, 0));
		g.drawString("Total points: " + GameScreen.total_score, 6, (showGameInfo) ? 240 : 120);
		
		g.setColor(new Color(175, 50, 25));
		g.drawString("Arrows: " + GameScreen.arrows, 6, (showGameInfo) ? 280 : 160);
	
	}
	
	public void tick() {
		KeyManager key = handler.getKeyManager();
		
		boolean paused = this.world.isActive();
		
		if(key.getKey(KeyEvent.VK_F3) && paused) {
			showGameInfo = !showGameInfo;
			key.dropKey(KeyEvent.VK_F3);
		}
	}
	
	private String getAction() {
		if(player.isSwimming()) return "Swimming";
		else if(player.isWalking() && !player.isSwimming()) return "Walking";
		else if(!player.isWalking()) return "Waiting";
		return null;
	}
	
	/**
	 * Get item from main hand
	 * 
	 * **/
	public String getItemName() {
		Item item = (isMainRightHand())   ? player.getInventory().getItemRightHand() :
											player.getInventory().getItemLeftHand();
		if(item != null) return item.getName();
		return "Empty hand";
	}
	
//	private void drawMainHandInfo(Graphics g) {
//		g.setFont(new Font("Consolas", Font.PLAIN, 24));
//		g.setColor(Color.WHITE);
//		g.drawString("Main Hand: " + ((isMainRightHand()) ? "Right" : "Left"), 10, 200);
//	}
	
	public void addPlayer(Entity player) {
		this.player = player;
	}
	
	public boolean isMainRightHand() {
		Player p = (Player) player;
		return p.getMainHand();
	}
	
	public GraphicsMain getGraphicsMain() {
		return gMain;
	}
	
}
