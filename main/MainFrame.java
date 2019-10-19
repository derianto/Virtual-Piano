package main;

import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.util.CustomGridBagConstraints;
import resource.LayoutConstant;


/**
 * Encapsulates the main dialog of the program.  Contains all its components.
 * Only created once.
 * @author Derianto Kusuma
 *
 */
public class MainFrame extends JFrame {
	
	private Canvas canvas;
	public static Piano piano;
	
	/**
	 * Returns the content panel.  The content panel contains the canvas and
	 * piano keys.
	 * @return
	 */
	private JPanel createContentPanel() {
		piano = new Piano();
		//canvas = new Canvas(piano.getPianoWidth());
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		//contentPanel.add(canvas, new CustomGridBagConstraints(0, 0));
		contentPanel.add(piano, new CustomGridBagConstraints(0, 1));
		
		return contentPanel;
	}
	
	/**
	 * Constructs a default EditFrame.
	 */
	public MainFrame() {
		super("Virtual Piano");
		
		add(createContentPanel());
		
		// housekeeping
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // will be overriden
		setResizable(false);
		pack();
		setVisible(false); // hide it first

		addWindowListener(new MainFrameWindowListener());
	}
	
	/*########################################################################
	 *   WindowListener 
	 *########################################################################*/

	private class MainFrameWindowListener extends WindowAdapter {
		/**
		 * Called on close operation.  Opens a dialog before actually closing
		 * the window.
		 */
		@Override		
		public void windowClosing(WindowEvent e) {
			
			// note that this dialog box is annoying!
			/*JOptionPane.showOptionDialog(null, LayoutConstant.byeTitle,
					LayoutConstant.byeMessage, JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, LayoutConstant.byeOptions, 
					LayoutConstant.byeOptions[0]);*/
			
			System.exit(0);
		}
	}
	
	
}
