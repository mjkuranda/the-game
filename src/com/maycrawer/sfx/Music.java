package com.maycrawer.sfx;

public class Music {

	private SoundJLayer soundToPlay;
	private long start, length;
	
	public Music(String subfolder, String filename, long length) {
		this.soundToPlay = new SoundJLayer(((subfolder != null) ? subfolder + "/" : "") +
														filename);
		soundToPlay.play();
		
		this.start = System.currentTimeMillis();
		this.length = length;
	}
	
	public SoundJLayer getSound() {
		return soundToPlay;
	}
	
	public boolean isFinished() {
		return (System.currentTimeMillis() >= start + length);
	}
	
	public void stop() {
		soundToPlay.stop();
	}
	
	public void reload() {
		
	}
	
	/** NATIVE CLASS PART **/
	
//	public Music(String subfolder, String fileName) {
//		Thread thread = new Thread() {
//			public void run() {
//				try {
//					FileInputStream fileInputStream = new FileInputStream(new File("res/mscs/" + 
//																	((subfolder != null) ? subfolder + "/" : "") +
//																		fileName + ".mp3"));
//					Player player = new Player(fileInputStream);
//					player.play();
//					//if(player.isComplete()) {
//					//	player.play(0);
//					//}
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				} catch(JavaLayerException e) {
//					e.printStackTrace();
//				}
//			}
//		}; thread.run();
//	}

}