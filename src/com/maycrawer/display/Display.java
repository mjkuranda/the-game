package com.maycrawer.display;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.Handler;

public class Display {

	private JFrame frame;
	private GraphicsMain gMain;
	private Handler handler;
	
	private Cursor mainCursor;
	
	public Display(String title, int width, int height, boolean isPanel, boolean fullScreen) {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(fullScreen);
		if(isPanel) {
			handler = new Handler();
			frame.addKeyListener(handler.getKeyManager());
			frame.addMouseListener(handler.getMouseManager());
			frame.addMouseWheelListener(handler.getMouseManager());
			frame.addMouseMotionListener(handler.getMouseManager());
			gMain = new GraphicsMain(frame, handler, width, height);
			mainCursor = Toolkit.getDefaultToolkit().createCustomCursor(
					Assets.mouse_texture, new Point(0, 0), "main cursor");
			gMain.setCursor(mainCursor);
			frame.setIconImage(Assets.iron_sword_texture);
			frame.add(gMain);
		} else frame.setLayout(null);
		frame.setVisible(true);
	}
	
	public Display(String title, int width, int height, boolean isPanel, boolean maximizeBoth, boolean fullScreen) {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(maximizeBoth) frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		if(fullScreen) frame.setUndecorated(fullScreen);
		if(isPanel) {
			handler = new Handler();
			frame.addKeyListener(handler.getKeyManager());
			frame.addMouseListener(handler.getMouseManager());
			frame.addMouseWheelListener(handler.getMouseManager());
			frame.addMouseMotionListener(handler.getMouseManager());
			gMain = new GraphicsMain(frame, handler, width, height);
//			mainCursor = Toolkit.getDefaultToolkit().createCustomCursor(
//					Assets.mouse_texture, new Point(0, 0), "main cursor");
//			gMain.setCursor(mainCursor);
			frame.add(gMain);
		} else frame.setLayout(null);
		frame.setVisible(true);
	}
	
	public Display(String title, int width, int height, boolean isPanel, boolean maximizeBoth, boolean fullScreen, boolean resizable) {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(maximizeBoth) frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		if(fullScreen) frame.setUndecorated(fullScreen);
		frame.setResizable(resizable);
		if(isPanel) {
			handler = new Handler();
			frame.addKeyListener(handler.getKeyManager());
			frame.addMouseListener(handler.getMouseManager());
			frame.addMouseWheelListener(handler.getMouseManager());
			frame.addMouseMotionListener(handler.getMouseManager());
			gMain = new GraphicsMain(frame, handler, width, height);
			mainCursor = Toolkit.getDefaultToolkit().createCustomCursor(
					Assets.mouse_texture, new Point(0, 0), "main cursor");
			gMain.setCursor(mainCursor);
			frame.add(gMain);
		} else frame.setLayout(null);
		frame.setVisible(true);
	}
	
	public void addToFrame(Component component) {
		frame.add(component);
		component.setVisible(true);
	}
	
	public void addToPanel(Component component) {
		gMain.add(component);
		component.setVisible(true);
	}
	
	public JFrame getJFrame() {
		return frame;
	}
	
	public void setVisible(boolean visibility) {
		frame.setVisible(visibility);
	}
	
}
