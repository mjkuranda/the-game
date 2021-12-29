package com.maycrawer.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageLoader
{
	
	public static String GRAPHICS_TYPE_16_BIT = "16BIT";
	public static String GRAPHICS_TYPE_8_BIT = "8BIT";
	private String type;
	
	public ImageLoader(String type) {
		this.type = type;
	}
	
	public BufferedImage loadImage(String fileName) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(ImageLoader.class.getClass().getResourceAsStream("/imgs/" + fileName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public BufferedImage loadImage(String folderName, String fileName) {
		BufferedImage img = null;
		try {
			if(folderName != null)
				img = ImageIO.read(ImageLoader.class.getClass().getResourceAsStream("/imgs/" + folderName + "/" + fileName + ".png"));
			else img = ImageIO.read(ImageLoader.class.getClass().getResourceAsStream("/imgs/" + fileName + ".png")); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public BufferedImage loadImage(String folderName, String fileName, String typeFile) {
		BufferedImage img = null;
		try {
			if(folderName != null)
				img = ImageIO.read(ImageLoader.class.getClass().getResourceAsStream("/imgs/" + folderName + "/" + fileName + "." + typeFile));
			else img = ImageIO.read(ImageLoader.class.getClass().getResourceAsStream("/imgs/" + fileName + "." + typeFile)); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public ImageIcon loadImageIcon(String folderName, String fileName, String typeFile) {
		ImageIcon img = null;
		if(folderName != null)
			img = new ImageIcon(new ImageIcon(getClass().getResource("res/images/" + folderName + "/" + fileName + "." + typeFile)).getImage());
		else img = new ImageIcon(new ImageIcon(getClass().getResource("res/" + fileName + "." + typeFile)).getImage());; 
		return img;
	}
	
	public String getType() {
		return type;
	}
	
}