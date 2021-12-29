package com.maycrawer.handlers;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.entities.Entity;

public class CameraManager {

	private GraphicsMain gMain;
	private int xOffset, yOffset;
	
	public CameraManager(GraphicsMain gMain, int xOffset, int yOffset) {
		this.gMain = gMain;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void settingCamera(int xMove, int yMove) {
		xOffset -= xMove;
		yOffset -= yMove;
	}
	
	public void tick() {
//		xOffset = (entity.getX() - (gMain.getMaxTilesX() / 2));
//		yOffset = (entity.getY() - (gMain.getMaxTilesY() / 2));
		//xOffset = entity.getX();
	}
	
	public void centerOnMob(Entity mob) {
		xOffset = ((int) mob.getX() - (gMain.getMaxTilesX() * 32 / 2) - (mob.getWidth() / 2));
		yOffset = ((int) mob.getY() - (gMain.getMaxTilesY() * 32 / 2) - (mob.getHeight() / 2));
		checkBlankSpaces();
	}
	
	public void checkBlankSpaces() {
		if(xOffset < 0) xOffset = 0;
		if(yOffset < 0) yOffset = 0;
		if(xOffset > (gMain.getWorldDimension().width * 32) - gMain.getMaxTilesX() * 32) xOffset = gMain.getWorldDimension().width * 32 - gMain.getMaxTilesX() * 32;
		if(yOffset > (gMain.getWorldDimension().height * 32) - gMain.getMaxTilesY() * 32) yOffset = gMain.getWorldDimension().height * 32 - gMain.getMaxTilesY() * 32;
	}
	
	public int getxOffset() {
		return xOffset;
	}
	
	public int getyOffset() {
		return yOffset;
	}

}
