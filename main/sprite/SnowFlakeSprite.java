package main.sprite;

import java.awt.Rectangle;

import resource.AnimationConstant;
import resource.ImageResource;

/**
 * Encapsulates a snowflake Sprite that has special movement. 
 * @author Derianto Kusuma
 *
 */
public class SnowFlakeSprite extends Sprite {

	protected double vx;
	protected double vy;
	protected double theta; // to variate vx.  vx ~ sin theta
	
	protected Rectangle canvasBounds;
	
	/**
	 * Creates a SnowFlakeSprite.
	 * @param canvasBounds the canvas bounds, so that the snowflake can now
	 * the bounds in which it is a visible snowflake
	 */
	public SnowFlakeSprite(Rectangle canvasBounds) {
		super(ImageResource.getInstance(ImageResource.SNOWFLAKE), 0, 0,
				AnimationConstant.snowFlakeAlpha);
		this.canvasBounds = canvasBounds;

		replay();
	}

	/**
	 * Initializes with random position, speed, etc. just as if the e-card
	 * animation is replayed.
	 */
	public void replay() {
		restart();
		
		// not necessarily from the top
		y = canvasBounds.y + Math.random() * canvasBounds.height;
	}
	
	/**
	 * Makes a single snowflake move.
	 */
	public void move() {
		x += vx * (1.0 + Math.sin(theta));
		y += vy;
		theta += AnimationConstant.snowFlakeVelTheta;
		
		// does the snowflake need to restart from top?
		if (y >= canvasBounds.y + canvasBounds.height) {
			restart();
		}
	}
	
	/**
	 * Puts on the top of the screen as a new snowflake.
	 * Assigns new speed and position.
	 */
	public void restart() {
		x = canvasBounds.x + Math.random() * canvasBounds.width; 
		y = AnimationConstant.snowFlakeStartingY;
		vx = AnimationConstant.snowFlakeMinVx + Math.random() *
			(AnimationConstant.snowFlakeMaxVx - AnimationConstant.snowFlakeMinVx);
		vy = AnimationConstant.snowFlakeMinVy + Math.random() *
		(AnimationConstant.snowFlakeMaxVy - AnimationConstant.snowFlakeMinVy);
		theta = Math.random() * 2 * Math.PI;
	}
	
}
