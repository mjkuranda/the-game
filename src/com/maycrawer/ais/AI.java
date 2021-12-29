package com.maycrawer.ais;

import com.maycrawer.entities.Entity;

public interface AI {

	public void walking(Entity e);
	
	public void following(Entity e, Entity e_destination);
	
	public void runningAway(Entity e, Entity e_destination);
	
	public boolean isNearEntity(Entity e, Entity object);
	
	public byte getMode();
	
	public void setMode(byte mode);
	
	public void tick(Entity e, Entity des);

	int getRadius();
	
}
