package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import resource.ImageResource;

/**
 * Encapsulates the background image.  Contains the model: the image, the alpha,
 * etc.  This is a model class.  The view class is Canvas.
 * This class contains methods pertaining to the specific animation to run.
 * 
 * The Image is scaled according to the specified width and height.
 * 
 * @author Derianto Kusuma
 *
 */
public class BackgroundImage {

	private ImageResource imageRes;
	private Image scaledImage;
	private Rectangle canvasBounds;
	
	/**
	 * Default constructor.  Initializes the background image.
	 * The Image will be scaled according to the specified width and height
	 * in the canvasBounds.
	 * @param canvasBounds
	 * @param height
	 */
	public BackgroundImage(Rectangle canvasBounds) {
		this.canvasBounds = canvasBounds;
		init();
	}

	/**
	 * Initializes the image resource.
	 */
	public void init() {
		imageRes = ImageResource.getInstance(ImageResource.BACKGROUND);
		
		if (imageRes == null) {
			scaledImage = null;
		} else {
			scaledImage = imageRes.getImage().
				getScaledInstance(canvasBounds.width, canvasBounds.height, Image.SCALE_DEFAULT);
		}			
	}

	/**
	 * Replays.
	 */
	public void replay() {
		// do nothing..
	}

	/**
	 * Returns the Image to draw (with the specified width and height).
	 * @return null if the Image doesn't exist.	 
	 */
	public Image getImage() {
		return scaledImage;
	}
	
	/**
	 * Draws itself.
	 * @param g
	 * @param observer
	 */
	public void draw(Graphics g, ImageObserver observer) {
		g.drawImage(getImage(), canvasBounds.x, canvasBounds.y, observer);
	}
}
