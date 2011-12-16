package main.util;

import java.awt.Image;
import java.awt.image.ImageObserver;

/**
 * An empty ImageObserver that doesn't do anything.
 * So any program implementing this must be sure that all image data are
 * readily available.
 * @author Derianto Kusuma
 */
public class NullImageObserver implements ImageObserver {

	public NullImageObserver() {
	}
	
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		return false; // don't care!!  false or true?
	}

}
