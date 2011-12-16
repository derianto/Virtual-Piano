package util;

import javax.swing.JOptionPane;

/**
 * Contains functions to handle errors.
 * @author Derianto Kusuma
 *
 */
public class ErrorHandler {
	
	/**
	 * Displays an error message.  This is the simplest way to notify the
	 * user of an error, regardless of where the error occurs.
	 * Not very robust.
	 * @param message the message to display
	 */
	public static void display(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}
