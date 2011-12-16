package resource;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

/**
 * Contains all the constants used in layouting, etc.
 * All constants must be obtained through the methods.  The default
 * implementation of the methods is just returning the value of private static
 * constants.  In the future, this may change.
 * @author Derianto Kusuma
 *
 */
public class LayoutConstant {
	
	// piano
	public static final Color pianoBackgroundColor = new Color(0.4f, 0.1f, 0.1f);
	
	public static final int plainKeyWidth = 26;
	public static final int plainKeyHeight = 100;
	public static final int chromaticKeyWidth = 15;
	public static final int chromaticKeyHeight = 60;
	public static final int keyLeftOffset = plainKeyWidth + 1;
	public static final int keyFrameWidth = plainKeyWidth + 2;
	public static final int keyFrameHeight = plainKeyHeight + 2;
	
	public static final int pianoKeyLeft = 1;
	public static final int pianoKeyTop = 1;
	public static final int keyFrameLeft = 0;
	public static final int keyFrameTop = 0;

	public static final int pedalWidth = 48;
	public static final int pedalHeight = 36;
	public static final int pedalPadding = 4;
	public static final int pedalAreaHeight = pedalPadding + pedalHeight;
	
	// instrument number
	public static final int instrumentNumberLeft = 10;
	public static final int instrumentNumberPadding = 25;
	public static final Color instrumentNumberColor = new Color(1.0f, 1.0f, 1.0f);
	public static final Font instrumentNumberFont = new Font("Dialog", Font.PLAIN, 14);
	
	// octave number
	public static final int octaveNumberLeft = 560;
	
	// canvas
	public static final int canvasMinHeight = 350;
	public static final int buttonRightBottomPadding = 10;
	
	// bye dialog
	public static final String byeTitle = "Bye";
	public static final String byeMessage = "Bye?";
	public static final String[] byeOptions = new String[]{"Bye!"};
	
	// keys
	public static final int pedalKey = KeyEvent.VK_SPACE;	
}
