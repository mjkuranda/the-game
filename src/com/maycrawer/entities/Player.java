package com.maycrawer.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.gfx.Animations;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.Buildings;
import com.maycrawer.handlers.CameraManager;
import com.maycrawer.handlers.Handler;
import com.maycrawer.handlers.KeyManager;
import com.maycrawer.handlers.MouseManager;
import com.maycrawer.handlers.Overheat;
import com.maycrawer.inventory.Inventory;
import com.maycrawer.inventory.Item;
import com.maycrawer.inventory.ItemDrop;
import com.maycrawer.inventory.Items;
import com.maycrawer.screens.GameScreen;
import com.maycrawer.sfx.Sounds;
import com.maycrawer.tiles.FallingObject;
import com.maycrawer.tiles.Objects;
import com.maycrawer.tiles.Tile;
import com.maycrawer.tiles.TileForest;
import com.maycrawer.tiles.Tiles;
import com.maycrawer.utils.GameEvents;
import com.maycrawer.utils.MapRenderer;
import com.maycrawer.world.World;

public class Player extends Entity {
	
	private GraphicsMain gMain;
	private World world;
	private Handler handler;
	private CameraManager camera;
	private MapRenderer mapRender;
	private Overheat overheat;
	
	private boolean active_inventory, active_escape, active_map;
	private boolean main_hand = true;
	
	private byte count_anim, count_sound;
	
	/** maximal value character current's level **/
	private short level_points_max; 
	/** current character level**/
	private short level;
	
	/** to define lines **/
	private boolean lines;
	
	public Player(GraphicsMain gMain, World world, Handler handler, CameraManager camera, int x, int y) {
		super("Player", 0.75f, 100, 100, 100, 100, x, y, 32, 32, (byte) 32, (byte) 120, Animations.ANIMATION_PLAYER_STOPPED,
				Animations.ANIMATION_PLAYER_WALKING, Animations.ANIMATION_PLAYER_SWIMMING, null,
				false, Entities.PLAYER_ID, Sounds.ENTITY_HURT, Sounds.ENTITY_HURT);
		this.addCollider(new Rectangle((int) this.x + 2, (int) this.y + 2, 28, 28));
		this.addStriker(new Rectangle((int) this.x - 5, (int) this.y - 5, 42, 42));
		this.gMain = gMain;
		this.world = world;
		this.handler = handler;
		this.camera = camera;
		this.mapRender = new MapRenderer(gMain, handler, this);
		
		this.level_points_max = 100;
		this.level = 0;
		
		this.buildings = new Buildings(gMain, handler, world);
		
		this.overheat = new Overheat(gMain, handler, world, 0);
		
		this.inventory = new Inventory(gMain, handler);
		this.inventory.addNewItem(Items.slimeball, 10);
		this.inventory.addNewItem(Items.slimeball, 5);
		this.inventory.addNewItem(Items.wood, 1);
		this.inventory.addNewItem(Items.slimeball, 9);
		this.inventory.addNewItem(Items.stone, 3);
		this.inventory.addNewItem(Items.sapling, 2);
		
		this.inventory.addItem(Items.iron_sword, 1);
		this.inventory.addItem(Items.iron_axe, 1);
		this.inventory.addItem(Items.iron_pickaxe, 1);
		
		this.inventory.addItem(Items.gold_coin, 1);
		this.inventory.addItem(Items.silver_coin, 1);
		this.inventory.addItem(Items.bronze_coin, 1);
	}
	
	public Player() {
		super("Player", 3, 100, 100, 100, 100, 0, 0, 28, 28, (byte) 32, (byte) 120, null,
				null, null, null, 
				false, Entities.PLAYER_ID, Sounds.ENTITY_HURT, Sounds.ENTITY_HURT);
	}

	@Override
	public void render(Graphics g) {
		
		int x = (int) this.x - camera.getxOffset();
		int y = (int) this.y - camera.getyOffset();
		
		int animX = 0;
		int animY = 0;
		
		if(isSwimming()) {
			g.drawImage(anim_swimming.getCurrentFrame(), x, y, width, height, null);
			animY = -3 - anim_swimming.getCurrentFrameID();
		} else if(isWalking() && !isSwimming()) {
			g.drawImage(anim_walking.getCurrentFrame(), x, y, width, height, null);
			animY = anim_walking.getCurrentFrameID() / 2;
		} else {
			g.drawImage(anim_stopped.getCurrentFrame(), x, y, width, height, null);
		}
		
		//////////////////////////////-HANDS-/////////////////////////////////////
		
		Item main = (main_hand) ? inventory.getItemRightHand() : inventory.getItemLeftHand();
		int correct = (main != null) ? 1 : 0;
		//correct = (correct == 1 && main_hand) ? 100 : 290;
		int angle = (-1 * getStrike() / 4) - 7;
		correct = (correct == 1 && main_hand) ? angle : (getStrike() - 20);
//		System.out.println(angle);
		
		if(inventory.getItemLeftHand() != null) {
			if(strike < max_strike && !main_hand)
				inventory.getItemLeftHand().render(g, camera, (int) this.x, (int) this.y, animX + 14, animY - 2, correct, false);
			else inventory.getItemLeftHand().render(g, camera, (int) this.x, (int) this.y, animX, animY, 0, false);			
		}

		if(inventory.getItemRightHand() != null) {
			if(strike < max_strike & main_hand)
				inventory.getItemRightHand().render(g, camera, (int) this.x, (int) this.y, animX - 6 + 2, animY + 4 - 3 + (angle / -8), correct, true);
			else inventory.getItemRightHand().render(g, camera, (int) this.x, (int) this.y, animX, animY, 0, true);
		}
	}
	
	@Override
	public void renderGUI(Graphics g) {
		int x = (int) this.x - camera.getxOffset();
		int y = (int) this.y - camera.getyOffset();
		
		//////////////////////////-DRAW-LIFE-GUI-/////////////////////////////////
		drawGUI(g, "Life", new Color(200, 0, 0, 175), 10, gMain.getHeight() - 70, (short) 250, (short) 24, getLife(), getMaxLife());
		
		drawGUI(g, "Strike", new Color(175, 75, 50, 175), 10, gMain.getHeight() - 30, (short) 250, (short) 24, getStrike(), getMaxStrike());
		
		drawGUI(g, "Points", new Color(0, 255, 0, 175), (gMain.getWidth() / 2) - 300, gMain.getHeight() - 30, (short) 600, (short) 24, GameScreen.score, (int) level_points_max);
		renderLevel(g, (short) ((gMain.getWidth() / 2) - 128), (short) (gMain.getHeight() - 30 - 25), (short) 256, (short) 24);
		
		if(getDamageTime() != 0) {
			g.drawImage(Assets.player_damage_skin, x, y, 32, 32, null);
			this.damage_time++;
		
			drawFillingBar(g, new Color(230, 230, 230, 100), 10, gMain.getHeight() - 70, (short) 250, (short) 24, getMaxLife(), getLife());
		}
		
		if(isCured()) {
			drawFillingBar(g, new Color(255, 0, 0, 100), 10, gMain.getHeight() - 70, (short) 250, (short) 24, getMaxLife(), getLife());
			this.cured = false;
		}
		
		if(this.active_inventory) this.inventory.render(g);
		
		if(this.buildings.isActive()) this.buildings.render(g);
		
		if(this.overheat.isActive()) this.overheat.render(g);
		
		//g.setColor(new Color(255, 0, 0, 100));
		//g.drawRect(((x - camera.getxOffset() + 16) / 32) * 32, ((y - camera.getyOffset() + 16) / 32) * 32, width, height);
		
		if(lines) g.drawRect(256, 256, 1280 - 512, 1024 - 512);
		
		if(active_map) {
			this.mapRender.render(g);
		}
	}

	@Override
	public void tick() {
		KeyManager key = handler.getKeyManager();
		boolean esc = key.getKey(KeyEvent.VK_ESCAPE);
		
		if(esc) {
			active_escape = !active_escape;
			world.changeActivity();
			key.dropKey(KeyEvent.VK_ESCAPE);
		}
		
		if(world.isActive()) active_escape = false;
		
		if(!this.active_escape) {
			Animations.ANIMATION_PLAYER_STOPPED.tick();
			Animations.ANIMATION_PLAYER_WALKING.tick();
			Animations.ANIMATION_PLAYER_SWIMMING.tick();
			
			this.refreshCollider(6, 6, 20, 20);
			this.refreshStriker(-8, -8, 48, 48);
			
			boolean w = key.getKey(KeyEvent.VK_W);
			boolean a = key.getKey(KeyEvent.VK_A);
			boolean s = key.getKey(KeyEvent.VK_S);
			boolean d = key.getKey(KeyEvent.VK_D);
			
			boolean e = key.getKey(KeyEvent.VK_E);
			boolean i = key.getKey(KeyEvent.VK_I);
			
			boolean spc = key.getKey(KeyEvent.VK_SPACE);
			boolean r = key.getKey(KeyEvent.VK_R);
			boolean m = key.getKey(KeyEvent.VK_M);
			
			boolean b = key.getKey(KeyEvent.VK_B);
			boolean p = key.getKey(KeyEvent.VK_P);
			
			boolean shift = key.getKey(KeyEvent.VK_SHIFT);
			
			MouseManager mm = handler.getMouseManager();
			
			if(m) {
				this.active_map = !active_map;
				key.dropKey(KeyEvent.VK_M);
			}
			
			if(b) {
				/**
				 * Get item from main hand
				 * **/
				Item item = (main_hand) ? inventory.getItemRightHand() : inventory.getItemLeftHand();
				
				/**
				 * If you have a hammer in the main hand
				 * you can build some building.
				 * 
				 * **/
				if(item != null && item.getId() == Items.IRON_HAMMER_ID) {
					if(!active_inventory) {
						this.buildings.changeActivity();
						Sounds.CLICK.play();
					}
				}
				/**
				 * In the other case, if you haven't a hammer
				 * in main hand, then:
				 * 
				 * **/
				else
					world.addEvent(GameEvents.NEEDED_HAMMER);
				
				key.dropKey(KeyEvent.VK_B);
			}
			
			if(p && world.getCurrentTile().getAge() == 9) {
				/**
				 * Get current tile
				 * **/
				Tile t = world.getCurrentTile();
				
				/**
				 * If you are standing over the furnace
				 * you can overheat something.
				 * 
				 * **/
				if(t.getId() == Tiles.ID_FURNACE) {
					if(!active_inventory && !this.buildings.isActive()) {
						this.overheat.changeActivity();
						Sounds.CLICK.play();
					}
				}
				
				key.dropKey(KeyEvent.VK_P);
			}
			
			if(active_map) {
				this.mapRender.tick();
			}
			
			if(shift) {
				key.dropKey(KeyEvent.VK_SHIFT);
				setRunning(true);
			} else setRunning(false);

			if(!this.active_inventory && !this.buildings.isActive() && !overheat.isActive()) {
				Tile tile = this.world.getCurrentTile();
				
				if(w && (y - speed) / 32 >= 0) y -= getSpeed(tile);
				if(a && (x - speed) / 32 >= 0) x -= getSpeed(tile);
				if(s && (y + speed) / 32 < world.getTilesY() - 1) y += getSpeed(tile);
				if(d && (x + speed) / 32 < world.getTilesX() - 1) x += getSpeed(tile);
				
				setWalking((w || a || s || d));	
			}
			
			if(isSwimming()) {
				count_anim++;
				if(count_anim == 4) {
					world.addParticles(Particles.PARTICLE_WATER,
							this.getX(),
							this.getY(), (byte) 1, (byte) 1);
					count_anim = 0;
					if(isWalking()) count_sound++;
					if(isWalking() && count_sound >= 10) {
						if(new Random().nextBoolean()) Sounds.SWIM.play(); else Sounds.SWIM_2.play();
						count_sound = 0;
					}
				}
			} else count_anim = 0;
			
			if(this.life < 0) this.life = 0;
			else if(this.life > this.max_life) this.life = this.max_life;
			
			if(x < 20) x = 20;
			if(y < 20) y = 20;
			if(x >= gMain.getWorldDimension().width * 32) x = gMain.getWorldDimension().width - 32;
		
			if(e) {
				if(!buildings.isActive() && !overheat.isActive()) {
					this.active_inventory = !this.active_inventory;	
					if(!this.active_inventory) {
						Item select = inventory.getSelectedItem();
						if(select != null) inventory.addItem(select, select.getCount());
						inventory.setSelectItem(null);
					}
					Sounds.CLICK.play();
				}
				key.dropKey(KeyEvent.VK_E);
			}
			
			if(i) {
				this.inventory.resetInventory();
				this.inventory.setRandomInventory();
				key.dropKey(KeyEvent.VK_I);
			}
			
			boolean l = key.getKey(KeyEvent.VK_L);
			// to render border of the field
			if(l) {
				this.lines = !lines;
				key.dropKey(KeyEvent.VK_L);
			}
			
			if(spc && getStrike() == getMaxStrike() && !active_inventory && !buildings.isActive() && !overheat.isActive()) {
				Sounds.SWISH.play();
				if(!isSwimming()) {
					Item mainHand = (main_hand) ? inventory.getItemRightHand() : inventory.getItemLeftHand();
					
					List<Entity> strs = world.getStrikedEntity();
					int dmg = (mainHand != null) ? (mainHand.getDamage() * -1) : 0;
					
					boolean isStrike = false;
					
					for(int en = 0; en < strs.size(); en++) {
						strs.get(en).addLife(dmg);
						isStrike = true;
						
						if(mainHand == null) {
							world.addEvent(GameEvents.NO_TOOL);
							Sounds.BOW_SHOT.play();
						}
					}
					
					//---------------------------HITTING-OBJECT-----------------------------------------------//
					Random rrr = new Random();
					Tile curr_tile = this.world.getCurrentTile();
					
					// if you have bow in main hand
					if(mainHand != null && mainHand.getId() == Items.WOODEN_BOW_ID) {
						
						if(GameScreen.arrows > 0) {
							this.world.addArrow(new Arrow((int) x, (int) y,
									(int) (mm.getXMoved() + camera.getxOffset()),
									(int) (mm.getYMoved() + camera.getyOffset())));
							GameScreen.addArrow(-1);
						}
						else world.addEvent(GameEvents.NO_ARROW);
					}
					// else
					else
					// if you have the hammer in main hand
					if(mainHand != null && mainHand.getId() == Items.IRON_HAMMER_ID) {
						this.strike = 0;
						this.progress_strike = (mainHand != null) ?
													mainHand.getStrikeProgress() : 5;
						if(mainHand != null && mainHand.getId() == Items.IRON_HAMMER_ID) {
//							if(inventory.hasItem(Items.wood) && inventory.hasItem(Items.stone)) {
								//if(world.getCurrentTile().getId() != Tiles.ID_TOWER && world.getCurrentTile().getId() == Tiles.ID_GRASS) {
								//	int x = world.getCurrentTile().getX();
								//	int y = world.getCurrentTile().getY();
								//	world.addTile(new TileTower(world, x, y));
								//	Sounds.STONE_HIT.play();
								//} else 
								if(world.getCurrentTile().getId() == Tiles.ID_TOWER ||
										world.getCurrentTile().getId() == Tiles.ID_FURNACE) {
									world.getCurrentTile().setHammer(true);
								} else world.addEvent(GameEvents.BAD_PLACE);
//							} else world.addEvent(GameEvents.NO_RESOURCE);
						}
						/**
						 *
						 * But, if you aren't holding the hammer
						 * in your hand, you can put nothing.
						 * **/
						else world.addEvent(GameEvents.NEEDED_HAMMER);
					}
					else
					/**
					 * In the other case, if you are holding
					 * the sapling, you can put it on plains.
					 * 
					 * **/
					if(mainHand != null && mainHand.getId() == Items.SAPLING_ID) {
						int x = world.getCurrentTile().getX();
						int y = world.getCurrentTile().getY();
						
						if(world.getCurrentTile().getId() == Tiles.ID_GRASS) {
							inventory.takeItemFromMainHand(main_hand, 1);
							world.addTile(new TileForest(x, y, (byte) 0));	
						}
						/**
						 * 
						 * If it is other tile than grass tile
						 * **/
						else
							world.addEvent(GameEvents.BAD_PLACE);
						
					}
					// else
					else
					// if you strike but hit in nothing
					if(!isStrike && curr_tile.getObj() == null) {
						Sounds.SWISH.play();
					} else
					// if you hit
					if(!isStrike && curr_tile.getObj() != null) {
						world.addParticles(Objects.PARTICLES[curr_tile.getId()], world.getCurrentTile().getX() * 32, world.getCurrentTile().getY() * 32, (byte) 5, (byte) 3);
						curr_tile.getObj().getClip().play();
						
						// if you hit good tool
						if(mainHand != null && mainHand.getId() == Objects.TOOLS[curr_tile.getId()].getId()) {
							world.getCurrentTile().getObj().hit();
							
							// if has extra drop. It happens after hit
							if(world.getCurrentTile().getObj().getExtraDrop() != null && rrr.nextBoolean()) {
								byte xr = (byte) (rrr.nextInt(16) - 8);
								byte yr = (byte) (rrr.nextInt(16) - 8);
								world.addItemDrop(new ItemDrop(curr_tile.getObj().getExtraDrop(), 1, curr_tile.getX() * 32 + 16 + xr, curr_tile.getY() * 32 + 16 + yr));
							}
							
							// if durability is lower than zero
							if(world.getCurrentTile().getObj().getDurability() <= 0) {
								byte count = (byte) (curr_tile.getObj().getAge() + 1);
								for(byte t = 0; t < count; t++) {
									byte xr = (byte) (rrr.nextInt(16) - 8);
									byte yr = (byte) (rrr.nextInt(16) - 8);
									world.addItemDrop(new ItemDrop(curr_tile.getObj().getDrop(), 1, curr_tile.getX() * 32 + 16 + xr, curr_tile.getY() * 32 + 16 + yr));	
									if(world.getCurrentTile().getObj().getExtraDrop() != null)
										world.addItemDrop(new ItemDrop(curr_tile.getObj().getExtraDrop(), 1, curr_tile.getX() * 32 + 16 + xr, curr_tile.getY() * 32 + 16 + yr));
								}
								byte c = world.getCurrentTile().getAge();
								
								if(world.getCurrentTile().getId() == Tiles.ID_FOREST) {
									world.addParticles(Particles.PARTICLE_LEAVES_HIT, 
											curr_tile.getX() * 32, curr_tile.getY() * 32 - 16,
											(byte) ((c+ 1) * 5), (byte) ((c + 1) * 5 - 2));
									world.addFallenObject(new FallingObject(curr_tile.getX(), curr_tile.getY(),
																			32, 64, curr_tile.getObj().getCurrentImg()));
								}
								
								world.removeObject(world.getCurrentTile());
							}
						} else world.addEvent(GameEvents.BAD_TOOL);
					}
					//----------------------------------------------------------------------------------------//
					
					this.strike = 0;
					this.progress_strike = (mainHand != null) ?
												mainHand.getStrikeProgress() : 5;
				} 
				// if you are in the water, you can't strike anyone entity
				else {
					world.addEvent(GameEvents.WATER_FIGHT);
					key.dropKey(KeyEvent.VK_SPACE);
					Sounds.BOW_SHOT.play();
				}
			}
			
			if(r && !active_inventory && !buildings.isActive() && !overheat.isActive()) {
				main_hand = !main_hand;
				key.dropKey(KeyEvent.VK_R);
			}
			
			updateStrike();
			regeneration();
			
			if(isPerhapsNewLevel()) {
				newLevel();
				world.addEvent(GameEvents.NEW_LEVEL);
			}
			
			if(this.active_inventory) this.inventory.tick();
			
			if(this.buildings.isActive()) this.buildings.tick();
			
			if(this.overheat.isActive()) this.overheat.tick();
		}
	}
	
	private void drawGUI(Graphics g, String title, Color color, int x, int y, short width, short height, int current, int max) {
//		g.setColor(Color.BLACK);
//		g.drawRect(x - 1, y - max - 1, 16 + 1, max + 1);
//		
//		g.setFont(new Font("Consolas", Font.PLAIN, 18));
//		g.drawString(title + ": " + current + "/" + max, x, y - 5 - max);
//		
//		if(current > 0) {
//			g.setColor(color);
//			g.fill3DRect(x, y - max + (max - current), 16, current, true);	
//		}
//		
//		g.setColor(new Color(50, 50, 50, 125));
//		g.fillRect(x, y - max, 16, (max - current));
		
		g.setColor(Color.BLACK);
		g.drawRect(x - 1, y - 1, width + 1, height + 1);
		
		g.setColor(new Color(50, 50, 50, 125));
		g.fillRect(x, y, ((max* width) / max) + 1, height);

		
		if(current > 0) {
			g.setColor(color);
			g.fill3DRect(x, y, current * width / max, height, true);	
		}
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Consolas", Font.PLAIN, 18));
		String text = title + ": " + current + "/" + max;
		short spc = (short) g.getFontMetrics().stringWidth(text);
		g.drawString(text, x + width / 2 - spc / 2, y + (height - 6));
	}
	
//	private void drawFillingBar(Graphics g, Color color, int x, int y, int max, int current) {
//		g.setColor(color);
//		g.fill3DRect(x - max + (max - current), y, current, 16, true);	
//	}
	
	public void renderLevel(Graphics g, short x, short y, short width, short height) {
		g.setColor(new Color(200, 200, 0));
		g.fill3DRect(x, y, (int) width, (int) height, true);
		
		g.setColor(new Color(0, 175, 0));
		g.setColor(Color.WHITE);
		String l = "Level " + level;
		short spc = (short) g.getFontMetrics().stringWidth(l);
		g.drawString(l, x + (width / 2) - (spc / 2), y + height - 6);
	}
	
	private boolean isPerhapsNewLevel() {
		return (GameScreen.score >= level_points_max);
	}
	
	private void newLevel() {
		/** switching new level **/
		this.level++;
		GameScreen.score -= level_points_max;
		
		/** New level is 25% higher than previous level **/
		this.level_points_max = (short) ((125 * level_points_max) / 100);
		
		/** Play sound **/
//		AudioPlayer.playSound(Sounds.LEVEL_UP, 1.0);
		Sounds.LEVEL_UP.play();
	}
	
	private void drawFillingBar(Graphics g, Color color, int x, int y, short width, short height, int max, int current) {
		g.setColor(color);
		g.fill3DRect(x, y, max * width / 100, height, true);	
	}
	
	private void updateStrike() {
		this.strike += getProgressStrike();
		if(this.strike > this.max_strike) this.strike =  this.max_strike;
	}

	@Override
	public Item getItemDrop() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean getMainHand() {
		return main_hand;
	}

}
