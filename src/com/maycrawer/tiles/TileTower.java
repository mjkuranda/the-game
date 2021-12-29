package com.maycrawer.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.maycrawer.entities.Arrow;
import com.maycrawer.entities.Entity;
import com.maycrawer.entities.Particles;
import com.maycrawer.gfx.Animations;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.CameraManager;
import com.maycrawer.inventory.Inventory;
import com.maycrawer.inventory.Items;
import com.maycrawer.sfx.Sounds;
import com.maycrawer.utils.GameEvents;
import com.maycrawer.world.World;

public class TileTower extends Tile {

	private World world;
	private byte count, level;
	private Rectangle coll;
	
	private long next_time;
	
	public TileTower() {
		super("Tower", Animations.ANIMATION_FOREST, 0.9f, Tiles.ID_TOWER);
	}
	
	public TileTower(World world, int x, int y) {
		super(Tiles.TOWER_TILE, x, y);
		this.level = 1;
		this.world = world;
		world.addParticles(Particles.PARTICLE_DEATH_HAZE, world.getPlayer().getX(), world.getPlayer().getY() - (count * 32), (byte) 10, (byte) 5);
		this.coll = new Rectangle(getX() * 32 - (getSizeCollider() / 2), getY() * 32 - (getSizeCollider() / 2),
									getSizeCollider(), getSizeCollider());
	}

	@Override
	public void render(Graphics g, CameraManager camera) {
		g.drawImage(Assets.grass_textures[9], getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset(), 32, 32, null);
	}
	
	@Override
	public void renderObject(Graphics g, CameraManager camera) {
		for(int c = 0; c < count; c++) {
			g.drawImage(Assets.tower_textures[11], getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset() - (c * 32), 32, 32, null);
		}
		g.drawImage(Assets.tower_textures[level], getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset() - (count * 32), 32, 32, null);
	
		g.setColor(new Color(100, 0, 0, 100));
		Graphics2D g2 = (Graphics2D) g;
		g2.draw(coll);
	}

	@Override
	public void tick() {
		int points = count * 10 + level;
		if(points < 50) {
			
			Inventory in = world.getPlayer().getInventory();
			
			if(isHammer()) {
				
				if(in.hasItem(Items.wood) && in.hasItem(Items.stone)) {
					
					world.getPlayer().getInventory().takeItem(Items.wood, 1);
					world.getPlayer().getInventory().takeItem(Items.stone, 1);	
					
					level++;
					Sounds.STONE_HIT.play();
					if(level == 10) {
						world.addParticles(Particles.PARTICLE_DEATH_HAZE, world.getPlayer().getX(), world.getPlayer().getY() - (count * 32) - 32, (byte) 5, (byte) 3);
						Sounds.STONE_BREAK.play();
						if(count == 4)
							world.addEvent(GameEvents.MAX_BUILD);
						else 
							world.addEvent(GameEvents.CONSTRUCTION_FINISHED);
					}
				}
				else
					world.addEvent(GameEvents.NO_RESOURCE);
				setHammer(false);
			}
			if(level > 10) {
				level = 1;
				this.coll = new Rectangle(getX() * 32 - (getSizeCollider() / 2), getY() * 32 - (getSizeCollider() / 2),
											getSizeCollider(), getSizeCollider());
				count++;
			}
		}
		else
		if(points == 50 && isHammer()) {
			world.addEvent(GameEvents.MAX_BUILD);
			setHammer(false);
		}
		
		/** if the tower has minimum first level
		 	and from last shot passed one second **/
		if(count > 0 && System.currentTimeMillis() >= next_time + 1000) {
			for(int e = 0; e < world.getEntities().size(); e++) {
				Entity ee = world.getEntities().get(e);
				if(coll.intersects(ee.getCollider())) {
					shot(ee);
					return;
				}
			}
		}
		
	}
	
	@Override
	public byte getAge() {
		return level;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void shot(Entity e) {
		world.addArrow(new Arrow(getX() * 32, getY() * 32,
				e.getX(), e.getY()));
		this.next_time = System.currentTimeMillis();
		
//		System.out.println((getX() * 32) + ", " + (getY() * 32) + ", " +
//							e.getX() + ", " + e.getY());
	}
	
	private int getSizeCollider() {
		int size = (5 + (count * 2)) * 32 + (level * 2);
		return size;
	}

}
