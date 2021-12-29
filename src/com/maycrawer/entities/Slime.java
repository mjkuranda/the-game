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

public class Slime extends Entity {

	private CameraManager camera;
	private World world;
	
	private long growTime;
	
	private byte count_anim, count_sound;
	
	public Slime(World world, CameraManager camera, int x, int y, byte age) {
		super("Slime", 0.25f, 50, 50, 200, 200, x, y, 32, 32, age, (byte) 64, Animations.ANIMATION_SLIME_STOPPED,
				Animations.ANIMATION_SLIME_WALKING, Animations.ANIMATION_SLIME_SWIMMING, Animations.ANIMATION_SLIME_WALKING,
				true, Entities.SLIME_ID, Sounds.SLIME_HURT, Sounds.SLIME_HURT);
		this.addCollider(new Rectangle((int) this.x, (int) this.y, 32, 32));
		this.addStriker(new Rectangle((int) this.x - 3, (int) this.y - 3, 38, 38));
		this.world = world;
		this.camera = camera;
		
		this.growTime = System.currentTimeMillis();
		
		this.ai = new SlimeAI(world);
	}
	
	public Slime() {
		super("Slime", 2, 50, 50, 200, 200, 0, 0, 32, 32, (byte) 32, (byte) 64, null,
				null, null, null,
				true, Entities.SLIME_ID, Sounds.SLIME_HURT, Sounds.SLIME_HURT);
	}

	@Override
	public void render(Graphics g) {
		int x = (int) this.x - camera.getxOffset();
		int y = (int) this.y - camera.getyOffset();
		
//		g.setColor(new Color(150, 0, 0, 100));
//		g.fillRect(x - 140, y - 140, this.ai.getRadius() * 2, this.ai.getRadius() * 2);
		
		if(isSwimming()) {
			g.drawImage(anim_swimming.getCurrentFrame(), x + (width / 2 - getSlimeAge() / 2), y + (height / 2 - getSlimeAge() / 2), getSlimeAge(), getSlimeAge(), null);
		} else if(isWalking() && !isSwimming()) {
			g.drawImage(anim_walking.getCurrentFrame(), x + (width / 2 - getSlimeAge() / 2), y + (height / 2 - getSlimeAge() / 2), getSlimeAge(), getSlimeAge(), null);
		} else {
			g.drawImage(anim_stopped.getCurrentFrame(), x + (width / 2 - getSlimeAge() / 2), y + (height / 2 - getSlimeAge() / 2), getSlimeAge(), getSlimeAge(), null);
		}
		
		if(getDamageTime() != 0) {
			g.drawImage(Assets.slime_damage_skin, x + (width / 2 - getSlimeAge() / 2), y + (height / 2 - getSlimeAge() / 2), getSlimeAge(), getSlimeAge(), null);
			this.damage_time++;
		}
	}
	
	@Override
	public void renderGUI(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		Animations.ANIMATION_SLIME_STOPPED.tick();
		Animations.ANIMATION_SLIME_WALKING.tick();
		Animations.ANIMATION_SLIME_SWIMMING.tick();
		
		if(System.currentTimeMillis() >= this.growTime + 3000) {
			age++;
			if(age >= old_age + 1) age = 65;
			this.growTime = System.currentTimeMillis();
		}
		
		if(world.isOnScreen(this, 256, 256)) {
			if(!isSwimming() && world.getCurrentTile(this).getId() == Tiles.ID_SEA) {
				Sounds.WATER_SPLASH.play();	
			}
		}
		setSwimming(this.world.getCurrentTile(this).getId() == Tiles.ID_SEA);
		
		if(isSwimming()) {
			count_anim++;
			if(count_anim == 4) {
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
		
		this.refreshCollider(0, 0, getSlimeAge(), getSlimeAge());
		this.refreshStriker(2, 2, getSlimeAge() - 4, getSlimeAge() - 4);
		
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
		
		regeneration();
		
	}
	
	private byte getSlimeAge() {
		return (byte) ((age >= 32) ? 32 : age);
	}

	@Override
	public Item getItemDrop() {
		Random r = new Random();
		if(age < 16) return null;
		else if(age < 32) return new Item(Items.slimeball, r.nextInt(3) + 1);
		else if(age < 48) return new Item(Items.slimeball, r.nextInt(5) + 2);
		else			  return new Item(Items.slimeball, r.nextInt(7) + 2);
	}

}
