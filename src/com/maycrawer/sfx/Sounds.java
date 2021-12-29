package com.maycrawer.sfx;

public class Sounds {
	
	private static final String ENTITY = "entities";
	private static final String ENVIRONMENT = "environment";
	
	/** Volume: 0.0 - 2.0 **/

	public static final Sound WATER_SPLASH = new Sound(ENVIRONMENT, "splash");
	
	public static final Sound TREE_FELL = new Sound(ENVIRONMENT, "fell_tree");
	public static final Sound STONE_BREAK = new Sound(ENVIRONMENT, "break_stone");
	
	public static final Sound WOOD_HIT = new Sound(ENVIRONMENT, "bar_wood");
	public static final Sound STONE_HIT = new Sound(ENVIRONMENT, "bar_stone");
	
	public static final Sound BOW_SHOT = new Sound(null, "bow_shot");

	public static final Sound ENTITY_HURT = new Sound(ENTITY, "hurt");

	public static final Sound GOBLIN_HURT = new Sound(ENTITY, "goblin_hurt");
	public static final Sound GOBLIN_DEATH = new Sound(ENTITY, "goblin_death");
	
	public static final Sound SLIME_HURT = new Sound(ENTITY, "slime_hurt");
	public static final Sound SLIME_DEATH = new Sound(ENTITY, "slime_death");
	
	public static final Sound LEVEL_UP = new Sound(null, "level_up");
	public static final Sound EXP = new Sound(null, "exp");
	public static final Sound SWIM = new Sound(ENVIRONMENT, "swim");
	public static final Sound SWIM_2 = new Sound(ENVIRONMENT, "swim2");
	public static final Sound CLICK = new Sound(null, "Select");
	public static final Sound CLICK_W = new Sound(null, "SelectW");
	public static final Sound SWISH = new Sound(null, "swish");
	
//	public static final AudioClip WATER_SPLASH = new AudioClip("res/snds/splash.wav");
//	
//	public static final AudioClip TREE_FELL = new AudioClip("res/snds/fell_tree.wav");
//	public static final AudioClip STONE_BREAK = new AudioClip("res/snds/break_stone.wav");
//	
//	public static final AudioClip WOOD_HIT = new AudioClip("res/snds/bar_wood.wav");
//	public static final AudioClip STONE_HIT = new AudioClip("res/snds/bar_stone.wav");
//	
//	public static final AudioClip BOW_SHOT = new AudioClip("res/snds/bow_shot.wav");
//
//	public static final AudioClip ENTITY_HURT = new AudioClip("res/snds/hurt.wav");
//
//	public static final AudioClip GOBLIN_HURT = new AudioClip("res/snds/goblin_hurt.wav");
//	public static final AudioClip GOBLIN_DEATH = new AudioClip("res/snds/goblin_death.wav");
//	
//	public static final AudioClip SLIME_HURT = new AudioClip("res/snds/slime_hurt.wav");
//	public static final AudioClip SLIME_DEATH = new AudioClip("res/snds/slime_death.wav");
//	
//	public static final AudioClip LEVEL_UP = new AudioClip("res/snds/level_up.wav");
//	public static final AudioClip EXP = new AudioClip("res/snds/exp.wav");
//	public static final AudioClip SWIM = new AudioClip("res/snds/swim.wav");
//	public static final AudioClip SWIM_2 = new AudioClip("res/snds/swim2.wav");
//	
//	public static final Sound SPL = new Sound("splash");
//	public static final Sound SWM = new Sound("swim2");
}
