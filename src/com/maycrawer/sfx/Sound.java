package com.maycrawer.sfx;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	public Sound() {
		// TODO Auto-generated constructor stub
	}
	
//	private Clip clip;
//
//	public Sound(String fileName) {
//		try {
//			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
//
//	        clip = AudioSystem.getClip();
//	        clip.open(ais);
//	     } catch (Exception e) {
//	    	 e.printStackTrace();
//	     }
//	 }
//	
//	public void play() {
//        try {
//               if (clip != null) {
//                      new Thread() {
//                             public void run() {
//                                   synchronized (clip) {
//                                          clip.stop();
//                                          clip.setFramePosition(0);
//                                          clip.start();
//                                   }
//                             }
//                      }.start();
//               }
//        } catch (Exception e) {
//               e.printStackTrace();
//        }
//  }
	
	private AudioClip clip;

	public Sound(String subfolder, String fileName) {
		clip = Applet.newAudioClip(getClass().getResource("/snds/" + ((subfolder != null) ? subfolder + "/" : "")
																		+ fileName + ".wav"));
	}

	public void play() {
		clip.play();
	}

	static {
		
	}

}
