package resource;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import music.MusicManager;

import util.ErrorHandler;
import util.Utilities;

/**
 * Encapsulates a MIDI music.  Contains a factory getter method to get a
 * preinitialized music resource.
 * 
 * Implements factory pattern.
 * 
 * @author Derianto Kusuma
 *
 */
public class MusicResource {

	// identifier for stored instances
	public static final int BACKGROUND = 0;

	// factory
	private static MusicResource[] musicResources;
	
	private String fileName;
	private Sequence sequence;

	/**
	 * Initializes the factory: creates MusicResource instances that can be
	 * identified with an int id.
	 */
	public static void initFactory() {
		musicResources = new MusicResource[] {
				new MusicResource("resources/music/background.mid")
		};
	}
	
	/**
	 * Returns the MusicResource corresponding to the specified id.
	 * If id is invalid, returns null.
	 * @param id
	 * @return the MusicResource
	 */
	public static MusicResource getInstance(int id) {
		// assumed no array index error
		return MusicResource.musicResources[id];		
	}
	
	/**
	 * Returns the sequence of the MusicResource corresponding to the specified id.
	 * If id is invalid, returns null.
	 * @param id
	 * @return the Sequence
	 */
	public static Sequence getSequence(int id) {
		MusicResource mr = MusicResource.getInstance(id);
		
		if (mr == null) {
			return null;
		} else {
			return mr.getSequence();
		}		
	}
	
	/**
	 * Constructs an MusicResource.  Loads the sequence readily and stores it.
	 * @param fileName the file name of the MIDI music
	 */
	private MusicResource(String fileName) {
		this.fileName = fileName;

		// populates the sequence for quick retrieval / playing later
		try {
			this.sequence = MidiSystem.getSequence(Utilities.getResourceURL(fileName));
		
		} catch (IOException e) {
			ErrorHandler.display("File " + fileName + " is missing");
			// recovery
			sequence = null;
		
		} catch (InvalidMidiDataException e) {
			ErrorHandler.display("File " + fileName + " is corrupted");
			// recovery
			sequence = null;
		}
	}
	
	/**
	 * Returns the sequence.
	 * @return the sequence.  May be null.
	 */
	public Sequence getSequence() {
		return sequence;
	}
	
	/**
	 * Returns the file name.
	 * @return the file name.
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Plays this music.
	 */
	public void play() {
		MusicManager.getInstance().play(sequence);
	}
	
}
