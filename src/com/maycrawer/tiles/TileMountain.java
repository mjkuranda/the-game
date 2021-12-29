package com.maycrawer.tiles;

import java.awt.Graphics;
import java.util.Random;

import com.maycrawer.gfx.Animations;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.CameraManager;

public class TileMountain extends Tile {

	public TileMountain() {
		super("Mountain", Animations.ANIMATION_MOUNTAIN, 0.75f, Tiles.ID_MOUNTAIN);
	}
	
	public TileMountain(int x, int y) {
		super(Tiles.MOUNTAIN_TILE, x, y);
		setBackground(Assets.grass_textures[9]);
		Random r = new Random();
		byte count = (byte) (r.nextInt(3));
		switch(r.nextInt(4)) {
		case 0:
			setObject(new Object(Objects.STONE, (int) x, (int) y, count));
			switch(r.nextInt(8)) {
			case 0:
				setObject(new Object(Objects.GOLD, (int) x, (int) y, count));
				break;
			case 1:
				setObject(new Object(Objects.SILVER, (int) x, (int) y, count));
				break;
			case 2:
				setObject(new Object(Objects.SILVER, (int) x, (int) y, count));
				break;
			case 3:
				setObject(new Object(Objects.BRONZE, (int) x, (int) y, count));
				break;
			case 4:
				setObject(new Object(Objects.BRONZE, (int) x, (int) y, count));
				break;
			case 5:
				setObject(new Object(Objects.IRON, (int) x, (int) y, count));
				break;
			case 6:
				setObject(new Object(Objects.IRON, (int) x, (int) y, count));
				break;
			case 7:
				setObject(new Object(Objects.IRON, (int) x, (int) y, count));
				break;
			}
			break;
		case 1:
			setObject(new Object(Objects.STONE, (int) x, (int) y, count));
			break;
		case 2:
			setObject(new Object(Objects.STONE, (int) x, (int) y, count));
			break;
		case 3:
			setObject(new Object(Objects.STONE, (int) x, (int) y, count));
			break;
		case 4:
			setObject(new Object(Objects.STONE, (int) x, (int) y, count));
			break;
		}
	}
	
	public TileMountain(Object obj, int x, int y) {
		super(Tiles.MOUNTAIN_TILE, x, y);
		setBackground(Assets.grass_textures[9]);
		setObject(obj);
	}

	@Override
	public void render(Graphics g, CameraManager camera) {
		g.drawImage(getBackground(), getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset(), 32, 32, null);
		//g.drawImage(Assets.stone_textures[2], getX() * 32 - camera.getxOffset(), getY() * 32 - camera.getyOffset(), 32, 32, null);
		this.getObj().render(g, camera);
	}
	
	@Override
	public void renderObject(Graphics g, CameraManager camera) {
		//this.getObj().render(g, camera);
	}

	@Override
	public void tick() {
		this.getObj().tick();
	}

	@Override
	public byte getAge() {
		return obj.getAge();
	}

}
