package com.maycrawer.utils;

public class GameEvents {

	public static final GameEvent LACK_OF_SPACE = new GameEvent("Lack of space!", 4000);
	public static final GameEvent BAD_TOOL = new GameEvent("You used bad tool!", 5000);
	public static final GameEvent LOW_FUEL = new GameEvent("Fuel is too low in furnace!", 5000);
	public static final GameEvent CONSTRUCTION_FINISHED = new GameEvent("Construction finished!", 5000);
	public static final GameEvent NO_ARROW = new GameEvent("You don't have any arrows!", 5000);
	public static final GameEvent BAD_PLACE = new GameEvent("You can only build on plains!", 5500);
	public static final GameEvent NO_RESOURCE = new GameEvent("You don't have resources!", 5500);
	public static final GameEvent NEEDED_HAMMER = new GameEvent("You use hammer to build!", 5500);
	public static final GameEvent NO_TOOL = new GameEvent("You aren't boxer, use sword!", 6000);
	public static final GameEvent MAX_BUILD = new GameEvent("You achieved max. level of building!", 10000);
	public static final GameEvent NEW_LEVEL = new GameEvent("You advanced to new character level!", 10000);
	public static final GameEvent WATER_FIGHT = new GameEvent("You can't fight with anyone in water!", 5000);
}
