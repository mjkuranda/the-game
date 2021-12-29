package com.maycrawer.tiles;

import java.awt.Graphics;

import com.maycrawer.gfx.Animations;
import com.maycrawer.handlers.CameraManager;

public class TileSea extends Tile {

	public TileSea() {
		super("Sea", Animations.ANIMATION_SEA, 0.5f, Tiles.ID_SEA);
	}

	public TileSea(int x, int y) {
		super(Tiles.SEA_TILE, x, y);
	}

	@Override
	public void render(Graphics g, CameraManager camera) {
		g.drawImage(getAnim().getCurrentFrame(), getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset(), 32, 32, null);
		g.drawImage(getBackground(), getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset(), 32, 32, null);
	}
	
	@Override
	public void renderObject(Graphics g, CameraManager camera) {
		//this.getObj().render(g, camera);
	}

	@Override
	public void tick() {

	}
	

	@Override
	public byte getAge() {
		if(this.obj != null) return obj.getAge();
		return 0;
	}

}
