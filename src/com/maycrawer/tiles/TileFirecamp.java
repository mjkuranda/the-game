package com.maycrawer.tiles;

import java.awt.Graphics;

import com.maycrawer.entities.Particles;
import com.maycrawer.gfx.Animations;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.CameraManager;
import com.maycrawer.world.World;

public class TileFirecamp extends Tile {

	private long fuel = 5000; // 1.000 is max;
	
	private World world;
	
	public TileFirecamp() {
		super("Firecamp", Animations.ANIMATION_FIRE, 0.8f, (byte) Tiles.ID_FIRECAMP);
	}
	
	public TileFirecamp(World world, int x, int y) {
		super(Tiles.FIRECAMP_TILE, x, y);
		this.world = world;
		world.addParticles(Particles.PARTICLE_DEATH_HAZE, world.getPlayer().getX(), world.getPlayer().getY(), (byte) 10, (byte) 5);
		setBackground(Assets.grass_textures[9]);
	}
	
	@Override
	public void render(Graphics g, CameraManager camera) {
		g.drawImage(getBackground(), getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset(), 32, 32, null);
		g.drawImage(Assets.firecamp_texture, getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset(), 32, 32, null);
		//g.drawImage(Assets.stone_textures[2], getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset(), 32, 32, null);
//		this.getObj().render(g, camera);
		
		this.renderFire(g, camera);
	}

	@Override
	public void renderObject(Graphics g, CameraManager camera) {
	}
	
	public void renderFire(Graphics g, CameraManager camera) {
		int sf = (int) ((this.fuel * 12) / 5000); // size of fire
		int cor = (12 + (sf * -1)) / 2;
		
		if(sf < 0) sf = 0;
		
		g.drawImage(getAnim().getCurrentFrame(),
				getX() * 32 - camera.getxOffset() + 5 + cor,
				getY() * 32 - camera.getyOffset() + 4 + cor,
				sf, sf,
				null);
	
		g.drawImage(getAnim().getCurrentFrame(),
				getX() * 32 - camera.getxOffset() + 16 + cor,
				getY() * 32 - camera.getyOffset() + 6 + cor,
				sf, sf,
				null);
		
		g.drawImage(getAnim().getCurrentFrame(),
				getX() * 32 - camera.getxOffset() + 9 + cor,
				getY() * 32 - camera.getyOffset() + 16 + cor,
				sf, sf,
				null);
	}

	@Override
	public void tick() {
		this.getAnim().tick();
		
		this.fuel--;
	}

	@Override
	public byte getAge() {
		return 0;
	}

}
