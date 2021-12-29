package com.maycrawer.inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.maycrawer.handlers.CameraManager;

public class ItemDrop {

	private Item item;
	private int count;
	
	private Rectangle collider;
	private float x, y;
	
	public ItemDrop(Item item, int count, float x, float y) {
		this.item = item;
		this.count = count;
		
		this.collider = new Rectangle((int) (x - 8), (int) (y - 8), 16, 16);
		
		this.x = x;
		this.y = y;
	}
	
	public void render(Graphics g, CameraManager camera) {
		g.drawImage(item.getTexture(), (int) (x - 8) - camera.getxOffset(), (int) (y - 8) - camera.getyOffset(), 16, 16, null);
		
		if(count > 1) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Consolas", Font.BOLD, 12));
			g.drawString("" + count, (int) (x) - camera.getxOffset(), (int) (y + 8) - camera.getyOffset());	
		}
	}
	
	///////////////////////////-GETTERS-AND-SETTERS-/////////////////////////////

	public Rectangle getCollider() {
		return collider;
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	} 
	
}
