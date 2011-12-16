package main;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import music.MusicManager;

import resource.ImageResource;
import resource.MusicResource;

/**
 * Cointains the starting point of the program.
 * @author Derianto Kusuma
 */
public class VirtualPiano {

	/**
	 * The starting point of the program.  Accepts no parameters.
	 * Sets up things and display the main dialog.
	 * @param args
	 */
	public static void main(String[] args) {
		// use current platform's look and feel 
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {}

		// init resources and systems
		ImageResource.initFactory();
		MusicResource.initFactory();
		MusicManager.init();
		
		// intro message box
		/*JOptionPane.showMessageDialog(null, "Tips:\nSpacebar = pedal",
				"Ready?", JOptionPane.INFORMATION_MESSAGE);*/
		
		// create and show the main dialog
		MainFrame mainFrame = new MainFrame();
		
		mainFrame.setVisible(true);
		
		// TODO
	}

}
