package com.maycrawer.world;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.entities.Arrow;
import com.maycrawer.entities.Entity;
import com.maycrawer.entities.Goblin;
import com.maycrawer.entities.Particle;
import com.maycrawer.entities.Particles;
import com.maycrawer.entities.Slime;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.CameraManager;
import com.maycrawer.inventory.Item;
import com.maycrawer.inventory.ItemDrop;
import com.maycrawer.screens.GameScreen;
import com.maycrawer.sfx.Sounds;
import com.maycrawer.tiles.FallingObject;
import com.maycrawer.tiles.Tile;
import com.maycrawer.tiles.TileGrass;
import com.maycrawer.tiles.TileSea;
import com.maycrawer.tiles.Tiles;
import com.maycrawer.utils.GameEvent;
import com.maycrawer.utils.Generator;

public class World {
	
	private GraphicsMain gMain;
	private Generator gen;
	private Entity player;
	private CameraManager camera;

	private Tile[][] tiles;
	private int width, height;
	
	private boolean activity;
	
	private List<Entity> entities;
	private List<Particle> particles;
	private List<ItemDrop> items;
	private List<Arrow> arrows;
	
	private List<FallingObject> fallingObjects;
	
	private List<GameEvent> events;
	
	public World(GraphicsMain gMain, CameraManager camera, int sizeX, int sizeY) {
		this.gMain = gMain;
		this.camera = camera;
		
		this.width = sizeX;
		this.height = sizeY;
		
		this.activity = true;
		
		this.gen = new Generator(sizeX, sizeY);
		
		this.tiles = new Tile[sizeX][sizeY];
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				this.tiles[x][y] = new TileSea(x, y);
			}
		}
		this.gen.generate(tiles, 0, false, (byte) 0);
		
		this.events = new LinkedList<GameEvent>();
		
		this.entities = new LinkedList<Entity>();
//		this.entities.add(new Slime(this, camera, 105 * 32, 105 * 32, (byte) 62));
//		this.entities.add(new Slime(this, camera, 107 * 32, 105 * 32, (byte) 63));
		
//		this.entities.add(new Goblin(this, camera, 104 * 32, 105 * 32, (byte) 32));
//		this.entities.add(new Goblin(this, camera, 106 * 32, 105 * 32, (byte) 50));
		
		this.particles = new LinkedList<Particle>();
//		this.particles.add(new Particle(Animations.ANIMATION_DEATH_HAZE, camera, 0, 99 * 32, 99 * 32, (byte) 0));
//		this.particles.add(new Particle(Animations.ANIMATION_DEATH_HAZE, camera, 0, 99 * 32 + 16, 99 * 32, (byte) 0));
//		this.particles.add(new Particle(Animations.ANIMATION_DEATH_HAZE, camera, 0, 99 * 32 - 16, 99 * 32, (byte) 0));
//		this.particles.add(new Particle(Animations.ANIMATION_DEATH_HAZE, camera, 0, 99 * 32, 99 * 32 + 16, (byte) 0));
//		this.particles.add(new Particle(Animations.ANIMATION_DEATH_HAZE, camera, 0, 99 * 32, 99 * 32 - 16, (byte) 0));
//		
//		this.particles.add(new Particle(Animations.ANIMATION_WOOD_PRTS, camera, 0, 95 * 32, 99 * 32 + 16, (byte) 0));
//		this.particles.add(new Particle(Animations.ANIMATION_STONE_PRTS, camera, 0, 104 * 32, 99 * 32 - 16, (byte) 0));
		
		this.items = new LinkedList<ItemDrop>();
		
		this.arrows = new LinkedList<Arrow>();
		
		this.fallingObjects = new LinkedList<FallingObject>();
	}
	
	public void render(Graphics g) {
		int xs = (int) (player.getX() / 32) - (gMain.getMaxTilesX());
		int ys = (int) (player.getY() / 32) - (gMain.getMaxTilesY());
		int xe = xs + (2 * gMain.getMaxTilesX());
		int ye = ys + (2 * gMain.getMaxTilesY());
		
		for(int x = xs; x < xe; x++) {
			for(int y = ys; y < ye; y++) {
				if(isInWorldBorders(x, y)) tiles[x][y].render(g, camera);
			}
		}
		
		for(int e = 0; e < entities.size(); e++) {
			entities.get(e).render(g);
		}
		
	//	this.player.render(g);
		
		for(int f = 0; f < fallingObjects.size(); f++) {
			fallingObjects.get(f).render(g, camera);
		}
		
		for(int p = 0; p < particles.size(); p++) {
			particles.get(p).render(g);
		}
		
		for(int a = 0; a < arrows.size(); a++) {
			arrows.get(a).render(g, camera);
		}
		
		for(int x = xs; x < xe; x++) {
			for(int y = ys; y < ye; y++) {
				if(isInWorldBorders(x, y)) {
					tiles[x][y].renderObject(g, camera);
					if(this.getCurrentTile(player).getX() == x && this.getCurrentTile(player).getY() == y)
						player.render(g);
				}
			}
		}
		
		for(int i = 0; i < items.size(); i++) {
			items.get(i).render(g, camera);
		}
		
		for(int e = 0; e < entities.size(); e++) {
			if(this.getCurrentTile(entities.get(e)).getId() == Tiles.ID_FOREST) 
				entities.get(e).render(g);
		}
		
		for(int v = 0; v < events.size(); v++) {
			events.get(v).render(g, gMain, v);
		}
		
		this.player.renderGUI(g);
		
//		if(this.getCurrentTile(this.player).getId() == Tiles.ID_FOREST)
//			this.player.render(g);
		
//		for(int i = 0; i < items.size(); i++) {
//			items.get(i).render(g, camera);
//		}
//		
//		for(int e = 0; e < entities.size(); e++) {
//			entities.get(e).render(g);
//		}
//		
//		for(int p = 0; p < particles.size(); p++) {
//			particles.get(p).render(g);
//		}
//		
//		for(int v = 0; v < events.size(); v++) {
//			events.get(v).render(g, gMain, v);
//		}
		
//		this.player.render(g);
	}

	public void tick() {
		this.player.tick();

		if(this.activity) {
			int xs = (int) (player.getX() / 32) - (gMain.getMaxTilesX());
			int ys = (int) (player.getY() / 32) - (gMain.getMaxTilesY());
			int xe = xs + (2 * gMain.getMaxTilesX());
			int ye = ys + (2 * gMain.getMaxTilesY());
			
			for(int x = xs; x < xe; x++) {
				for(int y = ys; y < ye; y++) {
					if(isInWorldBorders(x, y)) tickTile(x, y);
				}
			}
			
			if(!player.isSwimming() && getCurrentTile().getId() == Tiles.ID_SEA) {
				Sounds.WATER_SPLASH.play();
			}
			this.player.setSwimming(getCurrentTile().getId() == Tiles.ID_SEA); // setting swimming
			this.camera.centerOnMob(player);
			
			Random r = new Random();
			
			if(r.nextInt(750) == 0 && entities.size() < 300) {
				int x = (r.nextInt(300) + 300) * (r.nextBoolean() ? 1 : -1);
				int y = (r.nextInt(300) + 300) * (r.nextBoolean() ? 1 : -1);
				int xslime = (int) (player.getX() + x);
				int yslime = (int) (player.getY() + y);
				byte count = (byte) (r.nextInt(3) + 1);
				for(byte c = 0; c < count; c++) {
					int extraX = r.nextInt(64) * (r.nextBoolean() ? 1 : -1);
					int extraY = r.nextInt(64) * (r.nextBoolean() ? 1 : -1);
					boolean bool = r.nextBoolean();
					entities.add(
							(bool) ? new Slime(this, camera, xslime + extraX, yslime + extraY, (byte) (r.nextInt(25) + 8))
								   : new Goblin(this, camera, xslime + extraX, yslime + extraY, (byte) (r.nextInt(25) + 8))
							);
						if(bool) Sounds.SLIME_HURT.play(); else Sounds.GOBLIN_HURT.play();
					addParticles(Particles.PARTICLE_DEATH_HAZE, (float) (xslime + extraX), (float) (yslime + extraY), (byte) 3, (byte) 1);
				}
			}
			
			for(int i = 0; i < items.size(); i++) {
				ItemDrop item_drop = items.get(i);
				if(item_drop.getCollider().intersects(player.getCollider()) && this.player.getInventory().emptySlots() > 0) {
					this.player.addItemDrop(item_drop, item_drop.getCount());
					this.items.remove(i);
				}
			}
			
			for(int e = 0; e < entities.size(); e++) {
				//int x = (int) entities.get(e).getX();
				//int y = (int) entities.get(e).getY();
				//if(isInWorldBorders(x, y))
				entities.get(e).tick();
				
				if(entities.get(e).getLife() < 1 || entities.get(e).tooOld()) {
					Entity en = entities.get(e);
					Item drop = en.getItemDrop();
					int count = (drop != null) ? r.nextInt(Math.abs(drop.getCount())) : 0;
					
					addParticles(Particles.PARTICLE_DEATH_HAZE,
									en.getX(), en.getY(), (byte) 10, (byte) 5);
					
					if(!en.tooOld()) {
						if(count > 0) {
							items.add(new ItemDrop(drop, count, en.getX(), en.getY()));
						}
						GameScreen.addScore(en.getAge());
						GameScreen.addArrow(r.nextInt(3) + 1);
						Sounds.EXP.play();
					}
					entities.remove(en);
					if(this.isOnScreen(en, 256, 256)) en.getDeath().play();
				}
			}
			
			for(int f = 0; f < fallingObjects.size(); f++) {
				if(fallingObjects.get(f).isFinished())
					fallingObjects.remove(f);
				else fallingObjects.get(f).tick();
			}
			
			for(int p = 0; p < particles.size(); p++) {
				particles.get(p).tick();
				if(particles.get(p).getRecurrence() > 0) {
					particles.remove(p);
					p--;
				}
			}
			
			for(int v = 0; v < events.size(); v++) {
				if(events.get(v).tooLong()) {
					events.remove(v);
				}
				if(events.size() > 8) events.remove(0);
			}	
			
			for(int a = 0; a < arrows.size(); a++) {
				arrows.get(a).tick();
				
				if(!arrows.get(a).isTooLong()) {
					boolean alive = true;
					int damage = -1 * arrows.get(a).getDamage();
					
					for(int e = 0; e < entities.size(); e++) {
						Entity ee = entities.get(e);
						if(alive && arrows.get(a).isCollided(ee)) {
							entities.get(e).addLife(damage);
							arrows.remove(a);
							alive = false;
						}
					}
					
					if(alive && arrows.get(a).isCollided(player)) {
						player.addLife(damage);
						arrows.remove(a);
					}	
				} else destroyArrow(a);
			}
		}
	}
	
	public void addArrow(Arrow arrow) {
		this.arrows.add(arrow);
		Sounds.BOW_SHOT.play();
	}
	
	private void destroyArrow(int id) {
		float x = arrows.get(id).getX();
		float y = arrows.get(id).getY();
		arrows.remove(id);
		this.addParticles(Particles.PARTICLE_DEATH_HAZE, x, y, (byte) 1, (byte) 1);
	}
	
	public void addFallenObject(FallingObject obj) {
		this.fallingObjects.add(obj);
	}
	
	public boolean isOnScreen(Entity e) {
		int px = (int) (e.getX() - camera.getxOffset());
		int py = (int) (e.getY() - camera.getyOffset());
		return ((px >= 0) && (px < gMain.getMaxTilesX() * 32) &&
				(py >= 0) && (py < gMain.getMaxTilesY() * 32));
	}
	
	public boolean isOnScreen(Entity e, int xc, int yc) {
		int px = (int) (e.getX() - camera.getxOffset());
		int py = (int) (e.getY() - camera.getyOffset());
//		System.out.println(px + ", " + py);
		return ((px - xc >= 0) && (px - xc < gMain.getMaxTilesX() * 32) &&
				(py - yc >= 0) && (py - yc < gMain.getMaxTilesY() * 32));
	}
	
	public List<Entity> getStrikedEntity() {
		List<Entity> strs= new LinkedList<Entity>();
		
		for(int e = 0; e < entities.size(); e++) {
			if(this.player.isStriking(entities.get(e))) {
				strs.add(entities.get(e));
			}
		}
		
		return strs;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public void addItemDrop(ItemDrop item) {
		this.items.add(item);
	}
	
	public List<Particle> getParticles() {
		return particles;
	}
	
	private boolean isInWorldBorders(int x, int y) {
		return (x >= 0 && y >= 0 && x < getTilesX() && y < getTilesY());
	}
	
	public Entity getPlayer() {
		return player;
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	private void tickTile(int x, int y) {
		tiles[x][y].getAnim().tick();
		tiles[x][y].tick();
	}
	
	public void addPlayer(Entity player) {
		this.player = player;
	}
	
	public Tile getCurrentTile() {
		return tiles[(int) ((player.getX() + 16) / 32)][(int) ((player.getY() + 16) / 32)];
	}
	
	public Tile getCurrentTile(Entity e) {
		int x = (int) ((e.getX() + 16) / 32);
		int y = (int) ((e.getY() + 16) / 32);
		if(isInWorldBorders(x, y)) return tiles[x][y];
		return new TileSea(x, y);
	}
	
	public void addParticles(Particle parent_part, float x, float y, byte max, byte min) {
		Random r = new Random();
		byte cn = (byte) (r.nextInt(max) + min);
		byte count = (byte) ((cn > max) ? max : cn);
		for(byte c = 0; c < count; c++) {
			Particle prt = parent_part;
			particles.add(new Particle(prt.getAnimation(), camera, 0, x + r.nextInt(32) - 16, y + r.nextInt(32) - 16, prt.getID()));
		}
	}
	
	public void addParticles(Particle parent_part, float x, float y) {
		Particle prt = parent_part;
		particles.add(new Particle(prt.getAnimation(), camera, 0, x - 16, y - 16, prt.getID()));
	}
	
	public void removeObject(Tile tile) {
		if(tile.getObj() != null) {
			switch(tile.getId()) {
			case Tiles.ID_GRASS:
				tiles[tile.getX()][tile.getY()] = new TileGrass(tile.getX(), tile.getY());
				break;
			case Tiles.ID_FOREST:
				tiles[tile.getX()][tile.getY()] = new TileGrass(tile.getX(), tile.getY());
				//AudioPlayer.playSound(Sounds.TREE_FELL, 1.0);
				Sounds.TREE_FELL.play();
				break;
			case Tiles.ID_MOUNTAIN:
				tiles[tile.getX()][tile.getY()] = new TileGrass(tile.getX(), tile.getY());
//				AudioPlayer.playSound(Sounds.STONE_BREAK, 1.0);
				Sounds.STONE_BREAK.play();
				break;
			}	
		}
		if(tile.getId() != Tiles.ID_SEA) updateTile(tile.getX(), tile.getY());
	}
	
	public void addTile(Tile tile) {
		this.tiles[tile.getX()][tile.getY()] = tile;
		updateTile(tile.getX(), tile.getY());
	}
	
	public void updateTile(int x, int y) {
		boolean w = (y > 0 && y < this.height) ? isTile(tiles, Tiles.ID_SEA, x, y - 1) : false;
		boolean a = (x > 0 && x < this.width) ? isTile(tiles, Tiles.ID_SEA, x - 1, y) : false;
		boolean s = (y > 0 && y < this.height - 1) ? isTile(tiles, Tiles.ID_SEA, x, y + 1) : false;
		boolean d = (x > 0 && x < this.width - 1) ? isTile(tiles, Tiles.ID_SEA, x + 1, y) : false;
	
		if(w && a) tiles[x][y].setBackground(Assets.grass_textures[3]);
		if(w && d) tiles[x][y].setBackground(Assets.grass_textures[1]);
		if(s && a) tiles[x][y].setBackground(Assets.grass_textures[5]);
		if(s && d) tiles[x][y].setBackground(Assets.grass_textures[7]);
		if(w && d && s) tiles[x][y].setBackground(Assets.grass_textures[2]);
		if(a && s && d) tiles[x][y].setBackground(Assets.grass_textures[4]);
		if(w && a && s) tiles[x][y].setBackground(Assets.grass_textures[6]);
		if(a && w && d) tiles[x][y].setBackground(Assets.grass_textures[8]);
		if(w && a && s && d) tiles[x][y].setBackground(Assets.grass_textures[10]);
	}
	
	private boolean isTile(Tile[][] tiles, byte id, int x, int y) {
		return tiles[x][y].getId() == id;
	}
	
	public int getWidth() {
		return width * 32;
	}
	
	public int getHeight() {
		return height * 32;
	}
	
	public int getTilesX() {
		return width;
	}
	
	public int getTilesY() {
		return height;
	}
	
	public void changeActivity() {
		this.activity = !activity;
	}
	
	public boolean isActive() {
		return activity;
	}
	
	public void addEvent(GameEvent event) {
		this.events.add(new GameEvent(event.getMessage(), System.currentTimeMillis(), event.getHowlong()));
		Sounds.BOW_SHOT.play();
	}
	
	public void reset() {
		this.particles = new LinkedList<>();
		this.entities = new LinkedList<>();
	}

}
