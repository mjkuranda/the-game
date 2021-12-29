package com.maycrawer.inventory;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.maycrawer.handlers.CameraManager;

public class Item {

	private String name;
	private BufferedImage texture;
	private float weight;
	private int count;
	
	private Type type;
	private int damage;
	private int str_prog;

	private byte id;
	
	public Item(String name, BufferedImage texture, float weight, int count, byte id, Type type, int damage, int str_pog) {
		this.name = name;
		this.texture = texture;
		this.weight = weight;
		this.count = count;
		this.id = id;
		this.type = type;
		this.damage = damage;
		this.str_prog = str_pog;
	}
	
	public Item(String name, BufferedImage texture, float weight, byte id, Type type, int damage, int str_prog) {
		this.name = name;
		this.texture = texture;
		this.weight = weight;
		this.id = id;
		this.type = type;
		this.damage = damage;
		this.str_prog = str_prog;
	}
	
	public Item(Item item, int count) {
		this.name = item.getName();
		this.texture = item.getTexture();
		this.weight = item.getWeight();
		this.count = count;
		this.id = item.getId();
		this.type = item.getType();
		this.damage = item.getDamage();
		this.str_prog = item.getStrikeProgress();
	}
	
	///////////////////////////-AUXILIARY-METHODS-//////////////////////////////
	
	public void takeOne() {
		this.count--;
	}
	
	public void addOne() {
		this.count++;
	}
	
	public void addCount(int count) {
		this.count += count;
	}
	
	public void render(Graphics g, CameraManager camera, int x, int y, int animX, int animY, int angle, boolean rightHand) {
		int shift = (rightHand) ? -12 : 12;
		
		int xi = (int) (x + 8) + shift + animX - camera.getxOffset();
		int yi = (int) (y + 8) + animY - camera.getyOffset();
		
		AffineTransform at = AffineTransform.getTranslateInstance(xi, yi);
		at.rotate(Math.toRadians(angle));
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.getTexture(), at, null);
		
	}
	
	////////////////////////-GETTERS-AND-SETTERS-///////////////////////////////

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	
	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public int getDamage() {
		return damage;
	}

	public int getStrikeProgress() {
		return str_prog;
	}

	public void setStrikeProgress(int str_prog) {
		this.str_prog = str_prog;
	}

}
