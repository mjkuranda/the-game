package com.maycrawer.sfx;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class SoundJLayer extends PlaybackListener implements Runnable {
	private String filePath;
    private AdvancedPlayer player;
    private Thread playerThread; 
    
    private boolean end;

    public SoundJLayer(String filePath) {
        this.filePath = filePath;
    }

    public void play() {
        try {
        	String urlAsString = 
        			"file:///" 
                    + new java.io.File(".").getCanonicalPath() + "/" + "res/mscs/"
                    + this.filePath + ".mp3";

            this.player = new AdvancedPlayer(new java.net.URL(urlAsString).openStream(),
                    javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
            );
        	
            this.player.setPlayBackListener(this);

            this.playerThread = new Thread(this, "AudioPlayerThread");

            this.playerThread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // PlaybackListener members
    public void playbackStarted(PlaybackEvent playbackEvent) {
        System.out.println("playbackStarted");
    }

    public void playbackFinished(PlaybackEvent playbackEvent) {
        System.out.println("playbackEnded");
        end = true;
    }    
    
    public boolean isFinished() {
    	return end;
    }

    // Runnable members
    public void run() {
        try {
            this.player.play();
            
        } catch (javazoom.jl.decoder.JavaLayerException ex) {
            ex.printStackTrace();
        }

    }
    
    public void stop() {
    	this.player.stop();
    	this.playerThread.interrupt();
    }
}