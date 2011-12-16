package main.sprite;

import resource.ImageResource;
import util.Utilities;

/**
 * Encapsulates a Sprite that does not animate or move, but just displays.
 * @author Derianto Kusuma
 *
 */
public class StillSprite extends Sprite {

	protected float startAlpha;
	protected float endAlpha;
	protected float velAlpha;
	
	/**
	 * Creates a StillSprite.
	 * @param imageResource
	 * @param x
	 * @param y
	 * @param startAlpha the starting alpha
	 * @param endAlpha the target alpha
	 * @param velAlpha the alpha increase rate per frame in fade in effect
	 */
	public StillSprite(ImageResource imageResource, int x, int y, float startAlpha, float endAlpha, float velAlpha) {
		super(imageResource, x, y, startAlpha);
		this.startAlpha = startAlpha;
		this.endAlpha = endAlpha;
		this.velAlpha = velAlpha;
	}

	/**
	 * Replays.  Sets to initial condition.
	 */
	public void replay() {
		alpha = startAlpha;
	}

	/**
	 * Does one fade in step.  The alpha will not exceed the target.
	 */
	public void fadeInStep() {
		alpha += velAlpha;
		if (alpha >= endAlpha)
			alpha = endAlpha;
	}
}
