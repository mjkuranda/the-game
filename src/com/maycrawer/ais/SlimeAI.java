package com.maycrawer.ais;

import java.util.Random;

import com.maycrawer.entities.Entity;
import com.maycrawer.tiles.Tile;
import com.maycrawer.world.World;

public class SlimeAI extends MobAI {

	private World world;
	
	private int distance, curr_distance;
	private float sin, cos;
	
	private int radius = 160;
	
	private byte mode; // 0 - standing, 1 - walking, 2 - following, 3 - running away
	
	public SlimeAI(World world) {
		this.world = world;
		this.distance = 100;
	}
	
	@Override
	public void tick(Entity e, Entity des) {
		if(isNearEntity(e, des)) {
			following(e, des);
		} else
			walking(e);
	}

	@Override
	public void walking(Entity e) {
		
		Random r = new Random();
		
		if(this.mode == 0 && r.nextInt(100) == 0) {
			curr_distance = 0;
			sin = 0;
			cos = 0;
		
			this.mode = 1;
			e.setWalking(true);
			e.setFollowing(false);
		}
		
		if(this.mode == 1 && curr_distance == 0) {
			int angle = r.nextInt(361);
			this.sin = (float) Math.sin(angle);
			this.cos = (float) Math.cos(angle);
			
			this.mode = 1;
		}
		
		if(this.mode == 1 && curr_distance < distance) {
			Tile t = world.getCurrentTile(e);
			
			e.addX(sin * e.getSpeed() * t.getSpeed());
			e.addY(cos * e.getSpeed() * t.getSpeed());
			curr_distance++;
		}
		
		if(curr_distance == distance && curr_distance != 0 && curr_distance != 2) {
			curr_distance = 0;
			sin = 0;
			cos = 0;
			
			this.mode = 0;
			e.setWalking(false);
			e.setFollowing(false);
		}
		
	}

	@Override
	public void following(Entity e, Entity e_destination) {
		this.mode = 2;
		
		double angle = Math.toDegrees(Math.atan2(e_destination.getX() - e.getX(),
												e_destination.getY() - e.getY()));
		
		float sin = (float) Math.sin(Math.toRadians(angle));
		float cos = (float) Math.cos(Math.toRadians(angle));
		
		int result = 1;
		
		if(e.isBeaten()) {
			result = -36;
			e.wasBeaten();
		} else if(e_destination.isBeaten()) {
			e_destination.addX(sin * e.getSpeed() * 36);
			e_destination.addY(cos * e.getSpeed() * 36);
			e_destination.wasBeaten();
		}
		
		e.setWalking(true);
		e.setFollowing(true);
		
		Tile t = world.getCurrentTile(e);
		
		e.addX(sin * e.getSpeed() * result * t.getSpeed());
		e.addY(cos * e.getSpeed() * result * t.getSpeed());
	}
	
	@Override
	public void runningAway(Entity e, Entity e_destination) {
		this.mode = 3;
		
		float sin = (float) Math.sin(Math.toRadians(e.getX() - e_destination.getX()));
		float cos = (float) Math.cos(Math.toRadians(e.getY() - e_destination.getY()));
		
		Tile t = world.getCurrentTile(e);
		
		e.addX(sin * e.getSpeed() * t.getSpeed());
		e.addY(cos * e.getSpeed() * t.getSpeed());
	}

	@Override
	public boolean isNearEntity(Entity e, Entity object) {
		int xabs = (int) Math.abs(e.getX() - object.getX());
		int yabs = (int) Math.abs(e.getY() - object.getY());
		if(xabs <= this.radius && yabs <= this.radius)
			return true;
		return false;
	}
	
	@Override
	public byte getMode() {
		return mode;
	}

	@Override
	public void setMode(byte mode) {
		this.mode = mode;
	}
	
	@Override
	public int getRadius() {
		return radius;
	}

	
//	public float getDistanceToDestination() {
//		
//	}

}
