package resource;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import util.Utilities;

/**
 * Encapsulates an image.  Contains a factory getter method to get an image
 * resource.
 * 
 * Implements factory pattern.
 * 
 * @author Derianto Kusuma
 *
 */
public class ImageResource {
	// identifier for stored instances
	public static final int BACKGROUND = 0;
	public static final int TEXT = 1;
	public static final int BUTTON = 2;
	public static final int SNOWFLAKE = 3;
	public static final int PEDAL_UP = 4;
	public static final int PEDAL_DOWN = 5;

	// factory
	private static ImageResource[] imageResources;
	
	private String fileName;
	private Image image;
	private int width;
	private int height;
	
	/**
	 * Initializes the factory: creates ImageResource instances that can be
	 * identified with an int id.
	 */
	public static void initFactory() {
		imageResources = new ImageResource[] {
				new ImageResource("resources/images/background.jpg"),
				new ImageResource("resources/images/text.gif"),
				new ImageResource("resources/images/button.gif"),
				new ImageResource("resources/images/snowflake.gif"),
				new ImageResource("resources/images/pedal_up.gif"),
				new ImageResource("resources/images/pedal_down.gif")
		};
	}
	
	/**
	 * Returns the ImageResource corresponding to the specified id.
	 * If id is invalid, returns null.
	 * @param id
	 * @return the ImageResource
	 */
	public static ImageResource getInstance(int id) {
		// assumed no array index error
		return ImageResource.imageResources[id];		
	}
	
	/**
	 * Returns the image of the ImageResource corresponding to the specified id.
	 * If id is invalid, returns null.
	 * @param id
	 * @return the Image
	 */
	public static Image getImage(int id) {
		ImageResource ir = ImageResource.getInstance(id);
		
		if (ir == null) {
			return null;
		} else {
			return ir.getImage();
		}		
	}
	
	/**
	 * Constructs an ImageResource.  Loads the image readily and stores it
	 * for quick retrieval.
	 * @param fileName the file name of the image
	 */
	private ImageResource(String fileName) {
		this.fileName = fileName;

		// this, unlike createImage() or ImageIO.read(), will ensure that
		// the image is fully loaded first before returning
		// but I don't believe this since sometimes the image loading is slow
		this.image = new ImageIcon(Utilities.getResourceURL(fileName)).getImage();
		populateDimension();

		//this.image = Toolkit.getDefaultToolkit().createImage(fileName);
		
		// populates the image for quick retrieval later
		/*try {
			this.image = ImageIO.read(new File(fileName));
			populateDimension();
			
		} catch (IOException e) {
			ErrorHandler.display("File " + fileName + " is missing");
			// recovery: set it null
			image = null;
		}*/
	}
	
	/**
	 * Populates the width and height of the image.  Waits until the width
	 * and height are correctly populated.
	 */
	private void populateDimension() {

		// since use ImageIcon to load the Image, this is safe
		width = image.getWidth(null);
		height = image.getHeight(null);
		
		// alternative implementation.  uses ImageIcon
		//ImageIcon icon = new ImageIcon(image);
		//width = icon.getIconWidth();
		//height = icon.getIconHeight();
		
		// warning: there is a risk of infinite loop.  I just don't thoroughly
		// understand how this works
		/*while (true) {
			this.width = image.getWidth(new NullImageObserver());
			this.height = image.getHeight(new NullImageObserver());
			if (this.width > 0 && this.height > 0) return;
			try {
				wait(50); // very brief wait
			} catch (InterruptedException e) {
				// ignore.  cannot be interrupted in constructor
			}
		}*/
	}

	/**
	 * Returns the image.
	 * @return the image.  May be null.
	 */
	public Image getImage() {
		return image;
	}

	public String getFileName() {
		return fileName;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
