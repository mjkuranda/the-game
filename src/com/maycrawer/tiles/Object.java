package com.maycrawer.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.maycrawer.handlers.CameraManager;
import com.maycrawer.inventory.Item;
import com.maycrawer.sfx.Sound;

public class Object {

	protected Sound clip;
	protected Item drop, extradrop;
	protected byte dura;
	
	protected float x, y;
	protected short width, height;
	protected byte age;
	protected byte id;
	
	protected BufferedImage[] imgs;

	public Object(BufferedImage[] imgs, Item item, Item extradrop, Sound clip, float x, float y, short width, short height, byte age, byte id) {
		this.imgs = imgs;
		this.drop = new Item(item, 1);
		if(extradrop != null) this.extradrop = new Item(extradrop, 1);
		this.clip = clip;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.age = age;
		this.id = id;
		
		this.dura = (byte) (age * 2);
	}
	
	public Object(BufferedImage[] imgs, Item item, Item extradrop, Sound clip, short width, short height, byte age, byte id) {
		this.imgs = imgs;
		this.drop = new Item(item, 1);
		if(extradrop != null) this.extradrop = new Item(extradrop, 1);
		this.clip = clip;
		this.width = width;
		this.height = height;
		this.age = age;
		this.id = id;

		this.dura = (byte) (age * 2);
	}
	
	public Object(Object obj, float x, float y) {
		this.imgs = obj.getImgs();
		this.drop = obj.getDrop();
		this.extradrop = obj.getExtraDrop();
		this.clip = obj.getClip();
		this.x = x;
		this.y = y;
		this.width = obj.getWidth();
		this.height = obj.getHeight();
		this.age = obj.getAge();
		this.id = obj.getId();
		
		this.dura = (byte) (age * 2);
	}
	
	public Object(Object obj, float x, float y, byte age) {
		this.imgs = obj.getImgs();
		this.drop = obj.getDrop();
		this.extradrop = obj.getExtraDrop();
		this.clip = obj.getClip();
		this.x = x;
		this.y = y;
		this.width = obj.getWidth();
		this.height = obj.getHeight();
		this.age = age;
		this.id = obj.getId();
		
		this.dura = (byte) ((age * 2) + 1);
	}
	
	public void render(Graphics g, CameraManager camera) {
		if(id == Objects.ID_TREE)
			g.drawImage(this.imgs[getAge()], (int) (getX() * 32 - camera.getxOffset()), (int) (getY() * 32 - camera.getyOffset() - 36), width, height, null);
		else if(id != Objects.ID_TREE)
			g.drawImage(this.imgs[getAge()], (int) (getX() * 32 - camera.getxOffset()), (int) (getY() * 32 - camera.getyOffset()), width, height, null);
	}
	
	public void tick() {
		if(id == Objects.ID_TREE) {
			Random r = new Random();
			if(age < 4 && r.nextInt(10000) == 0) {
				age++;
				dura += 2;
				if(age >= 4) age = 4;
			}
		}
	}
	
	public Object setAge(byte age) {
		this.age = age;
		return this;
	}
	
	public void resetDurability() {
		this.dura = (byte) (age * 2);
	}
	
	/* GETTERS AND SETTERS */

	public BufferedImage getCurrentImg() {
		return imgs[age];
	}
	
	public BufferedImage[] getImgs() {
		return imgs;
	}

	public void setImgs(BufferedImage[] imgs) {
		this.imgs = imgs;
	}
	
	public Item getDrop() {
		return drop;
	}
	
	public void setDrop(Item item) {
		this.drop = item;
	}
	
	public Item getExtraDrop() {
		return extradrop;
	}
	
	public void setExtraDrop(Item item) {
		this.extradrop = item;
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

	public short getWidth() {
		return width;
	}

	public void setWidth(short width) {
		this.width = width;
	}

	public short getHeight() {
		return height;
	}

	public void setHeight(short height) {
		this.height = height;
	}

	public byte getAge() {
		return age;
	}

//	public void setAge(byte age) {
//		this.age = age;
//	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}
	
	public byte getDurability() {
		return this.dura;
	}
	
	public void hit() {
		dura--;
	}
	
	public void setClip(Sound clip) {
		this.clip = clip;
	}
	
	public Sound getClip() {
		return clip;
	}
}
