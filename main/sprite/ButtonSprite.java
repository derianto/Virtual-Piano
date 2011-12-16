package main.sprite;

import java.awt.Point;
import java.awt.Rectangle;

import resource.AnimationConstant;
import resource.ImageResource;

/**
 * Encapsulates a button Sprite that is clickable.
 * @author Derianto Kusuma
 *
 */
public class ButtonSprite extends StillSprite {

	/**
	 * Creates a ButtonSprite.
	 * @param imageResource
	 * @param x
	 * @param y
	 * @param startAlpha the starting alpha
	 * @param endAlpha the target alpha
	 * @param velAlpha the alpha increase rate per frame in fade in effect
	 */
	public ButtonSprite(ImageResource imageResource, int x, int y, float startAlpha, float endAlpha, float velAlpha) {
		super(imageResource, x, y, startAlpha, endAlpha, velAlpha);
		this.startAlpha = startAlpha;
		this.endAlpha = endAlpha;
		this.velAlpha = velAlpha;
	}

	/**
	 * Returns whether the point is contained in the boundary of the sprite.
	 * @param point
	 * @return true if yes, false otherwise
	 */
	public boolean contains(Point point) {
		return new Rectangle((int)x, (int)y, width, height).contains(point);
	}
	
	/**
	 * Returns whether the button is enabled.  Only if the button if enabled
	 * that it can be clicked.
	 * @return true | false
	 */
	public boolean isEnabled() {
		return (alpha >= AnimationConstant.buttonVisibilityAlpha);
	}

	/**
	 * Returns whether the point is contained in the boundary of the sprite and
	 * that the button is currently enabled.
	 * @param point
	 * @return true if yes, false otherwise
	 */
	public boolean isHovered(Point point) {
		return contains(point) && isEnabled();
	}
	
}
