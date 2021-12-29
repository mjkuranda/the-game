package com.maycrawer.ffx;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import com.maycrawer.gfx.Assets;

public class FontLoader {
	
	public static final String RAMSEY = new String("res/fnts/ramsey.ttf");
	public static final String CELTIC = new String("res/fnts/celtic-bit.ttf");
	public static final String LIVINS = new String("res/fnts/livingstone.ttf");
	
	public static Font loadFont(String path, float size) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public static void init() {
//		Assets.font12 = loadFont(RAMSEY, 12);
//		Assets.font14 = loadFont(RAMSEY, 14);
//		Assets.font16 = loadFont(RAMSEY, 16);
//		Assets.font18 = loadFont(RAMSEY, 18);
//		Assets.font20 = loadFont(RAMSEY, 20);
//		Assets.font22 = loadFont(RAMSEY, 22);
//		Assets.font24 = loadFont(RAMSEY, 24);
//		Assets.font26 = loadFont(RAMSEY, 26);
//		Assets.font28 = loadFont(RAMSEY, 28);
//		Assets.font36 = loadFont(RAMSEY, 36);
	
//		Assets.font12 = loadFont(CELTIC, 12);
//		Assets.font14 = loadFont(CELTIC, 14);
//		Assets.font16 = loadFont(CELTIC, 16);
//		Assets.font18 = loadFont(CELTIC, 18);
//		Assets.font20 = loadFont(CELTIC, 20);
//		Assets.font22 = loadFont(CELTIC, 22);
//		Assets.font24 = loadFont(CELTIC, 24);
//		Assets.font26 = loadFont(CELTIC, 26);
//		Assets.font28 = loadFont(CELTIC, 28);
//		Assets.font36 = loadFont(CELTIC, 36);
		
		Assets.font12 = loadFont(LIVINS, 12);
		Assets.font14 = loadFont(LIVINS, 14);
		Assets.font16 = loadFont(LIVINS, 16);
		Assets.font18 = loadFont(LIVINS, 18);
		Assets.font20 = loadFont(LIVINS, 20);
		Assets.font22 = loadFont(LIVINS, 22);
		Assets.font24 = loadFont(LIVINS, 24);
		Assets.font26 = loadFont(LIVINS, 26);
		Assets.font28 = loadFont(LIVINS, 28);
		Assets.font36 = loadFont(LIVINS, 36);
		Assets.font48 = loadFont(LIVINS, 48);
		Assets.font96 = loadFont(LIVINS, 96);
	}

}
