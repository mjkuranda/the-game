package com.maycrawer.entities;

import java.awt.Color;
import java.awt.Graphics;

public class Collider {

	private Entity e;
	private int x, y;
	private int width, height;
	
	public Collider(Entity e, int x, int y, int width, int height) {
		this.e = e;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean isColided(Entity e) {
		if((e.getX() >= this.e.getX() &&
			e.getX() < this.e.getX() + this.width &&
			e.getY() >= this.e.getY() &&
			e.getY() < this.e.getY() + this.height) ||
				(this.e.getX() >= e.getX() &&
				this.e.getX() < e.getX() + this.e.getCollider().getWidth() &&
				this.e.getY() >= e.getY() &&
				this.e.getY() < e.getY() + this.e.getCollider().height) ||
			(e.getX() >= this.e.getX() &&
			e.getX() < this.e.getX() + this.width &&
			e.getY() >= this.e.getY() &&
			e.getY() < this.e.getY() + this.height)) {
					return true;	
		}
//		}
		return false;
	}
	
	public void render(Graphics g, int x, int y, int xc, int yc) {
		g.setColor(new Color(100, 0, 0, 50));
		g.fillRect(this.x + x + xc, this.y + y + yc, width, height);
	}
	
	//////////////////////////-GETTERS-AND-SETTERS-/////////////////////////////

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
