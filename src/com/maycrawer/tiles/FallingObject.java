package com.maycrawer.tiles;

import java.awt.image.BufferedImage;

import com.maycrawer.gfx.Animations;
import com.maycrawer.handlers.CameraManager;

import java.awt.Graphics;

public class FallingObject {

	private int x, y; // x, y on map
	private int height, heightStart;
	private BufferedImage image;
	
	private long time; // wait to 0.05 s
	
	public FallingObject(int x, int y, int heightImg, int height, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.heightStart = height;
		this.image = image;
	}
	
	public void render(Graphics g, CameraManager camera) {
		g.drawImage(this.image, x * 32 - camera.getxOffset(),
								y * 32 - camera.getyOffset() - 64 + (heightStart - height),
								image.getWidth() * 2, image.getHeight() * 2, null);
		g.drawImage(Animations.ANIMATION_DEATH_HAZE.getCurrentFrame(),
								x * 32 - camera.getxOffset() + 8,
								y * 32 - camera.getyOffset() + 8, 16, 16, null);
		g.drawImage(Animations.ANIMATION_DEATH_HAZE.getCurrentFrame(),
								x * 32 - camera.getxOffset() + 12,
								y * 32 - camera.getyOffset() + 8, 16, 16, null);
		g.drawImage(Animations.ANIMATION_DEATH_HAZE.getCurrentFrame(),
								x * 32 - camera.getxOffset() - 8,
								y * 32 - camera.getyOffset() + 8, 16, 16, null);
		g.drawImage(Animations.ANIMATION_DEATH_HAZE.getCurrentFrame(),
								x * 32 - camera.getxOffset() - 4,
								y * 32 - camera.getyOffset() + 8, 16, 16, null);
		g.drawImage(Animations.ANIMATION_DEATH_HAZE.getCurrentFrame(),
								x * 32 - camera.getxOffset() + 20,
								y * 32 - camera.getyOffset() + 8, 16, 16, null);
	}
	
	public void tick() {
		if(System.currentTimeMillis() >= time + 50) {
			if(image.getHeight() > 1) {
				BufferedImage img = image.getSubimage(0, 0, image.getWidth(), image.getHeight() - 1);
				this.image = img;
				this.height = img.getHeight();
			}
			time = System.currentTimeMillis();
		}
		Animations.ANIMATION_DEATH_HAZE.tick();
	}
	
	public boolean isFinished() {
		return (image.getHeight() <= 1);
	}

}
