package com.maycrawer.handlers;

public class Handler {

	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	public Handler() {
		this.keyManager = new KeyManager();
		this.mouseManager = new MouseManager();
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}

}
