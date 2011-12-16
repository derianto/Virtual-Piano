package main.sprite;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageObserver;
import java.awt.image.RGBImageFilter;

import main.util.NullImageObserver;
import resource.ImageResource;

/**
 * Encapsulates the model of a sprite.  Knows its location, image etc.
 * @author Derianto Kusuma
 *
 */
public class Sprite {

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected ImageResource imageResource;
	protected float alpha;

	protected AlphaFilter filter;
	
	/**
	 * Creates a Sprite with default x, y, alpha.
	 * @param imageResource
	 */
	public Sprite(ImageResource imageResource) {
		this(imageResource, 0, 0);
	}
	
	/**
	 * Creates a Sprite with default alpha.
	 * @param imageResource
	 * @param x
	 * @parma y
	 */
	public Sprite(ImageResource imageResource, int x, int y) {
		this(imageResource, x, y, 0.0f);
	}
	
	/**
	 * Creates a Sprite.
	 * @param imageResource
	 * @param x
	 * @parma y
	 * @param alpha initial alpha
	 */
	public Sprite(ImageResource imageResource, int x, int y, float alpha) {
		this.imageResource = imageResource;
		this.x = x;
		this.y = y;
		this.width = imageResource.getWidth();
		this.height = imageResource.getHeight();
		this.alpha = alpha;
		filter = new AlphaFilter(alpha);
	}
	
	/**
	 * Returns the Image to display.  Depends on alpha.
	 * @return
	 */
	public Image getImage() {
		filter.setAlpha(alpha);
		return Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(imageResource.getImage().getSource(), filter));
	}
	
	/**
	 * Draws itself.
	 * @param g
	 * @param observer
	 */
	public void draw(Graphics g, ImageObserver observer) {
		g.drawImage(getImage(), (int)x, (int)y, observer);
	}
	
	public float getAlpha() {
		return alpha;
	}
	
	/**
	 * A filter to add alpha data to each non-transparent pixel of the image.
	 * @author Derianto Kusuma
	 */
	protected class AlphaFilter extends RGBImageFilter {
		private float alpha;
		
		/**
		 * Creates a new AlphaFilter.
		 * @param alpha initial alpha
		 */
		public AlphaFilter(float alpha) {
			this.alpha = alpha;
		}
		
		public void setAlpha(float alpha) {
			this.alpha = alpha;
		}
		
		/**
		 * Adds alpha data.
		 */
		@Override
		public int filterRGB(int x, int y, int rgb) {
			if ((rgb & 0xFF000000) == 0) { // transparent pixel: keep it transparent
				return rgb;
			} else { // else add alpha data
				return (rgb & 0x00FFFFFF) + 0x01000000 * (int)(alpha * 0xff);
			}
		}
	}
}
