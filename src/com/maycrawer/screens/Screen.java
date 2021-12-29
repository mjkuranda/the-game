package com.maycrawer.screens;

import java.awt.Graphics;

public abstract class Screen {

	public Screen() {
	}
	
	public abstract void render(Graphics g);
	
	public abstract void tick();

}
