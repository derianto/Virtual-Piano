package main;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import music.MusicManager;
import resource.ImageResource;

/**
 * Encapsulates the model of the pedal, that knows its state and image to draw.
 * @author Derianto Kusuma
 *
 */
public class Pedal {

	private boolean isDown;

	// listener list
	private List<PedalListener> listeners;

	/**
	 * Creates a unpressed pedal (default position).  
	 */
	public Pedal() {
		isDown = false;

		this.listeners = new ArrayList<PedalListener>();
	}
	
	public boolean isDown() {
		return isDown;
	}
	
	/**
	 * Returns the Image to draw, depends on whether this pedal is down.
	 * @return
	 */
	public Image getImage() {
		return ImageResource.getImage( (isDown) ?
				ImageResource.PEDAL_DOWN : ImageResource.PEDAL_UP); 
	}
	
	/*########################################################################
	 *  S E T T E R S
	 *########################################################################*/
	
	/**
	 * Sets whether this pedal is down.  Also adjusts the pedal as a
	 * side effect of this setter method.
	 * @param isDown true means down, false means up
	 */
	public void setDown(boolean isDown) {			
		if (!this.isDown && isDown) {
			MusicManager.getInstance().pedalDown();
		} else if (this.isDown && !isDown) {
			MusicManager.getInstance().pedalUp();
		}

		this.isDown = isDown;
		fireNeedsRedraw(); // even though isDown doesn't change 
	}
	
	/*########################################################################
	 *  L I S T E N E R S
	 *########################################################################*/

	/**
	 * Registers the specified listener with this.
	 * @param listener
	 */
	public void addListener(PedalListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Removes the specified listener.
	 * @param listener
	 */
	public void removeListener(PedalListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Fires pedalNeedsRedraw method in the registered listeners.
	 */
	private void fireNeedsRedraw() {
		for (PedalListener listener: listeners)
			listener.pedalNeedsRedraw(this);
	}
}
