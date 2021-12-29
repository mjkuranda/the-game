package com.maycrawer.display;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class Main {
	
	public static final String game_title = "The Game";
	public static final String game_author = "Marek Kuranda";
	public static final String game_version = "1.1.7 Alpha";
	
	private int WIDTH, HEIGHT;

	private Display display;
	private GraphicsMain gMain;
	
	private Display launcher;
	private JComboBox<String> resolutions;
	private Dimension resDim[] = {
		new Dimension(1280, 1024),
		new Dimension(1366, 768),
		new Dimension(1440, 900),
		new Dimension(1440, 1080),
		new Dimension(1600, 1200),
		new Dimension(1920, 1080)
	};
	private JButton bPlay;
	private JButton bQuit;
	private JCheckBox cFull;
	
	public Main() {
		settingLauncherDisplay();
	}

	public static void main(String[] args) {
		new Main();
	}
	
	private void settingLauncherDisplay() {
		launcher = new Display("Launcher", 640, 480, false, false, false);
		
		bPlay = new JButton("Play");
		bPlay.setBounds(270, 160, 100, 20);
		bPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				WIDTH = (int) resDim[resolutions.getSelectedIndex()].getWidth();
				HEIGHT = (int) resDim[resolutions.getSelectedIndex()].getHeight();
				launcher.setVisible(false);
				launcher.getJFrame().dispose();
				settingMainDisplay();
			}
		});
		launcher.addToFrame(bPlay);
		
		bQuit = new JButton("Quit");
		bQuit.setBounds(270, 240, 100, 20);
		bQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		launcher.addToFrame(bQuit);
		
		cFull = new JCheckBox("Full Screen");
		cFull.setText("Full Screen");
		cFull.setBounds(370, 200, 20, 20);
		//launcher.addToFrame(cFull);
		
		resolutions = new JComboBox<String>();
		resolutions.setBounds(270, 200, 100, 20);
		for(int r = 0; r < resDim.length; r++) {
			resolutions.addItem("" + (int) (resDim[r].getWidth()) + "x" + (int) (resDim[r].getHeight()));	
		}
		resolutions.setVisible(true);
		launcher.addToFrame(resolutions);
	}
	
	private void settingMainDisplay() {
		display = new Display(game_title + " v." + game_version + ", by " + game_author, WIDTH, HEIGHT, true, false);
		display.setVisible(true);
	}
	
	public Display getDisplay() {
		return display;
	}
	
	public GraphicsMain getGraphicsMain() {
		return gMain;
	}

}
