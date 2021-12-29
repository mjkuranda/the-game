package com.maycrawer.sfx;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioClip {

	private File file;
	private long time;
	
	public AudioClip(String path) {
//		file = new File(path);
//		if(!file.exists()) {
//			System.out.println("Error >> AudioClip Not Found!");
//		}
//		time = System.currentTimeMillis();
	
//		AudioInputStream audioInputStream = null;
//		try {
//			audioInputStream = AudioSystem.getAudioInputStream(
//					  new File("some_file.wav"));
//		} catch (UnsupportedAudioFileException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//				Clip clip = null;
//				try {
//					clip = AudioSystem.getClip();
//				} catch (LineUnavailableException e) {
//					e.printStackTrace();
//				}
//				try {
//					clip.open(audioInputStream);
//				} catch (LineUnavailableException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				FloatControl gainControl = 
//				  (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//				gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
//				clip.start();
	}
	
	public AudioInputStream getAudioStream() {
		try {
			return AudioSystem.getAudioInputStream(file);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long getTime() {
		return time;
	}

}
