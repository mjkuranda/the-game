package com.maycrawer.handlers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.gfx.Assets;
import com.maycrawer.inventory.Inventory;
import com.maycrawer.inventory.Item;
import com.maycrawer.inventory.ItemDrop;
import com.maycrawer.inventory.Items;
import com.maycrawer.sfx.Sounds;
import com.maycrawer.utils.GameEvents;
import com.maycrawer.world.World;

public class Overheat {

	/**
	 * All buildings in this game
	 * 
	 **/
	public Burner gold_ingot = new Burner("Gold Ingot")
										.setTexture(Assets.gold_ingot_texture)
										.setFuelCost(20)
										.addInput(Items.gold_ore, 2)
										.addOutput(Items.gold_ingot, 1);
	public Burner silver_ingot = new Burner("Silver Ingot")
										.setTexture(Assets.silver_ingot_texture)
										.setFuelCost(20)
										.addInput(Items.silver_ore, 2)
										.addOutput(Items.silver_ingot, 1);
	public Burner bronze_ingot = new Burner("Bronze Ingot")
										.setTexture(Assets.bronze_ingot_texture)
										.setFuelCost(20)
										.addInput(Items.bronze_ore, 2)
										.addOutput(Items.bronze_ingot, 1);
	public Burner iron_ingot = new Burner("Iron Ingot")
										.setTexture(Assets.iron_ingot_texture)
										.setFuelCost(20)
										.addInput(Items.iron_ore, 2)
										.addOutput(Items.iron_ingot, 1);

	public List<Burner> burners = new ArrayList<Burner>();

	///////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * This part contains all fields used by this class
	 * 
	 **/

	private GraphicsMain gMain;
	private Handler handler;
	private World world;

	/** These int's are coordinates of this frame **/
	private int x, y;

	/** Is there active? **/
	private boolean active;

	/** Current select burner **/
	private byte select;
	
	/** Amount of fuel **/
	private int fuel;
	
	/** Auxiliary buttons **/
	private Button bWood, bSapling;
	
	/** Time - ever 0.5s, fuel decrease of 1 **/
	private long time;
	
	/**
	 * Next part of class Contains: Render, tick and other methods which are
	 * used by player.
	 * 
	 **/

	public Overheat(GraphicsMain gMain, Handler handler, World world, int fuel) {
		this.gMain = gMain;
		this.handler = handler;
		this.world = world;
		
		this.fuel = fuel;
		
		this.active = false;

		// burners
		this.burners.add(gold_ingot);
		this.burners.add(silver_ingot);
		this.burners.add(bronze_ingot);
		this.burners.add(iron_ingot);

		// current set values of dimension -> 704 x 384
		this.x = gMain.getWidthScreen() / 2 - (704 / 2);
		this.y = gMain.getHeightScreen() / 2 - (384 / 2);
		
		// buttons
		int xf = x + (704 / 2) + 120;
		int yf = y + (384 - 32 + 6);
		this.bWood = new Button("", xf, yf, 20, 20);
		this.bSapling = new Button("", xf + 40, yf, 20, 20);
	}

	/**
	 * This method draws a frame which shows all know buildings in "The Game" :)
	 * 
	 **/
	public void render(Graphics g) {
		/** Drawing a background frame **/
		g.drawImage(Assets.buildings_texture, x, y, null);

		/** Draw 3D border **/
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.draw3DRect(this.x, this.y, 704, 384, true);
		
		/** Building title **/
		g.setColor(new Color(150, 150, 150, 150));
		g.setFont(Assets.font48);
		int xstr = x + (704 / 2) - (g.getFontMetrics().stringWidth("Furnace") / 2);
		int ystr = y + 20 + g.getFontMetrics().getHeight() / 2;
		g.drawString("Furnace", xstr, ystr);

		/** Drawing all informations **/
		g.setColor(Color.WHITE);
		g.setFont(Assets.font28);
		g.drawString("Name", x + 128, y + 64 + 24);
		g.drawString("Input", x + 288, y + 64 + 24);
		g.drawString("Output", x + 480, y + 64 + 24);
		
		/** Render bar of fuel **/
		renderFuel(g);
		
		/** Render auxiliary buttons **/
		renderAuxiliaryButtons(g);

		/** Rendering of all buildings line **/
		for (int b = 0; b < burners.size(); b++) {
			/** Instance **/
			Burner burners = this.burners.get(b);

			/** Texture **/
			g.drawImage(burners.getTexture(), x + 96 + 2, y + 96 + 2 + (b * 32), 28, 28, null);

			/** Title **/
			g.setColor(Color.BLACK);
			g.setFont(Assets.font26);
			g.drawString(burners.getName(), x + 128 + 8, y + 96 + 24 + (b * 32));

			/** Items and count **/
			for (int i = 0; i < burners.getInputCount(); i++) {
				Item item = burners.getInput(i);

				// render his texture
				g.drawImage(item.getTexture(), x + 288 + (i * 32), y + 96 + (b * 32), 32, 32, null);

				if (item.getCount() > 1) {
					g.setColor(Color.BLACK);
					g.setFont(new Font("Consolas", Font.PLAIN, 18));
					int count_width = g.getFontMetrics().stringWidth("" + item.getCount());
					g.drawString("" + item.getCount(), x + 288 + 31 - count_width + (i * 32), y + 126 + (b * 32));
				}
			}
			
			/** Items for update and his count **/
			for (int i = 0; i < burners.getOutputCount(); i++) {
				Item item = burners.getOutput(i);

				// render his texture
				g.drawImage(item.getTexture(), x + 480 + (i * 32), y + 96 + (b * 32), 32, 32, null);

				if (item.getCount() > 1) {
					g.setColor(Color.BLACK);
					g.setFont(new Font("Consolas", Font.PLAIN, 18));
					int count_width = g.getFontMetrics().stringWidth("" + item.getCount());
					g.drawString("" + item.getCount(), x + 480 + 31 - count_width + (i * 32), y + 126 + (b * 32));
				}
			}

			/** Buttons **/
			BufferedImage bi = Assets.button_impossible;
			if (burners.isReady())
				bi = Assets.button_possible;
			g.drawImage(bi, x + 32, y + 96 + (b * 32), null);
		}

		/** Is something select? **/
		if (this.select != -1 && this.select < this.burners.size()) {
			g.setColor(new Color(0, 0, 125, 125));
			g.fillRect(x, y + 96 + (this.select * 32), 672, 32);
		}
	}

	/**
	 * Every one tick program updates this class.
	 * 
	 **/
	public void tick() {
		this.fuel = world.getCurrentTile().getValue();
		
		/** Give all handlers (almost) :) **/
		MouseManager mm = this.handler.getMouseManager();

		int xcurr = mm.getXMoved() - this.x; /** Interval: <0, 672> **/
		int ycurr = mm.getYMoved() - this.y - 96; /** Interval: <0, 256> **/

		/** Set up select building **/
		if (ycurr >= 0 && ycurr <= 256 && xcurr >= 0 && xcurr <= 672) {
			this.select = (byte) (ycurr / 32);
		} else
			this.select = (byte) -1;

		/**
		 * Selecting
		 * 
		 * If you clicked and 'select' isn't equals '-1' also you are focusing
		 * the mouse on the some building, AND your cursor is on button, then:
		 * 
		 **/
		if (mm.isClicked() && this.select != -1 && this.select < this.burners.size() && xcurr >= 32 && xcurr < 64
				&& ycurr >= 0 && ycurr < 256) {
			
			Burner burner = burners.get(this.select);
			
			/**
			 * If you have all needed resources to build this.
			 * 
			 * **/
			if(this.burners.get(this.select).isReady() && isFuelEnoughAmount(burner)) {

				this.overheat(burner);
				Sounds.STONE_HIT.play();
				mm.clicked();
				
				/** Upgrade all buildings whether you can build **/
				this.checkAllEnoughItems();
			}
			/**
			 * In other case, if furnace has too low
			 * level of fuel, you can give information
			 * about fuel level:
			 * 
			 * **/
			else
			if(!isFuelEnoughAmount(burner))
				world.addEvent(GameEvents.LOW_FUEL);
			/**
			 * In other case, if you haven't needed resources
			 * you give message:
			 * 
			 * **/
			else
				world.addEvent(GameEvents.NO_RESOURCE);
		}
		
		/** Clicked you on any buttons? **/
		Inventory in = world.getPlayer().getInventory();
		if(this.bWood.isClicked() && mm.isClicked()) {
			if(isEmptyEnoughAmount(Items.wood)) {
				if(in.hasItem(Items.wood)) {
					if(in.hasItem(Items.wood)) {
						in.takeItem(Items.wood, 1);
						fuel += 75;	
					} 
				} else world.addEvent(GameEvents.NO_RESOURCE);
			} else world.addEvent(GameEvents.LACK_OF_SPACE);
		}
		if(this.bSapling.isClicked() && mm.isClicked()) {
			if(isEmptyEnoughAmount(Items.sapling)) {
				if(in.hasItem(Items.sapling)) {
					in.takeItem(Items.sapling, 1);
					fuel += 7;
				} else world.addEvent(GameEvents.NO_RESOURCE);	
			} else world.addEvent(GameEvents.LACK_OF_SPACE);
		}

		mm.clicked();
		
		checkAllEnoughItems();
		
		updateFuel();

		/** Check buttons **/
		int xm = mm.getXMoved();
		int ym = mm.getYMoved();
		this.bWood.check(xm, ym);
		this.bSapling.check(xm, ym);
		
		world.getCurrentTile().setValue(fuel);
	}
	
	/**
	 * Render level of fuel
	 * 
	 * **/
	private void renderFuel(Graphics g) {
		
		/** Calculate bar length **/
//		int length = ()
		
		int xf = x + (704 / 2) - 100;
		int yf = y + (384 - 32 + 6);
		
		/** Fill center **/
		g.setColor(Color.GREEN.brighter());
		g.fillRect(xf, yf, fuel, 20);
		
		/** Render of main borders **/
		g.setColor(Color.BLACK);
		g.drawRect(xf, yf, 200, 20);
	
		/** Title on bar **/
		g.setColor(Color.WHITE);
		g.setFont(Assets.font18);
		int lt = g.getFontMetrics().stringWidth("Fuel");
		g.drawString("Fuel", xf + 100 - (lt / 2), yf + 16);
	}

	/**
	 * Render auxiliary buttons
	 * 
	 * **/
	private void renderAuxiliaryButtons(Graphics g) {
		
		/** Calculate coordinates **/
		int xf = x + (704 / 2) + 120;
		int yf = y + (384 - 32 + 6);
		
		/** Render wood button **/
		this.bWood.render(g, handler);
		renderTransparent(g, this.bWood, xf, yf);
		g.drawImage(Assets.wood_texture, xf, yf, 20, 20, null);
		
		/** Render sapling button **/
		this.bSapling.render(g, handler);
		renderTransparent(g, this.bSapling, xf + 40, yf);
		g.drawImage(Assets.sapling_texture, xf + 40, yf, 20, 20, null);
	}
	
	/**
	 * If you have mouse over button, then:
	 * 
	 * **/
	private void renderTransparent(Graphics g, Button b, int xb, int yb) {
		
		/** Set up color **/
		g.setColor(new Color(200, 200, 200, 200));
		
		/** Render field **/
		if(b.isMoved()) {
			g.fillRect(xb, yb, 20, 20);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	// Activity part
	///////////////////////////////////////////////////////////////////////////////////

	/**
	 * It returns value of frame activity
	 **/
	public boolean isActive() {
		return active;
	}

	/**
	 * It changes frame activity for example: false -> true or: true -> false
	 * 
	 * Also, checks whether you have enough count of items to make it
	 **/
	public void changeActivity() {
		this.active = !this.active;

		if (this.active) {
			// Check for all burners
			checkAllEnoughItems();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////
	// Remaining methods part
	///////////////////////////////////////////////////////////////////////////////////

	/**
	 * This method overheats selected tile by you.
	 * 
	 **/
	public void overheat(Burner burner) {
		
		/**
		 * Take all inputs
		 * 
		 * **/
		for(int i = 0; i < burner.getInputCount(); i++) {
			Item item = burner.getInput(i);
			world.getPlayer().getInventory().takeItem(item, item.getCount());
		}
		
		/**
		 * Give all outputs
		 * 
		 * **/
		for(int i = 0; i < burner.getOutputCount(); i++) {
			Item item = burner.getOutput(i);
			int x = world.getCurrentTile().getX() * 32 + 8;
			int y = world.getCurrentTile().getY() * 32 + 16;
			world.addItemDrop(new ItemDrop(item, item.getCount(), x, y));
		}
		
		this.fuel -= burner.getFuelCost();
		
	}

	/**
	 * This method checks whether you have all needed items.
	 * 
	 **/
	public void checkAllEnoughItems() {
		Inventory in = this.world.getPlayer().getInventory();

		// For all buildings
		for (int b = 0; b < burners.size(); b++) {

			boolean check = true;

			/**
			 * Check whether you have all needed items to build this
			 **/
			for (int i = 0; i < burners.get(b).getInputCount(); i++) {
				Item ii = burners.get(b).getInput(i);
				Item ii2 = in.getWholeItem(ii);
				
				if (ii2 == null || (ii2 != null && (ii2.getCount() < ii.getCount() || ii2.getCount() == 0))) {
					check = false;
					System.out.println("Oups");
				}
			}
			
			/** Check whether you have enough amount fuel **/
			if(!isFuelEnoughAmount(burners.get(b))) check = false;
			
			burners.get(b).setRedadiness(check);
		}
	}
	
	/**
	 * This method checks if furnace has enough amount
	 * of fuel to overheat it.
	 * 
	 * **/
	private boolean isFuelEnoughAmount(Burner burner) {
		return (fuel >= burner.getFuelCost());
	}
	
	/**
	 * This method checks if you have enough empty
	 * amount of fuel in furnace.
	 * 
	 * This item is 'given' to the furnace.
	 * 
	 * **/
	private boolean isEmptyEnoughAmount(Item item) {
		int fuel = 0;
		switch(item.getId()) {
		case Items.WOOD_ID:
			fuel = 75;
			break;
		case Items.SAPLING_ID:
			fuel = 7;
			break;
		}
		return (this.fuel + fuel <= 200);
	}
	
	/**
	 * Manager of fuel
	 * 
	 * **/
	private void updateFuel() {
		
		/** Update fuel **/
		if(System.currentTimeMillis() >= time + 500) {
			fuel--;
			time = System.currentTimeMillis();
		}
		
		/** Check it well **/
		if(this.fuel < 0) fuel = 0;
		if(this.fuel > 200) fuel = 200;
	}
	
}
