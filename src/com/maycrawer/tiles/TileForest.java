package com.maycrawer.tiles;

import java.awt.Graphics;
import java.util.Random;

import com.maycrawer.gfx.Animations;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.CameraManager;

public class TileForest extends Tile {
	
	public TileForest() {
		super("Forest", Animations.ANIMATION_FOREST, 0.8f, (byte) Tiles.ID_FOREST);
	}

	public TileForest(int x, int y) {
		super(Tiles.FOREST_TILE, x, y);
		setBackground(Assets.grass_textures[9]);
		Random r = new Random();
		setObject(new Object(Objects.TREE, (int) x, (int) y, (byte) r.nextInt(6)));
//		this.fallen = false;
	}
	
	public TileForest(int x, int y, byte age) {
		super(Tiles.FOREST_TILE, x, y);
		setBackground(Assets.grass_textures[9]);
		setObject(new Object(Objects.TREE, (int) x, (int) y, age));
	}
	
	public TileForest(Object obj, int x, int y) {
		super(Tiles.FOREST_TILE, x, y);
		setBackground(Assets.grass_textures[9]);
		setObject(obj);
	}
	
	@Override
	public void render(Graphics g, CameraManager camera) {
		g.drawImage(getBackground(), getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset(), 32, 32, null);
		//g.drawImage(Assets.tree_textures[4], getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset() - 36, 32, 64, null);
//		this.getObj().render(g, camera);
	}
	
	@Override
	public void renderObject(Graphics g, CameraManager camera) {
		this.getObj().render(g, camera);
	}

	@Override
	public void tick() {
		this.getObj().tick();
	}

	@Override
	public byte getAge() {
		return obj.getAge();
	}

}
