package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import main.thread.TimedEvent;
import main.thread.TimerThread;
import main.util.NullImageObserver;
import resource.AnimationConstant;
import resource.LayoutConstant;
import util.Utilities;

/**
 * Encapsulates the canvas in the main frame, where animations and background
 * will be displayed and background music played.  Does not include the piano
 * keys.
 * 
 * Canvas directly handles background image and background music, but not
 * the animations.  All the visual animations are taken care by Animation class.
 * 
 * Canvas is repaint regularly at a constant interval by a specialized Thread.
 * 
 * @author Derianto Kusuma
 */
public class Canvas extends JPanel {
	
	private BackgroundMusic backgroundMusic;
	private BackgroundImage backgroundImage;
	private Animation animation;
	
	private int width;
	private int height;
	
	private Rectangle bounds;
	
	private Thread repaintThread;
	
	/**
	 * Default constructor.  Sets its width according to the width of Piano.
	 * @param width the width of the Canvas, which is supplied by the MainFrame.
	 */
	public Canvas(int width) {
		
		// layouting
		this.width = width;  
		this.height = LayoutConstant.canvasMinHeight;
		this.bounds = new Rectangle(0, 0, this.width, this.height);
		setPreferredSize(new Dimension(width, height));
		
		// components
		backgroundMusic = new BackgroundMusic();
		backgroundImage = new BackgroundImage(bounds);
		animation = new Animation(bounds);
		
		addMouseListener(new CanvasMouseListener());

		// launch thread
		initRepaintThread();
		
		replay();
	}

	/**
	 * Paints the background image and the animation.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		//Utilities.trace("Canvas paintComponent");
		
		super.paintComponent(g);

		// background image
		backgroundImage.draw(g, null);

		// all the animations
		animation.draw(g, null);
	}
	
	@Override
	public void repaint() {
		//Utilities.trace("Canvas repaint");
		super.repaint();
	}
	
	/**
	 * Replays the animation.  Resets everything.
	 */
	public void replay() {
		terminateRepaintThread();
		
		backgroundMusic.play();
		backgroundImage.replay();
		animation.replay();
		
		launchRepaintThread();
	}

	/**
	 * Initializes and creates the repaint Thread.
	 */
	private void initRepaintThread() {
		repaintThread = new TimerThread(new TimedEvent(new Runnable() {
			public void run() {
				repaint();
			}
		}, AnimationConstant.canvasRepaintDelay));
		repaintThread.setName("Repaint Thread"); // for bookkeeping
		
	}
	
	/**
	 * Launches the Thread to constantly repaint.  Only called once. 
	 */
	private void launchRepaintThread() {
		initRepaintThread(); // must create new Thread!
		repaintThread.start();
	}

	/**
	 * Terminates the repaint Thread.
	 *
	 */
	private void terminateRepaintThread() {
		repaintThread.interrupt();
		try {
			repaintThread.join();
		} catch (InterruptedException e) {
			// ignore.  this is Swing Thread
		}
	}

	/*########################################################################
	 *  L I S T E N E R   I N T E R F A C E S
	 *########################################################################*/
	
	/**
	 * Detects click, especially on button sprite (replay button).
	 */
	private class CanvasMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (animation.isReplayHovered(e.getPoint())) {
				replay();
			}
		}
	}

	/**
	 * Waits for the Thread to quit.
	 */
	@Override
	protected void finalize() throws Throwable {
		if (repaintThread != null) {
			repaintThread.interrupt();
			repaintThread.join();
		}
		
		super.finalize();
	}
	
}
