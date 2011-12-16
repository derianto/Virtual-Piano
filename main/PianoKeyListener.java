package main;

/**
 * A listener interface to listen to events on a piano key.
 * @author Derianto Kusuma
 */
public interface PianoKeyListener {
	/**
	 * Called when the specified piano key needs redraw.
	 * @param pianoKey
	 */
	public void pianoKeyNeedsRedraw(PianoKey pianoKey);
}
