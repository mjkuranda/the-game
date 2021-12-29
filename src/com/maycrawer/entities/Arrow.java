package com.maycrawer.entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.CameraManager;

public class Arrow {

	private Rectangle coll;
	
	private float xcurr, ycurr;

	private double angle;
	
	private byte speed = 5;
	private byte size = 8;
	private byte updates = 30; // distance
	private byte damage = 20;
	
	public Arrow(float xbeg, float ybeg, float xdes, float ydes) {
		this.xcurr = xbeg + 12;
		this.ycurr = ybeg + 12;
		
		this.coll = new Rectangle((int) (xcurr - 2), (int) (ycurr - 2), 4, 4);
		
		this.angle = Math.toDegrees(Math.atan2(xdes - this.xcurr, ydes - this.ycurr));
		
		this.xcurr += getXValue() * 4;
		this.ycurr += getYValue() * 4;
	}
	
	public void render(Graphics g, CameraManager camera) {
		Graphics2D g2d = (Graphics2D) g;
		
		//g2d.rotate(angle);
	//	g2d.drawImage(Assets.arrow_texture, (int) (this.xcurr - camera.getxOffset()), 
	//										(int) (this.ycurr - camera.getyOffset()),
	//										this.scale, this.scale, null);
//		g2d.drawImage(Assets.arrow_texture, at, null);
		
		//g2d.rotate(-angle);
		
//		AffineTransform tx = AffineTransform.getRotateInstance(this.angle, 8, 8);
//		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//		// Drawing the rotated image at the required drawing locations
//		g2d.drawImage(Assets.arrow_texture, tx, null);

		int x = (int) (this.xcurr - camera.getxOffset());
		int y = (int) (this.ycurr - camera.getyOffset());
//		
//	    AffineTransform backup = g2d.getTransform();
//	    AffineTransform a = AffineTransform.getRotateInstance(angle, 8, 8);
//	    g2d.setTransform(a);
//	    g2d.drawImage(Assets.arrow_texture, x, y, 8, 8, null);
//	    g2d.setTransform(backup);
		
		AffineTransform ta = AffineTransform.getTranslateInstance(x, y);
		ta.rotate(Math.toRadians(this.angle * -1), this.size, this.size);
		ta.scale(0.5, 0.5);
		
		g2d.drawImage(Assets.arrow_texture, ta, null);
	}
	
	public void tick() {
		update();
	}
	
	private void update() {
		float xa = (float) Math.sin(Math.toRadians(angle));
		float ya = (float) Math.cos(Math.toRadians(angle));

		this.xcurr += xa * this.speed;
		this.ycurr += ya * this.speed;
		
		this.coll = new Rectangle((int) (xcurr - 2), (int) (ycurr - 2), 4, 4);
		
		this.updates--;
	}
	
	private float getXValue() {
		float xa = (float) Math.sin(Math.toRadians(angle));
		return xa * this.speed;
	}
	
	private float getYValue() {
		float ya = (float) Math.cos(Math.toRadians(angle));
		return ya * this.speed;
	}
	
	public boolean isCollided(Entity e) {
		return (e.getCollider().intersects(coll));
	}
	
	public boolean isTooLong() {
		return (this.updates <= 0);
	}
	
	public byte getDistance() {
		return updates;
	}
	
	public byte getDamage() {
		return damage;
	}
	
	public float getX() {
		return this.xcurr;
	}
	
	public float getY() {
		return this.ycurr;
	}

}
