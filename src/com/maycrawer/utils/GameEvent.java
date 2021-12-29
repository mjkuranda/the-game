package com.maycrawer.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.maycrawer.display.GraphicsMain;

public class GameEvent {
	
	/**
	 * When you hit bad tool
	 * Communicate
	 *
	 * **/
	
	private String message;
	private long appear;
	private int howlong;
	
	public GameEvent(String message, long appear, int howlong) {
		this.message = message;
		this.appear = appear;
		this.howlong = howlong;
	}
	
	public GameEvent(String message, int howlong) {
		this.message = message;
		this.howlong = howlong;
	}
	
	public void render(Graphics g, GraphicsMain gm, int y) {
		int trans = (int) ((255 * ((this.appear + this.howlong) - System.currentTimeMillis())) / this.howlong);
		
		g.setColor(new Color(75, 75, 75, ((trans >= 0) ? trans : 0)));
		g.fill3DRect(gm.getWidth() - 272, gm.getHeight() - 37 - y * 32, 256, 32, true);
		
		g.setColor(new Color(0, 0, 0, ((trans >= 0) ? trans : 0)));
		g.setFont(new Font("Consolas", Font.BOLD, 12));
		short spc = (short) g.getFontMetrics().stringWidth(this.message);
		g.drawString(this.message, gm.getWidth() - 272 + 128 - (spc / 2), gm.getHeight() - 37 - y * 32 + 20);
	}
	
	public boolean tooLong() {
		return (System.currentTimeMillis() >= this.appear + this.howlong);
	}
	
	//-------------------METHODS-----------------------------------------------------------------------//
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getAppear() {
		return appear;
	}

	public void setAppear(long appear) {
		this.appear = appear;
	}

	public int getHowlong() {
		return howlong;
	}

	public void setHowlong(int howlong) {
		this.howlong = howlong;
	}


}
