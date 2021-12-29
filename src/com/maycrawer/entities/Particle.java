package com.maycrawer.entities;

import java.awt.Graphics;

import com.maycrawer.gfx.Animation;
import com.maycrawer.handlers.CameraManager;

public class Particle {

	private Animation animation;
	private CameraManager camera;
	private int recurrence;
	private float x, y;
	private byte id;

	public Particle(Animation animation, CameraManager camera, int recurrence, float x, float y, byte id) {
		this.animation = new Animation(animation);
		this.recurrence = recurrence;
		this.x = x;
		this.y = y;
		this.camera = camera;
		this.id = id;
	}
	
	public Particle(Animation animation, int recurrence, byte id) {
		this.animation = new Animation(animation);
		this.recurrence = recurrence;
		this.id = id;
	}
	
	public void render(Graphics g) {
		int x = (int) this.x - camera.getxOffset();
		int y = (int) this.y - camera.getyOffset();
		
		g.drawImage(animation.getCurrentFrame(), x, y, 32, 32, null);
	}
	
	public void tick() {
		this.animation.tick();
		if(this.animation.getCurrentFrameID() == this.animation.getAnimationLength() - 1) this.recurrence++;
	}
	
	public Animation getAnimation() {
		return animation;
	}

	public int getRecurrence() {
		return recurrence;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public byte getID() {
		return id;
	}
	
}
