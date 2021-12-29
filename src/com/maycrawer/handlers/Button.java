package com.maycrawer.handlers;

import java.awt.Color;
import java.awt.Graphics;

import com.maycrawer.gfx.Assets;
import com.maycrawer.sfx.Sounds;

public class Button {

	private String name;
	private int x, y, width, height;
	private boolean sound, move;
	
	public Button(String name, int x, int y, int width, int height) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void tick(int xm, int ym) {
		if(xm >= this.x && xm < this.x + this.width && ym >= this.y && ym < this.y + this.height) {
			this.move = true;
			this.sound = true;
		} else this.move = false;
		
		if(this.sound) {
			Sounds.CLICK.play();
			this.sound = false;
		}
	}
	
	public void check(int xm, int ym) {
		if(xm >= this.x && xm < this.x + this.width && ym >= this.y && ym < this.y + this.height) {
			this.move = true;
		} else this.move = false;
	}
	
	public void render(Graphics g, Handler handler) {
		g.setColor(new Color(100, 50, 20).brighter());
		g.fillRect(this.x, this.y, this.width, this.height);
		g.draw3DRect(this.x, this.y, this.width, this.height, true);
		
		g.setFont(Assets.font28);
		g.setColor(getColor(handler));
		int tx = g.getFontMetrics().stringWidth(name) / 2;
		g.drawString(this.name, this.x + (this.width / 2) - tx, this.y + (this.height / 2) + (this.height / 4));
	}
	
	private Color getColor(Handler handler) {
		MouseManager m = handler.getMouseManager();
		int xm = m.getXMoved();
		int ym = m.getYMoved();
		if(xm >= this.x && xm < this.x + this.width && ym >= this.y && ym < this.y + this.height) {
			return Color.WHITE;
		} else return Color.BLACK;
	}
	
	public boolean isClicked() {
		return move;
	}
	
	public boolean isMoved() {
		return move;
	}
	
	/** GETTERS **/
	
	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
