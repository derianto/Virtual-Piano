package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import music.MusicManager;
import music.MusicalNote;

/**
 * Encapsulates a single piano key which knows its pitch, color to draw,
 * location, etc.
 * @author Derianto Kusuma
 *
 */
public class PianoKey {

	public static final Color COLOR_KEY_FRAME = new Color(0.0f, 0.0f, 0.0f); // frame / edge of keys
	
	public static final Color COLOR_NORMAL_PLAIN = new Color(1.0f, 1.0f, 1.0f);
	public static final Color COLOR_DOWN_PLAIN = new Color(1.0f, 1.0f, 0.5f); // light yellow
	public static final Color COLOR_NORMAL_CHROMATIC = new Color(0.0f, 0.0f, 0.0f);
	public static final Color COLOR_DOWN_CHROMATIC = new Color(0.5f, 0.5f, 0.2f); // dark yellow
	
	// I decide to do this (even though a little dirty) because the Piano is the
	// context of a PianoKey.  A PianoKey cannot live without the Piano it
	// belongs to.
	private Piano piano;
	
	private int no;
	private int pitchOffset;
	private boolean isChromatic;
	private Rectangle bounds;
	private boolean isDown;

	// listener list
	private List<PianoKeyListener> listeners;

	/**
	 * Creates a PianoKey.  Must call setBounds after this.
	 * @param no the index of the key.  0 means the leftmost key displayed on
	 * the piano.  no is not a pitch, but no and pitch has a consistent
	 * difference.
	 */
	public PianoKey(Piano piano, int no) {
		this.piano = piano;
		this.no = no;
		this.pitchOffset = no;
		this.isChromatic = MusicalNote.isChromatic(pitchOffset);
		this.bounds = null;
		this.isDown = false; // initially, no key is down (pressed)
		
		this.listeners = new ArrayList<PianoKeyListener>();
	}
	
	/**
	 * Returns whether the drawing bounds of this piano key contains the point.
	 * @param point
	 * @return true if yes, false otherwise
	 */
	public boolean containPoint(Point point) {
		return bounds.contains(point);
	}

	/**
	 * Returns the fill color of the key (for drawing purpose).
	 * @return
	 */
	public Color getFillColor() {
		return (isChromatic) ?
				((isDown) ? PianoKey.COLOR_DOWN_CHROMATIC : PianoKey.COLOR_NORMAL_CHROMATIC)
				:
				((isDown) ? PianoKey.COLOR_DOWN_PLAIN : PianoKey.COLOR_NORMAL_PLAIN);
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	public boolean isChromatic() {
		return isChromatic;
	}
	
	public boolean isDown() {
		return isDown; 
	}
	
	public int getPitch() {
		return pitchOffset + piano.getBasePitch();
	}
	
	public int getNo() {
		return no;
	}
	
	/*########################################################################
	 *  S E T T E R S
	 *########################################################################*/

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	/**
	 * Sets whether this key is down.  Also plays the musical note as a
	 * side effect of this setter method.
	 * @param isDown true means down, false means up
	 */
	public void setDown(boolean isDown) {
		if (!this.isDown && isDown) {
			MusicManager.getInstance().playNote(getPitch());
		} else if (this.isDown && !isDown) {
			// TODO: What happens if the octave changes before note stopped?
			MusicManager.getInstance().stopNote(getPitch());
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
	public void addListener(PianoKeyListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Removes the specified listener.
	 * @param listener
	 */
	public void removeListener(PianoKeyListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Fires pianoKeyNeedsRedraw method in the registered listeners.
	 */
	private void fireNeedsRedraw() {
		for (PianoKeyListener listener: listeners)
			listener.pianoKeyNeedsRedraw(this);
	}
}
