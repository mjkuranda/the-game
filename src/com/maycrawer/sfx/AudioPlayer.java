package com.maycrawer.sfx;

//import java.io.File;
//import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer {

	public static List<SoundThread> CLIPS = new LinkedList<SoundThread>();
	
	public static synchronized void playSound(AudioClip sfx, double vol) {
//		Thread thread = new Thread() {
//			public void run() {
//				try {
//					AudioInputStream stream = sfx.getAudioStream();
//					Clip clip = AudioSystem.getClip();
//				
////					clip.open(stream);
//					setVol(vol, clip);
//					clip.start();
//				} catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//			public void cancel() { interrupt(); }
//		}; thread.start();
		
//		SoundThread st = new SoundThread("Thread");
//		st.init(sfx, vol);
//		CLIPS.add(st);
		
	
	}
	
	//public static void playSound
	
	public static void setVol(double vol, Clip clip) {
		FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		float dB = (float) (Math.log(vol) / Math.log(10) * 20);
		gain.setValue(dB);
	}
	
	public static void update() {
		for(int c = 0; c < CLIPS.size(); c++) {
			if(CLIPS.get(c).tooLong()) {
					CLIPS.get(c).stop();
			}
			CLIPS.remove(c);
		}
	}
	
}
