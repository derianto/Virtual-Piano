package main;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.sprite.ButtonSprite;
import main.sprite.SnowFlakeSprite;
import main.sprite.StillSprite;
import main.thread.Scenario;
import main.thread.ScenarioThread;
import main.thread.TimedEvent;
import main.thread.TimerThread;
import resource.AnimationConstant;
import resource.ImageResource;
import resource.LayoutConstant;
import util.Utilities;

/**
 * Encapsulates various sprites that can animate.  Contains methods pertaining
 * to the specific animation design of this e-card.
 * @author Derianto Kusuma
 *
 */
public class Animation {

	private StillSprite text;
	private ButtonSprite button;	
	private List<SnowFlakeSprite> snowFlakes;
	private Rectangle canvasBounds;
	
	private Thread snowFlakeThread;
	private Thread textThread;
	private Thread buttonThread;
	private Thread launcherThread;
	
	/**
	 * Creates a new Animation.  Populates all the sprites.
	 * @param canvasBounds the bounds of the Canvas
	 */
	public Animation(Rectangle canvasBounds) {
		this.canvasBounds = canvasBounds;
		init();
	}
	
	/**
	 * Initializes all the sprites.
	 */
	public void init() {
		// text
		ImageResource ir = ImageResource.getInstance(ImageResource.TEXT);
		text = new StillSprite(ir,
				canvasBounds.x + (canvasBounds.width - ir.getWidth()) / 2,
				canvasBounds.y + (canvasBounds.height - ir.getHeight()) / 2,
				AnimationConstant.textStartAlpha,
				AnimationConstant.textEndAlpha,
				AnimationConstant.textVelAlpha);
				
		// button
		ir = ImageResource.getInstance(ImageResource.BUTTON);
		button = new ButtonSprite(ir,
				canvasBounds.x + canvasBounds.width - ir.getWidth() - LayoutConstant.buttonRightBottomPadding,
				canvasBounds.y + canvasBounds.height - ir.getHeight() - LayoutConstant.buttonRightBottomPadding,
				AnimationConstant.buttonStartAlpha,
				AnimationConstant.buttonEndAlpha,
				AnimationConstant.buttonVelAlpha);

		// snowFlakes
		snowFlakes = new ArrayList<SnowFlakeSprite>();
		for (int i = 0; i < AnimationConstant.NUM_SNOWFLAKES; i++) {
			SnowFlakeSprite snowFlake = new SnowFlakeSprite(canvasBounds);
			snowFlakes.add(snowFlake);
		}
		
		initThread();
	}
	
	/**
	 * Initializes all the animation threads (creates them).
	 */
	private void initThread() {
		// snowflake
		snowFlakeThread = new TimerThread(new TimedEvent(new Runnable() {
			public void run() {
				for (SnowFlakeSprite snowFlake: snowFlakes)
					snowFlake.move();
			}
		}, AnimationConstant.snowFlakeDelay));
		snowFlakeThread.setName("Snowflake Thread"); // for bookkeeping
		
		// text
		textThread = new TimerThread(new TimedEvent(new Runnable() {
			public void run() {
				text.fadeInStep();
			}
		}, AnimationConstant.textFadeInDelay));
		textThread.setName("Text Thread"); // for bookkeeping
		
		// button
		buttonThread = new TimerThread(new TimedEvent(new Runnable() {
			public void run() {
				button.fadeInStep();
			}
		}, AnimationConstant.buttonFadeInDelay));
		buttonThread.setName("Button Thread"); // for bookkeeping
		
		// launcher: launching snowflakes, text, button
		launcherThread = new ScenarioThread(new Scenario(Arrays.asList(
				new TimedEvent(new Runnable() {
					public void run() {
						snowFlakeThread.start();
					}
				}, AnimationConstant.snowFlakeInitDelay),
				new TimedEvent(new Runnable() {
					public void run() {
						textThread.start();
					}
				}, AnimationConstant.textInitDelay),
				new TimedEvent(new Runnable() {
					public void run() {
						buttonThread.start();
					}
				}, AnimationConstant.buttonInitDelay)
		)));
		launcherThread.setName("Animation Launcher Thread"); // for bookkeeping
	}
	
	/**
	 * Replays the animation.  Resets the sprites to their original states.
	 * The sprites are not recreated though, but only reset.  Terminates all
	 * running threads started by this instance.
	 */
	public void replay() {
		terminateThreads();
	
		text.replay();
		button.replay();
		for (SnowFlakeSprite snowFlake: snowFlakes)
			snowFlake.replay();
		
		launchThreads();
	}
	
	/**
	 * Launches all animation threads.  Before this method is call,
	 * all threads must not be running.
	 */
	private void launchThreads() {
		initThread(); // must create new Threads!
		launcherThread.start(); // will finally launch all other threads
	}
	
	/**
	 * Terminates all threads launched by this.
	 */
	private void terminateThreads() {
		launcherThread.interrupt(); // launcher first so it doesn't launch anything!
		
		snowFlakeThread.interrupt();
		textThread.interrupt();
		buttonThread.interrupt();
		
		try {
			launcherThread.join();

			snowFlakeThread.join();
			textThread.join();
			buttonThread.join();

		} catch (InterruptedException e) {
			// ignored!  this is Swing Thread running
		}
	}
	
	/**
	 * Draws itself in the given Graphics.
	 * @param g
	 * @param observer
	 */
	public void draw(Graphics g, ImageObserver observer) {
		//Utilities.trace("Animation draw() text alpha = " + text.getAlpha());
		
		// snowflake
		for (SnowFlakeSprite snowFlake: snowFlakes)
			snowFlake.draw(g, observer);
		
		// text
		text.draw(g, observer);
		
		// button
		button.draw(g, observer);
	}

	/**
	 * Returns whether the given point hovers above the replay button, only when
	 * the replay button is active / enabled.
	 * @param point
	 * @return true if yes, false otherwise
	 */
	public boolean isReplayHovered(Point point) {
		return button.isHovered(point);
	}

}
