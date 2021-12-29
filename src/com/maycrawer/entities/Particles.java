package com.maycrawer.entities;

import com.maycrawer.gfx.Animations;

public class Particles {

	public static final Particle PARTICLE_DEATH_HAZE = new Particle(Animations.ANIMATION_DEATH_HAZE, 0, (byte) 0);
	
	public static final Particle PARTICLE_WOOD_HIT = new Particle(Animations.ANIMATION_WOOD_PRTS, 0, (byte) 1);
	public static final Particle PARTICLE_STONE_HIT = new Particle(Animations.ANIMATION_STONE_PRTS, 0, (byte) 2);
	public static final Particle PARTICLE_LEAVES_HIT = new Particle(Animations.ANIMATION_LEAVES_PRTS, 0, (byte) 4);
	
	public static final Particle PARTICLE_WATER = new Particle(Animations.ANIMATION_WATER_PRTS, 0, (byte) 3);
}
