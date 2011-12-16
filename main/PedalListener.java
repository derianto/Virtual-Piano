package main;

/**
 * A listener interface to listen to events on the pedal.
 * @author Derianto Kusuma
 */
public interface PedalListener {
	/**
	 * Called when the pedal needs redraw.
	 * @param pedal
	 */
	public void pedalNeedsRedraw(Pedal pedal);
}
