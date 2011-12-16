package resource;

/**
 * A namespace containing animation constants for experience design.
 * @author Derianto Kusuma
 *
 */
public class AnimationConstant {

	// timing
	public static final int canvasRepaintDelay = 40;
	public static final int snowFlakeDelay = 40;
	public static final int textFadeInDelay = 30;
	public static final int buttonFadeInDelay = 30;

	public static final int snowFlakeInitDelay = 0;
	//public static final int textInitDelay = 2000; // for testing
	//public static final int buttonInitDelay = 2000; // for testing
	public static final int textInitDelay = 112000; // after snowflakes
	public static final int buttonInitDelay = 48000; // after text
	// note: timing perfect :)
	
	// snowflake
	public static final int NUM_SNOWFLAKES = 15;
	public static final float snowFlakeAlpha = 0.7f;
	public static final double snowFlakeMinVy = 1.2;
	public static final double snowFlakeMaxVy = 2.0;
	public static final double snowFlakeMinVx = -0.8;
	public static final double snowFlakeMaxVx = 0.8;
	public static final double snowFlakeVelTheta = 0.1;
	public static final double snowFlakeStartingY = -50;

	// alpha
	public static final float alphaTolerance = 0.01f;
	
	// text
	public static final float textStartAlpha = 0.0f;
	public static final float textEndAlpha = 0.7f;
	public static final float textVelAlpha = 0.02f;
	
	// button
	public static final float buttonStartAlpha = 0.0f;
	public static final float buttonEndAlpha = 1.0f;
	public static final float buttonVelAlpha = 0.02f;
	public static final float buttonVisibilityAlpha = 0.1f;
}
