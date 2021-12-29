package com.maycrawer.handlers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.maycrawer.inventory.Item;

public class Burner {
	
	private String name;
	private BufferedImage texture;
	private List<Item> input;
	private List<Item> output; // items for update
	private int fuelCost;
	
	// it is readiness on that build
	private boolean readiness;
	
	public Burner(String name) {
		this.name = name;
		this.input = new ArrayList<Item>();
		this.output = new ArrayList<Item>();
	}
	
	public Burner setTexture(BufferedImage texture) {
		this.texture = texture;
		return this;
	}
	
	public Burner addInput(Item item, int count) {
		this.input.add(new Item(item, count));
		return this;
	}
	
	public Item getInput(int index) {
		return input.get(index);
	}
	
	public int getInputCount() {
		return input.size();
	}
	
	public Burner addOutput(Item item, int count) {
		this.output.add(new Item(item, count));
		return this;
	}
	
	public Item getOutput(int index) {
		return output.get(index);
	}
	
	public int getOutputCount() {
		return output.size();
	}
	
	public String getName() {
		return name;
	}
	
	public BufferedImage getTexture() {
		return texture;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	public Burner setRedadiness(boolean readiness) {
		this.readiness = readiness;
		return this;
	}
	
	public boolean isReady() {
		return readiness;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	public Burner setFuelCost(int fuelCost) {
		this.fuelCost = fuelCost;
		return this;
	}
	
	public int getFuelCost() {
		return fuelCost;
	}
	
}
