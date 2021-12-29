package com.maycrawer.inventory;

public class Type {
	
	private String name;
	private byte id;
	
	public Type(String name, byte id) {
		this.name = name;
		this.id = id;
	}
	
	////////////////////////-GETTERS-AND-SETTERS-///////////////////////////////

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

}
