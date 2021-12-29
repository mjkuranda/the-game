package com.maycrawer.sfx;

//import sun.audio.*;
import java.io.*;
//import javax.swing.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {
	
	private String name;
	private long start;

	public Audio() {
		// TODO Auto-generated constructor stub
		this.start = System.currentTimeMillis();
	}
	
	public void play(String name) {
		  try {
		   File file = new File("res/snds/" + name + ".wav");
		   AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		   AudioFormat format = audioInputStream.getFormat();
		   long frames = audioInputStream.getFrameLength();
		   double durationInSeconds = (frames+0.0) / format.getFrameRate();
		   System.out.println(durationInSeconds);
		   Clip clip = AudioSystem.getClip();
		   clip.open(AudioSystem.getAudioInputStream(file));
		   clip.start();
		   if(System.currentTimeMillis() >= start + durationInSeconds) {
			   clip.stop();
			   audioInputStream.close();
			   return;
		   }
//		   Thread.sleep(clip.getMicrosecondLength());
		  } catch (Exception e) {
		   System.err.println(e.getMessage());
		  }
	}
	
	public String getName() {
		return name;
	}
	
}
