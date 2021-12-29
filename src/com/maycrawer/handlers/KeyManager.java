package com.maycrawer.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	private boolean[] keys;
	
	public KeyManager() {
		keys = new boolean[2048];
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		keys[keyCode] = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		keys[keyCode] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	public boolean getKey(int keyCode) {
		return keys[keyCode];
	}
	
	public void dropKey(int keyCode) {
		this.keys[keyCode] = false;
	}

}