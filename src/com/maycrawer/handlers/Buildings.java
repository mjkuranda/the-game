package com.maycrawer.handlers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.entities.Player;
import com.maycrawer.gfx.Assets;
import com.maycrawer.inventory.Inventory;
import com.maycrawer.inventory.Item;
import com.maycrawer.inventory.Items;
import com.maycrawer.sfx.Sounds;
import com.maycrawer.tiles.Tile;
import com.maycrawer.tiles.TileFirecamp;
import com.maycrawer.tiles.TileFurnace;
import com.maycrawer.tiles.TileTower;
import com.maycrawer.tiles.Tiles;
import com.maycrawer.utils.GameEvents;
import com.maycrawer.world.World;

public class Buildings {

	/**
	 * All buildings in this game
	 * 
	 **/
	public ArchitectureManager tower = new ArchitectureManager("Tower")
														.setTexture(Assets.tower_textures[10])
														.addItem(Items.wood, 4)
														.addItem(Items.stone, 4)
														.addItemUpdate(Items.wood, 1)
														.addItemUpdate(Items.stone, 1);
	public ArchitectureManager firecamp = new ArchitectureManager("Firecamp")
														.setTexture(Assets.firecamp_texture)
														.addItem(Items.wood, 2)
														.addItem(Items.stone, 8);
	public ArchitectureManager furnace = new ArchitectureManager("Furnace")
														.setTexture(Assets.furnace_textures[9])
														.addItem(Items.wood, 2)
														.addItem(Items.stone, 4)
														.addItemUpdate(Items.stone, 2);

	public List<ArchitectureManager> buildings = new ArrayList<ArchitectureManager>();

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

	/** Current select building **/
	private byte select;

	/**
	 * Next part of class Contains: Render, tick and other methods which are
	 * used by player.
	 * 
	 **/

	public Buildings(GraphicsMain gMain, Handler handler, World world) {
		this.gMain = gMain;
		this.handler = handler;
		this.world = world;

		this.active = false;

		// buildings
		this.buildings.add(tower);
		this.buildings.add(firecamp);
		this.buildings.add(furnace);

		// current set values of dimension -> 704 x 384
		this.x = gMain.getWidthScreen() / 2 - (704 / 2);
		this.y = gMain.getHeightScreen() / 2 - (384 / 2);
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
		int xstr = x + (704 / 2) - (g.getFontMetrics().stringWidth("Building Manager") / 2);
		int ystr = y + 20 + g.getFontMetrics().getHeight() / 2;
		g.drawString("Building Manager", xstr, ystr);

		/** Drawing all informations **/
		g.setColor(Color.WHITE);
		g.setFont(Assets.font28);
		g.drawString("Name", x + 128, y + 64 + 24);
		g.drawString("To  start", x + 288, y + 64 + 24);
		g.drawString("Every  update", x + 480, y + 64 + 24);

		/** Rendering of all buildings line **/
		for (int b = 0; b < buildings.size(); b++) {
			/** Instance **/
			ArchitectureManager building = this.buildings.get(b);

			/** Texture **/
			g.drawImage(building.getTexture(), x + 96 + 2, y + 96 + 2 + (b * 32), 28, 28, null);

			/** Title **/
			g.setColor(Color.BLACK);
			g.setFont(Assets.font28);
			g.drawString(building.getName(), x + 128 + 8, y + 96 + 24 + (b * 32));

			/** Items and count **/
			for (int i = 0; i < building.getItemCount(); i++) {
				Item item = building.getItem(i);

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
			for (int i = 0; i < building.getItemUpdateCount(); i++) {
				Item item = building.getItemUpdate(i);

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
			if (building.isReady())
				bi = Assets.button_possible;
			g.drawImage(bi, x + 32, y + 96 + (b * 32), null);
		}

		/** Is something select? **/
		if (this.select != -1 && this.select < this.buildings.size()) {
			g.setColor(new Color(0, 0, 125, 125));
			g.fillRect(x, y + 96 + (this.select * 32), 672, 32);
		}
	}

	/**
	 * Every one tick program updates this class.
	 * 
	 **/
	public void tick() {
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
		if (mm.isClicked() && this.select != -1 && this.select < this.buildings.size() && xcurr >= 32 && xcurr < 64
				&& ycurr >= 0 && ycurr < 256) {
			
			/**
			 * If you are on plains
			 * then:
			 * **/
			if(world.getCurrentTile().getId() == Tiles.ID_GRASS) {
				/**
				 * If you have all needed resources to build this.
				 * 
				 * **/
				if(this.buildings.get(this.select).isReady()) {
					Player p = (Player) world.getPlayer();
					ArchitectureManager building = buildings.get(this.select);
					int x = (int) (world.getCurrentTile().getX());
					int y = (int) (world.getCurrentTile().getY());

					/** Take items of selected building **/
					for (int i = 0; i < building.getItemCount(); i++) {
						Item item = building.getItem(i);
						p.getInventory().takeItem(item, item.getCount());
					}

					this.build(x, y);
					Sounds.STONE_HIT.play();
					mm.clicked();
					
					/** Upgrade all buildings whether you can build **/
					this.checkAllEnoughItems();

					/** Close a frame **/
					this.changeActivity();	
				}
				/**
				 * In other case, if you haven't needed resources
				 * you give message:
				 * 
				 * **/
				else
					world.addEvent(GameEvents.NO_RESOURCE);
			}
			/**
			 * In other case, if you aren't on the plains,
			 * you give a message:
			 * 
			 * **/
			else
				world.addEvent(GameEvents.BAD_PLACE);
		}

		mm.clicked();
		
		checkAllEnoughItems();

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
			// Check for all buildings
			checkAllEnoughItems();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////
	// Remaining methods part
	///////////////////////////////////////////////////////////////////////////////////

	/**
	 * This method builds selected tile by you.
	 * 
	 **/
	public void build(int x, int y) {
		Tile t = null;
		
		switch (this.select) {
		/** The Tower **/
		case 0:
			t = new TileTower(this.world, x, y);
			break;
		/** The Fire camp **/
		case 1:
			t = new TileFirecamp(this.world, x, y);
			break;
		/** The Furnace **/
		case 2:
			t = new TileFurnace(this.world, x, y);
			break;
		}
		
		world.addTile(t);
	}

	/**
	 * This method checks whether you have all needed items.
	 * 
	 **/
	public void checkAllEnoughItems() {
		Inventory in = this.world.getPlayer().getInventory();

		// For all buildings
		for (int b = 0; b < buildings.size(); b++) {

			boolean check = true;

			/**
			 * Check whether you have all needed items to build this
			 **/
			for (int i = 0; i < buildings.get(b).getItemCount(); i++) {
				Item ii = buildings.get(b).getItem(i);
				Item ii2 = in.getWholeItem(ii);
				
				if (ii2 == null || (ii2 != null && (ii2.getCount() < ii.getCount() || ii2.getCount() == 0))) {
					check = false;
					System.out.println("Oups");
				}
			}

			buildings.get(b).setRedadiness(check);
		}
	}

}
