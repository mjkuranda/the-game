package com.maycrawer.sfx;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundThread implements Runnable { 
	  
    // to stop the thread 
    private boolean exit; 
  
    private String name; 
    Thread t; 
    
    AudioClip sfx;
    double vol;
  
    public SoundThread(String threadname) 
    { 
        name = threadname; 
        t = new Thread(this, name); 
        System.out.println("New thread: " + t); 
        exit = false; 
        t.start(); // Starting the thread 
    } 
  
    // execution of thread starts from run() method 
//    public void run() 
//    { 
//        int i = 0; 
//        while (!exit) { 
//            System.out.println(name + ": " + i); 
//            i++; 
//            try { 
//                Thread.sleep(100); 
//            } 
//            catch (InterruptedException e) { 
//                System.out.println("Caught:" + e); 
//            } 
//        } 
//        System.out.println(name + " Stopped."); 
//    } 
    
    public void init(AudioClip sfx, double vol) {
    	this.sfx = sfx;
    	this.vol = vol;
    }
    
    public boolean tooLong() {
		if(System.currentTimeMillis() >= sfx.getTime() + 3000) {
			return true;
		}
		return false;
    }

    public void run() {
    	while (!exit) {
    		try {
    			AudioInputStream stream = sfx.getAudioStream();
    			Clip clip = AudioSystem.getClip();
    			
    			clip.open(stream);
    			AudioPlayer.setVol(vol, clip);
    			clip.start();
 
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
	}
    
    // for stopping the thread 
    public void stop() { 
        exit = true; 
    } 
} 