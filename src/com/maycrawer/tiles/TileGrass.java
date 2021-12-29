package com.maycrawer.tiles;

import java.awt.Graphics;

import com.maycrawer.gfx.Animations;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.CameraManager;

public class TileGrass extends Tile {

	public TileGrass() {
		super("Grass", Animations.ANIMATION_GRASS, 1.0f, Tiles.ID_GRASS);
	}
	
	public TileGrass(int x, int y) {
		super(Tiles.GRASS_TILE, x, y);
		setBackground(Assets.grass_textures[9]);
	}

	@Override
	public void render(Graphics g, CameraManager camera) {
		g.drawImage(getBackground(), getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset(), 32, 32, null);
	}
	
	@Override
	public void renderObject(Graphics g, CameraManager camera) {
		// TODO Auto-generated method stub
		
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
