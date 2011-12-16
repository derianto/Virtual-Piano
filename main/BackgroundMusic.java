package main;

import music.MusicManager;
import resource.MusicResource;

/**
 * Encapsulates the background music of the canvas.  Takes care of playing it.
 * Usage: construct, then play.
 * @author Derianto Kusuma
 *
 */
public class BackgroundMusic {

	private MusicResource musicResource;
	
	/**
	 * Default constructor.
	 */
	public BackgroundMusic() {
		this.musicResource = MusicResource.getInstance(MusicResource.BACKGROUND);
	}

	/**
	 * Plays the music.  Stops the currently running music.
	 */
	public void play() {
		musicResource.play();
	}
	
}
