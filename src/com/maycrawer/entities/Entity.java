package com.maycrawer.entities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.maycrawer.ais.AI;
import com.maycrawer.ais.MobAI;
import com.maycrawer.display.GraphicsMain;
import com.maycrawer.gfx.Animation;
import com.maycrawer.handlers.Buildings;
import com.maycrawer.handlers.Handler;
import com.maycrawer.inventory.Inventory;
import com.maycrawer.inventory.Item;
import com.maycrawer.inventory.ItemDrop;
import com.maycrawer.inventory.Items;
import com.maycrawer.sfx.Sound;
import com.maycrawer.tiles.Tile;
import com.maycrawer.world.World;

public abstract class Entity {

	protected String name;
	protected float x, y;
	protected int width, height;
	protected float speed;
	protected int max_life, life, max_strike, strike, progress_strike;
	protected byte id;
	
	protected boolean walking, swimming, following;
	protected boolean beaten, cured;
	protected int damage_time, cure_time;
	
	protected Rectangle col, str;

	protected boolean has_ai;
	private MobAI mob_ai;
	protected AI ai;
	protected Animation anim_stopped, anim_walking, anim_swimming, anim_following;

	protected byte old_age, age;
	protected boolean running;
	
	protected Inventory inventory;
	protected Buildings buildings;
	
	protected Sound hurt, death;
	
	protected Dimension last_pos;

	public Entity(String name, float speed, int life, int max_life, int strike, int max_strike, float x, float y, int width, int height, byte age, byte old_age, Animation stopped, Animation walking, Animation swimming, Animation following, boolean ai, byte id, Sound hurt, Sound death) {
		this.name = name;
		this.speed = speed;
		this.life = life;
		this.max_life = max_life;
		this.strike = strike;
		this.max_strike = max_strike;
		this.x = x;
		this.y = y;
		this.last_pos = new Dimension((int) x / 32, (int) y / 32);
		this.width = width;
		this.height = height;
		this.age = age;
		this.old_age = old_age;
		this.anim_stopped = stopped;
		this.anim_walking = walking;
		this.anim_swimming = swimming;
		this.anim_following = following;
		
		this.id = id;
		
		this.hurt = hurt;
		this.death = death;
		
		this.has_ai = ai;
	}
	
	public void resetInventory(GraphicsMain gMain, Handler handler, World world, float x, float y) {
		this.inventory = new Inventory(gMain, handler);
		this.inventory.addItem(Items.iron_sword, 1);
		this.inventory.addItem(Items.iron_axe, 1);
		this.inventory.addItem(Items.iron_pickaxe, 1);
		this.inventory.addItem(Items.iron_hammer, 1);
		this.inventory.addItem(Items.wooden_bow, 1);
		this.inventory.addItem(Items.wood, 55);
		this.inventory.addItem(Items.stone, 55);
		this.inventory.addItem(Items.iron_ingot, 1);
		this.x = x;
		this.y = y;
	}
	
	public Dimension getLastPos() {
		return last_pos;
	}
	
	public boolean tooOld() {
		return (age >= old_age);
	}
	
	public byte getOldAge() {
		return old_age;
	}
	
	public void beat() {
		this.beaten = true;
	}
	
	public void wasBeaten() {
		this.beaten = false;
	}
	
	public boolean isBeaten() {
		return this.beaten;
	}
	
	public void setDamageTime(int time) {
		this.damage_time = time;
	}
	
	public int getDamageTime() {
		return damage_time;
	}
	
	public Entity addCollider(Rectangle col) {
		this.col = col;
		return this;
	}
	
	public void refreshCollider(int xx, int yy, int width, int height) {
		this.col = new Rectangle((int) this.x + xx, (int) this.y + yy, width, height);
	}
	
	public boolean isCollision(Entity e) {
		return (this.col.intersects(e.getCollider()));
	}
	
	public Entity addStriker(Rectangle str) {
		this.str = str;
		return this;
	}
	
	public void refreshStriker(int xx, int yy, int width, int height) {
		this.str = new Rectangle((int) this.x + xx, (int) this.y + yy, width, height);
	}
	
	public boolean isStriking(Entity e) {
		return (this.str.intersects(e.getCollider()));
	}
	
	public Rectangle getStriker() {
		return str;
	}
	
	public abstract void render(Graphics g);
	
	public abstract void renderGUI(Graphics g);
	
	public abstract void tick();
	
	public void regeneration() {
		this.cure_time++;
		
		if(this.cure_time >= 100) {
			if(this.life < this.max_life) {
				this.addLife(1);
				this.cure_time = 0;
				this.cured = true;
			}
		}
	}
	
	public void addItemDrop(ItemDrop item_drop, int count) {
		this.inventory.addItem(item_drop.getItem(), count);
	}
	
	public void setProgressStrike(int progress) {
		this.progress_strike = progress;
	}
	
	public int getProgressStrike() {
		return progress_strike;
	}
	
	public float getSpeed(Tile curr) {
		float speed = this.speed * curr.getSpeed();
		if(isRunning()) return 2 * speed;
		return speed;
	}

	/**----------------------GETTERS-AND-SETTERS------------------------------**/
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public abstract Item getItemDrop();
	
	public Entity setMobAI(MobAI mob_ai) {
		this.mob_ai = mob_ai;
		return this;
	}
	
	public MobAI getMobAI() {
		return mob_ai;
	}
	
	public void setRunning(boolean run) {
		this.running = run;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void addX(float x) {
		this.x += x;
	}
	
	public void addY(float y) {
		this.y += y;
	}
	
	public void setLife(int life) {
		this.life = life;
	}
	
	public void addLife(int life) {
		this.life += life;
		
		if(life < 0) {
			setDamageTime(life);
			beat();
			hurt.play();
		}
	}
	
	public double getVolume(World world) {
		double dis = Math.abs(Math.atan2(world.getPlayer().getX() - getX(),
				world.getPlayer().getY() - getY()));
		double vol = (dis * 1.0) / 2.5 - 0.2;
		return vol;
	}
	
	public int getMaxLife() {
		return max_life;
	}
	
	public int getLife() {
		return life;
	}
	
	public void setAge(byte age) {
		this.age = age;
	}
	
	public byte getAge() {
		return age;
	}
	
	public int getStrike() {
		return strike;
	}

	public void setStrike(int strike) {
		this.strike = strike;
	}

	public int getMaxStrike() {
		return max_strike;
	}

	public void setMaxStrike(int max_strike) {
		this.max_strike = max_strike;
	}
	
	public boolean isCured() {
		return cured;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public boolean isWalking() {
		return walking;
	}
	
	public void setWalking(boolean walking) {
		this.walking = walking;
	}
	
	public boolean isFollowing() {
		return following;
	}
	
	public void setFollowing(boolean following) {
		this.following = following;
	}
	
	public Animation getStopped() {
		return anim_stopped;
	}

	public void setStopped(Animation stopped) {
		this.anim_stopped = stopped;
	}

	public Animation getWalking() {
		return anim_walking;
	}

	public void setWalking(Animation walking) {
		this.anim_walking = walking;
	}
	
	public boolean isSwimming() {
		return swimming;
	}

	public void setSwimming(boolean swimming) {
		this.swimming = swimming;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public boolean hasAI() {
		return has_ai;
	}
	
	public Rectangle getCollider() {
		return col;
	}
	
	public Sound getHurt() {
		return hurt;
	}

	public void setHurt(Sound hurt) {
		this.hurt = hurt;
	}

	public Sound getDeath() {
		return death;
	}

	public void setDeath(Sound death) {
		this.death = death;
	}
	
}
