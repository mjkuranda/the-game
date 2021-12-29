package com.maycrawer.tiles;

import java.awt.Graphics;

import com.maycrawer.entities.Particles;
import com.maycrawer.gfx.Animations;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.CameraManager;
import com.maycrawer.inventory.Inventory;
import com.maycrawer.inventory.Items;
import com.maycrawer.sfx.Sounds;
import com.maycrawer.utils.GameEvents;
import com.maycrawer.world.World;

public class TileFurnace extends Tile {

	private byte age;
	private long time; // to spawn haze
	
	private World world;
	
	public TileFurnace() {
		super("Furnance", Animations.ANIMATION_FIRE, 0.8f, (byte) Tiles.ID_FURNACE);
	}
	
	public TileFurnace(World world, int x, int y) {
		super(Tiles.FURNACE_TILE, x, y);
		this.world = world;
		world.addParticles(Particles.PARTICLE_DEATH_HAZE, world.getPlayer().getX(), world.getPlayer().getY(), (byte) 10, (byte) 5);
		setBackground(Assets.grass_textures[9]);
	}
	
	@Override
	public void render(Graphics g, CameraManager camera) {
		g.drawImage(getBackground(), getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset(), 32, 32, null);
		g.drawImage(Assets.furnace_textures[getAge()], getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset(), 32, 32, null);
		
		this.renderFire(g, camera, getValue());
	}

	@Override
	public void renderObject(Graphics g, CameraManager camera) {
	}
	
	public void renderFire(Graphics g, CameraManager camera, int fuel) {
		int sf = (int) ((fuel * 13) / 200); // size of fire, 200 -> maximal fuel
		int cor = (13 + (sf * -1)) / 2;
		
		if(sf < 0) sf = 0;
		
		if(getAge() == 9 && fuel > 0) {
			g.drawImage(getAnim().getCurrentFrame(),
					getX() * 32 - camera.getxOffset() + 10 + cor,
					getY() * 32 - camera.getyOffset() + 17 + cor,
					sf, sf,
					null);	
			g.drawImage(getAnim().getCurrentFrame(),
					getX() * 32 - camera.getxOffset() + 4 + cor,
					getY() * 32 - camera.getyOffset() + 19 + cor,
					sf, sf,
					null);	
			g.drawImage(getAnim().getCurrentFrame(),
					getX() * 32 - camera.getxOffset() + 16 + cor,
					getY() * 32 - camera.getyOffset() + 19 + cor,
					sf, sf,
					null);
			
			if(System.currentTimeMillis() >= time + 400) {
				world.addParticles(Particles.PARTICLE_DEATH_HAZE,
						getX() * 32 + 16,
						getY() * 32 - 7);
				time = System.currentTimeMillis();
			}
		}
		
		
	}

	@Override
	public void tick() {
		this.getAnim().tick();
		
		Inventory in = world.getPlayer().getInventory();
		
		if(isHammer() && getAge() < 9) {
			
			if(in.getCount(Items.stone) >= 2) {
				
				world.getPlayer().getInventory().takeItem(Items.stone, 2);	
				
				age++;
				
				Sounds.STONE_HIT.play();
				if(getAge() == 9) {
					world.addParticles(Particles.PARTICLE_DEATH_HAZE, world.getPlayer().getX(), world.getPlayer().getY() - 32, (byte) 5, (byte) 3);
					Sounds.STONE_BREAK.play();
					world.addEvent(GameEvents.CONSTRUCTION_FINISHED);
				} else
				if(getAge() > 9) age = 9;
			}
			else
				world.addEvent(GameEvents.NO_RESOURCE);
			setHammer(false);
		} else
		if(getAge() == 9 && isHammer()) {
			world.addEvent(GameEvents.MAX_BUILD);
			setHammer(false);
		}
	
	}

	@Override
	public byte getAge() {
		return age;
	}

}