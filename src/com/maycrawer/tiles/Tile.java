package com.maycrawer.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.maycrawer.gfx.Animation;
import com.maycrawer.handlers.CameraManager;

public abstract class Tile {
	
	private Tile type;
	protected Object obj;
	
	private BufferedImage background;

	private String name;
	private int x, y;
	private Animation anim;
	private byte id;
	private float speed; // entity speed
	
	private boolean hammer; // czy uderzenie mlotkiem
	
	private int value;
	
	public Tile(String name, Animation anim, float speed, byte id) {
		this.name = name;
		this.anim = anim;
		this.speed = speed;
		this.id = id;
	}
	
	public Tile(Object obj, String name, Animation anim, float speed, byte id) {
		this.obj = obj;
		this.name = name;
		this.anim = anim;
		this.speed = speed;
		this.id = id;
	}
	
	public Tile(Tile type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
		
		this.obj = type.getObj();
		this.name = type.getName();
		this.anim = type.getAnim();
		this.speed = type.getSpeed();
		this.id = type.getId();
	}
	
	public Tile setBackground(BufferedImage background) {
		this.background = background;
		return this;
	}
	
	public Tile setObject(Object obj) {
		this.obj = obj;
		return this;
	}
	
	public abstract void render(Graphics g, CameraManager camera);
	
	public abstract void renderObject(Graphics g, CameraManager camera);
	
	public abstract void tick();
	
	public void removeObject() {
		this.obj = null;
	}
	
	public void setHammer(boolean hit) {
		this.hammer = hit;
	}
	
	public boolean isHammer() {
		return hammer;
	}
	
	public abstract byte getAge();
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	/**-------------------------GETTERS-AND-SETTERS---------------------------**/

	public Tile getType() {
		return type;
	}

	public void setType(Tile type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Animation getAnim() {
		return anim;
	}

	public void setAnim(Animation anim) {
		this.anim = anim;
	}
	
	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}
	
	public BufferedImage getBackground() {
		return background;
	}
	
	public Object getObj() {
		return obj;
	}
	
	public float getSpeed() {
		return speed;
	}
}
