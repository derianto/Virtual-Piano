package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import music.MusicManager;
import music.MusicalNote;
import resource.LayoutConstant;

/**
 * The piano panel contains clickable piano keys and pedal.
 * @author Derianto Kusuma
 */
public class Piano extends JPanel {

	private static final int NUM_OCTAVES = 3;
	private static final int NUM_KEYS = MusicalNote.OCTAVE_PITCH_DELTA * Piano.NUM_OCTAVES + 1;
	private static final int NUM_KEYS_PER_OCTAVE = 12;
	private static final int KEY_NOT_FOUND = -1;

	// Default base pitch (lowest playable C)
	private static final int DEFAULT_BASE_PITCH = 48;
	private static final int MIN_BASE_PITCH = 0;
	private static final int MAX_BASE_PITCH = 216;

	private List<PianoKey> pianoKeys;
	private Pedal pedal;
	
	// of the Piano JPanel
	private int width;
	private int height;
	private Point pedalPos;
	
	private int currentHovered = KEY_NOT_FOUND;
	
	private int basePitch = DEFAULT_BASE_PITCH;
	
	private Map<Integer, Integer> keyMap;
	
	/**
	 * Default constructor.
	 */
	public Piano() {
		createKeys();		
		createPedal();
		initKeyMap();
		
		// width already assigned in createKeys()
		height = LayoutConstant.keyFrameHeight + LayoutConstant.pedalPadding +
				 LayoutConstant.pedalHeight;
		setPreferredSize(new Dimension(width, height));
		setFocusable(true); // to be able to read key
		
		// listener interfaces
		addMouseMotionListener(new PianoMouseMotionListener());
		addMouseListener(new PianoMouseListener());
		addKeyListener(new PianoViewKeyListener());
	}

	/**
	 * A bootstrap method to initialize the key map.  The key map is a map
	 * from virtual key codes (VK_blablabla) to the key number.
	 */
	private void initKeyMap() {
		keyMap = new HashMap<Integer, Integer>();
		keyMap.put(KeyEvent.VK_Z, 12 * 0 + 0); // C4
		keyMap.put(KeyEvent.VK_S, 12 * 0 + 1);
		keyMap.put(KeyEvent.VK_X, 12 * 0 + 2);
		keyMap.put(KeyEvent.VK_D, 12 * 0 + 3);
		keyMap.put(KeyEvent.VK_C, 12 * 0 + 4);
		keyMap.put(KeyEvent.VK_V, 12 * 0 + 5);
		keyMap.put(KeyEvent.VK_G, 12 * 0 + 6);
		keyMap.put(KeyEvent.VK_B, 12 * 0 + 7);    
		keyMap.put(KeyEvent.VK_H, 12 * 0 + 8);
		keyMap.put(KeyEvent.VK_N, 12 * 0 + 9);
		keyMap.put(KeyEvent.VK_J, 12 * 0 + 10);
		keyMap.put(KeyEvent.VK_M, 12 * 0 + 11);
		keyMap.put(KeyEvent.VK_Q, 12 * 1 + 0); // C5
		keyMap.put(KeyEvent.VK_2, 12 * 1 + 1);
		keyMap.put(KeyEvent.VK_W, 12 * 1 + 2);
		keyMap.put(KeyEvent.VK_3, 12 * 1 + 3);
		keyMap.put(KeyEvent.VK_E, 12 * 1 + 4);
		keyMap.put(KeyEvent.VK_R, 12 * 1 + 5);
		keyMap.put(KeyEvent.VK_5, 12 * 1 + 6);
		keyMap.put(KeyEvent.VK_T, 12 * 1 + 7);
		keyMap.put(KeyEvent.VK_6, 12 * 1 + 8);
		keyMap.put(KeyEvent.VK_Y, 12 * 1 + 9);
		keyMap.put(KeyEvent.VK_7, 12 * 1 + 10);
		keyMap.put(KeyEvent.VK_U, 12 * 1 + 11);
		keyMap.put(KeyEvent.VK_I, 12 * 2 + 0); // C6
		keyMap.put(KeyEvent.VK_9, 12 * 2 + 1);
		keyMap.put(KeyEvent.VK_O, 12 * 2 + 2);
		keyMap.put(KeyEvent.VK_0, 12 * 2 + 3);
		keyMap.put(KeyEvent.VK_P, 12 * 2 + 4);
		keyMap.put(KeyEvent.VK_OPEN_BRACKET, 12 * 2 + 5);
		keyMap.put(KeyEvent.VK_EQUALS, 12 * 2 + 6);
		keyMap.put(KeyEvent.VK_CLOSE_BRACKET, 12 * 2 + 7);
		keyMap.put(KeyEvent.VK_BACK_SPACE, 12 * 2 + 8);
		keyMap.put(KeyEvent.VK_BACK_SLASH, 12 * 2 + 9);
	}
	
	/**
	 * Create the piano keys, initializes them, registers listeners.
	 * Also populates width.
	 */
	private void createKeys() {
		pianoKeys = new ArrayList<PianoKey>();
		
		// the left coordinate of the next plain key
		int pianoKeyCurLeft = LayoutConstant.pianoKeyLeft;
		
		// creates the PianoKey's and position them correctly
		for (int i = 0; i < Piano.NUM_KEYS; i++) {
			PianoKey pianoKey = new PianoKey(this, i);
			
			// position it
			if (pianoKey.isChromatic()) {
				pianoKey.setBounds(new Rectangle(
						pianoKeyCurLeft - LayoutConstant.chromaticKeyWidth / 2,
						LayoutConstant.pianoKeyTop,
						LayoutConstant.chromaticKeyWidth,
						LayoutConstant.chromaticKeyHeight));
			} else {
				pianoKey.setBounds(new Rectangle(
						pianoKeyCurLeft,
						LayoutConstant.pianoKeyTop,
						LayoutConstant.plainKeyWidth,
						LayoutConstant.plainKeyHeight));
				pianoKeyCurLeft += LayoutConstant.keyLeftOffset; // move next plain left
			}
			
			// register listener
			pianoKey.addListener(new PianoPianoKeyListener());
			
			pianoKeys.add(pianoKey);
		}
		
		width = pianoKeyCurLeft; // precisely the width of Piano JPanel!
	}

	/**
	 * Create and initializes the pedal and registers a listener.  Populates
	 * pedalPos.
	 */
	private void createPedal() {
		pedal = new Pedal();

		// register listener
		pedal.addListener(new PianoPedalListener());
		
		// we can assume that Piano width is already determined
		pedalPos = new Point((width - LayoutConstant.pedalWidth) / 2,
							  LayoutConstant.keyFrameHeight + LayoutConstant.pedalPadding);
	}
	
	/**
	 * Stops all currently playing notes (through set down).
	 */
	public void reset() {
		pedal.setDown(false);
		for (PianoKey pianoKey: pianoKeys) {
			pianoKey.setDown(false);
		}
	}
	
	/**
	 * Paints the whole Piano JPanel.
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintKeys(g);
		paintPedalArea(g);
	}

	@Override
	public void repaint() {
		//Utilities.trace("Piano repaint()");
		super.repaint();
	}
	
	/**
	 * Paints all the piano keys with its frames.
	 * @param g
	 */
	private void paintKeys(Graphics g) {		
		// paint key frames
		for (int i = 0; i < Piano.NUM_KEYS; i++) {
			g.setColor(PianoKey.COLOR_KEY_FRAME);
			g.drawRect(LayoutConstant.keyFrameLeft + i * LayoutConstant.keyLeftOffset,
					   LayoutConstant.keyFrameTop, LayoutConstant.keyFrameWidth - 1,
					   LayoutConstant.keyFrameHeight - 1);
		}
		
		// plain first
		for (PianoKey key: pianoKeys) {
			if (!key.isChromatic()) {
				g.setColor(key.getFillColor());
				Rectangle bounds = key.getBounds();
				g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
			}
		}
		
		// chromatic second
		for (PianoKey key: pianoKeys) {
			if (key.isChromatic()) {
				g.setColor(key.getFillColor());
				Rectangle bounds = key.getBounds();
				g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
			}
		}
		
	}
	
	/**
	 * Paints the pedal area (the area below piano keys).
	 * @param g
	 */
	private void paintPedalArea(Graphics g) {
		// background
		g.setColor(LayoutConstant.pianoBackgroundColor);
		g.fillRect(0, LayoutConstant.keyFrameHeight, width, LayoutConstant.pedalAreaHeight);
		
		// pedal
		g.drawImage(pedal.getImage(), pedalPos.x, pedalPos.y, null);
		
		// instrument number and octave number
		g.setFont(LayoutConstant.instrumentNumberFont);
		g.setColor(LayoutConstant.instrumentNumberColor);
		g.drawString(MusicManager.getInstance().getSynthInstrument() + ". " +
				MusicManager.getInstance().getInstrumentName(),
				LayoutConstant.instrumentNumberLeft,
				LayoutConstant.keyFrameHeight + LayoutConstant.instrumentNumberPadding);
		
		g.drawString("C" + getBasePitch() / 12,
				LayoutConstant.octaveNumberLeft,
				LayoutConstant.keyFrameHeight + LayoutConstant.instrumentNumberPadding);
	}
	
	/**
	 * Paints only the given PianoKey and its neighboring keys.
	 * Currently unused.
	 * @param g
	 * @param pianoKey
	 */
	private void paintKey(Graphics g, PianoKey pianoKey) {
		// if the specified key is chromatic, draw neighbors first
		// otherwise, draw the specified key first
		
		// nothing
	}

	/**
	 * Paints only the pedal area.
	 * Currently unused.
	 * @param g
	 */
	private void paintPedal(Graphics g) {
		// nothing
	}
	
	/**
	 * Returns the key number at the supplied point.
	 * Returns KEY_NOT_FOUND if the point is not inside any key.
	 * @param point
	 * @return the key number or KEY_NOT_FOUND
	 */
	public int getKeyNoHovered(Point point) {
		// chromatic first
		for (PianoKey key: pianoKeys) {
			if (key.isChromatic())
				if (key.containPoint(point))
					return key.getNo();
		}

		// plain second
		for (PianoKey key: pianoKeys) {
			if (!key.isChromatic())
				if (key.containPoint(point))
					return key.getNo();
		}
		
		return Piano.KEY_NOT_FOUND;
		
	}
	
	/**
	 * Returns the actual width of the Piano.  We do not use the default
	 * getWidth in JComponent because before the component is drawn, getWidth
	 * will return 0.
	 * @return the width
	 */
	public int getPianoWidth() {
		return width;
	}
	
	/**
	 * Returns the base pitch number of the Piano.  Used by the PianoKeys.
	 * @return the base pitch
	 */
	public int getBasePitch() {
		return basePitch;
	}
	
	/*########################################################################
	 *  S E T T E R S
	 *########################################################################*/

	/**
	 * Increases the base pitch by one octave.
	 */
	public void incOctave() {
		if (basePitch < MAX_BASE_PITCH) {
			basePitch += NUM_KEYS_PER_OCTAVE;
		}
	}
	
	/**
	 * Decreases the base pitch by one octave.
	 */
	public void decOctave() {
		if (basePitch > MIN_BASE_PITCH) {
			basePitch -= NUM_KEYS_PER_OCTAVE;
		}
	}
	
	/**
	 * Sets the current key number hovered to a new number.
	 * @param no the new key number
	 */
	private void setCurrentHovered(int no) {
		if (currentHovered != no && currentHovered != Piano.KEY_NOT_FOUND) {
			// force release the previous hovered key
			pianoKeys.get(currentHovered).setDown(false);
		}
		
		currentHovered = no;
	}
	
	/*########################################################################
	 *  L I S T E N E R   I N T E R F A C E S
	 *########################################################################*/
	
	/**
	 * Handles mouse motion.  Detects whenever the mouse pointer goes out of
	 * a piano key.
	 */
	private class PianoMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			setCurrentHovered(getKeyNoHovered(e.getPoint()));
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			mouseMoved(e);
		}
	}
	
	/**
	 * Handles mouse press and release on piano keys.
	 */
	private class PianoMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			/*int hovered = getKeyNoHovered(e.getPoint());
			if (hovered != Piano.KEY_NOT_FOUND)
				pianoKeys.get(hovered).setDown(true);*/
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			/*int hovered = getKeyNoHovered(e.getPoint());
			if (hovered != Piano.KEY_NOT_FOUND)
				pianoKeys.get(hovered).setDown(false);*/
		}
	}
	
	/**
	 * Handles pedal up / down with keyboard. 
	 */
	private class PianoViewKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			if (keyCode == LayoutConstant.pedalKey) {
				pedal.setDown(true);
			} else if (keyCode == KeyEvent.VK_LEFT) { // instrument --
				MusicManager.getInstance().decSynthInstrument();
				repaint();
			} else if (keyCode == KeyEvent.VK_RIGHT) { // instrument ++
				MusicManager.getInstance().incSynthInstrument();
				repaint();
			} else if (keyCode == KeyEvent.VK_PAGE_UP) { // octave ++
				reset();
				incOctave();
				repaint();
			} else if (keyCode == KeyEvent.VK_PAGE_DOWN) { // octave --
				reset();
				decOctave();
				repaint();
			} else if (keyCode == KeyEvent.VK_ENTER) { // reset
				reset();
				repaint();
			} else {
				if (keyMap.containsKey(keyCode))
					pianoKeys.get(keyMap.get(keyCode)).setDown(true);
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();

			if (keyCode == LayoutConstant.pedalKey) {
				pedal.setDown(false);
			} else {
				if (keyMap.containsKey(keyCode))
					pianoKeys.get(keyMap.get(keyCode)).setDown(false);
			}
		}
	}
	
	/**
	 * Responds to piano key's notification to redraw. 
	 */
	private class PianoPianoKeyListener implements PianoKeyListener {
		public void pianoKeyNeedsRedraw(PianoKey pianoKey) {
			repaint();
		}
	}
	
	/**
	 * Responds to pedal's notification to redraw. 
	 */
	private class PianoPedalListener implements PedalListener {
		public void pedalNeedsRedraw(Pedal pedal) {
			repaint();
		}
	}
	
}
