package com.maycrawer.handlers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.maycrawer.inventory.Item;
import com.maycrawer.tiles.Tile;

public class ArchitectureManager {

	private String name;
	private BufferedImage texture;
	private List<Item> items;
	private List<Item> items_upd; // items for update
	private Tile tile;
	
	// it is readiness on that build
	private boolean readiness;
	
	public ArchitectureManager(String name) {
		this.name = name;
		this.items = new ArrayList<Item>();
		this.items_upd = new ArrayList<Item>();
	}
	
	public ArchitectureManager setTexture(BufferedImage texture) {
		this.texture = texture;
		return this;
	}
	
	public ArchitectureManager addItem(Item item, int count) {
		this.items.add(new Item(item, count));
		return this;
	}
	
	public Item getItem(int index) {
		return items.get(index);
	}
	
	public int getItemCount() {
		return items.size();
	}
	
	public ArchitectureManager addItemUpdate(Item item, int count) {
		this.items_upd.add(new Item(item, count));
		return this;
	}
	
	public Item getItemUpdate(int index) {
		return items_upd.get(index);
	}
	
	public int getItemUpdateCount() {
		return items_upd.size();
	}
	
	public String getName() {
		return name;
	}
	
	public BufferedImage getTexture() {
		return texture;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	public ArchitectureManager setRedadiness(boolean readiness) {
		this.readiness = readiness;
		return this;
	}
	
	public boolean isReady() {
		return readiness;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	public ArchitectureManager setTile(Tile tile) {
		this.tile = tile;
		return this;
	}
	
	public Tile getTile() {
		return tile;
	}

}
