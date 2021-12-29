package com.maycrawer.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.maycrawer.ais.SlimeAI;
import com.maycrawer.gfx.Animations;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.CameraManager;
import com.maycrawer.inventory.Item;
import com.maycrawer.inventory.Items;
import com.maycrawer.sfx.Sounds;
import com.maycrawer.tiles.Tiles;
import com.maycrawer.world.World;

public class Goblin extends Entity {

	private CameraManager camera;
	private World world;
	
	private long growTime;
	
	private byte count_anim, count_sound;
	
	public Goblin(World world, CameraManager camera, int x, int y, byte age) {
		super("Goblin", 0.85f, 150, 150, 200, 200, x, y, 32, 32, age, (byte) 64, Animations.ANIMATION_GOBLIN_STOPPED,
				Animations.ANIMATION_GOBLIN_WALKING, Animations.ANIMATION_GOBLIN_SWIMMING, Animations.ANIMATION_GOBLIN_FOLLOWING,
				true, Entities.GOBLIN_ID, Sounds.GOBLIN_HURT, Sounds.GOBLIN_DEATH);
		this.addCollider(new Rectangle((int) this.x, (int) this.y, 32, 32));
		this.addStriker(new Rectangle((int) this.x - 3, (int) this.y - 3, 38, 38));
		this.world = world;
		this.camera = camera;
		
		this.growTime = System.currentTimeMillis();
		
		this.ai = new SlimeAI(world);
	}
	
	public Goblin() {
		super("Goblin", 2, 150, 150, 200, 200, 0, 0, 32, 32, (byte) 32, (byte) 64, null, null, null,
				null, true,	Entities.GOBLIN_ID, Sounds.GOBLIN_HURT, Sounds.GOBLIN_DEATH);
	}

	@Override
	public void render(Graphics g) {
		int x = (int) this.x - camera.getxOffset();
		int y = (int) this.y - camera.getyOffset();
		
//		g.setColor(new Color(150, 0, 0, 100));
//		g.fillRect(x - 140, y - 140, this.ai.getRadius() * 2, this.ai.getRadius() * 2);
		
		if(isSwimming()) {
			g.drawImage(anim_swimming.getCurrentFrame(), x + (width / 2 - getGoblinAge() / 2), y + (height / 2 - getGoblinAge() / 2), getGoblinAge(), getGoblinAge(), null);
		} else if(isWalking() && !isSwimming()) {
			g.drawImage(anim_walking.getCurrentFrame(), x + (width / 2 - getGoblinAge() / 2), y + (height / 2 - getGoblinAge() / 2), getGoblinAge(), getGoblinAge(), null);
		} else {
			g.drawImage(anim_stopped.getCurrentFrame(), x + (width / 2 - getGoblinAge() / 2), y + (height / 2 - getGoblinAge() / 2), getGoblinAge(), getGoblinAge(), null);
		}
		
		if(isFollowing() && !isSwimming()) {
			g.drawImage(anim_following.getCurrentFrame(), x + (width / 2 - getGoblinAge() / 2), y + (height / 2 - getGoblinAge() / 2), getGoblinAge(), getGoblinAge(), null);
		} else if(isFollowing() && isSwimming()) {
			g.drawImage(anim_following.getFrame(anim_swimming.getCurrentFrameID()), x + (width / 2 - getGoblinAge() / 2), y + (height / 2 - getGoblinAge() / 2), getGoblinAge(), getGoblinAge(), null);
		}
		
		if(getDamageTime() != 0) {
			g.drawImage(Assets.goblin_damage_skin, x + (width / 2 - getGoblinAge() / 2), y + (height / 2 - getGoblinAge() / 2), getGoblinAge(), getGoblinAge(), null);
			this.damage_time++;
		}
	}
	
	@Override
	public void renderGUI(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		Animations.ANIMATION_GOBLIN_STOPPED.tick();
		Animations.ANIMATION_GOBLIN_WALKING.tick();
		Animations.ANIMATION_GOBLIN_SWIMMING.tick();
		Animations.ANIMATION_GOBLIN_FOLLOWING.tick();
		
		if(System.currentTimeMillis() >= this.growTime + 3000) {
			age++;
			if(age >= old_age + 1) age = 65;
			this.growTime = System.currentTimeMillis();
		}
		
		if(isSwimming()) {
			count_anim++;
			if(count_anim == 8) {
				world.addParticles(Particles.PARTICLE_WATER,
						this.getX(),
						this.getY(), (byte) 1, (byte) 1);
				count_anim = 0;
				
				if(isWalking()) count_sound++;
				if(isWalking() && count_sound >= 8) {
					Random rn = new Random();
					boolean b = rn.nextBoolean();
					if(world.isOnScreen(this, 256, 256)) {
						if(b) Sounds.SWIM.play(); else Sounds.SWIM_2.play();
					}
					count_sound = 0;
				}
			}
		} else count_anim = 0;
		
		if(world.isOnScreen(this, 256, 256)) {
			if(!isSwimming() && world.getCurrentTile(this).getId() == Tiles.ID_SEA) {
				Sounds.WATER_SPLASH.play();	
			}
		}
		setSwimming(this.world.getCurrentTile(this).getId() == Tiles.ID_SEA);
		
		this.refreshCollider(0, 0, getGoblinAge(), getGoblinAge());
		this.refreshStriker(2, 2, getGoblinAge() - 4, getGoblinAge() - 4);
		
		if(isStriking(world.getPlayer())) {
			world.getPlayer().addLife(-5);
			if(world.getPlayer().getLife() < 0) world.getPlayer().setLife(0);
		}
		
		if(this.ai.isNearEntity(this, world.getPlayer()))
			this.ai.following(this, world.getPlayer());
		else {
			if(this.ai.getMode() == (byte) 2) this.ai.setMode((byte) 0);
			this.ai.walking(this);
		}
		
//		if(this.isFollowing()) {
//			world.addArrow(new Arrow(camera, (int) x, (int) y, (int) this.world.getPlayer().getX(),
//													(int) this.world.getPlayer().getY()));
//		}
		
		regeneration();
		
	}
	
	private byte getGoblinAge() {
		return (byte) ((age >= 32) ? 32 : age);
	}

	@Override
	public Item getItemDrop() {
		Random r = new Random();
			 if(age < 32) return new Item(Items.bronze_coin, r.nextInt(16) + 1);
		else if(age < 48) return new Item(Items.silver_coin, r.nextInt(8) + 1);
		else 			  return new Item(Items.gold_coin,   r.nextInt(4) + 1);
	}
	
}
