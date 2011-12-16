package music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

import util.ErrorHandler;
import util.Utilities;

/**
 * A class that handles MIDI music playing.  This class takes care of the
 * Sequencer, provides functions to play and stop music, and to play a single
 * note on a special channel.
 * 
 * This class will take control of the default MIDI Sequencer and Synthesizer
 * while the program is running.
 * 
 * Any MIDI sequence played must not occupy the last channel (highest numbered)
 * since that channel is used for synthesizing.
 * 
 * Implements singleton pattern.
 * 
 * @author Derianto Kusuma
 *
 */
public class MusicManager {

	private static final String instrumentFileName = "resources/data/instruments.txt";
	
	// single instance
	private static MusicManager musicManager;
	
	public static final int NUM_INSTRUMENT = 128;
	
	// default values for synthesizer
	public static final int SYNTH_CHANNEL_NO = 15;
	public static final int SYNTH_NOTE_VELOCITY = 120;
	public static final int SYNTH_INSTRUMENT = 0; // acoustic grand piano

	// pedal
	public static final int PEDAL_ID = 64;
	public static final int PEDAL_ON = 127;
	public static final int PEDAL_OFF = 0;
	
	private Sequencer sequencer;
	private Synthesizer synth;
	private MidiChannel synthChannel;
	private int synthInstrument;
	
	private static List<String> instrumentNames;
	
	/**
	 * Returns the default instance.
	 * @return the default instance
	 */
	public static MusicManager getInstance() {
		if (musicManager == null)
			musicManager = new MusicManager();
		
		return musicManager;
	}

	/**
	 * Initializes the default instance.  Must be called first when the
	 * program starts to open the sequencer.
	 */
	public static void init() {
		musicManager = new MusicManager();
		initInstrumentNames();
	}

	/**
	 * Populates the instrument names (a private static List).
	 */
	private static void initInstrumentNames() {
		instrumentNames = new ArrayList<String>();
		try {
			URL url = Utilities.getResourceURL(MusicManager.instrumentFileName);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			// read line by line.  empty line or null line indicates an end
			while (true) {
				String line = in.readLine();
				if (line == null || line.trim().isEmpty()) {
					break;
				} else {					
					instrumentNames.add(line);
				}
			}

			in.close();
			
		} catch (IOException e) {
			ErrorHandler.display("Cannot read MIDI instrument names");
		}

		if (instrumentNames.size() != MusicManager.NUM_INSTRUMENT) {
			ErrorHandler.display("Wrong MIDI instrument names data");
		}
		
		// fault tolerance: append empty string until size is at least 128
		while (instrumentNames.size() < MusicManager.NUM_INSTRUMENT)
			instrumentNames.add(""); // default: empty string

	}

	/**
	 * Returns the name of the instrument with specific id.
	 * @param id assumed in the correct range (0..127)
	 * @return
	 */
	public static String getInstrumentName(int id) {
		return MusicManager.instrumentNames.get(id);
	}
	
	/**
	 * Default constructor.  Initializes the sequencer.
	 */
	private MusicManager() {
		try {
			// init sequencer
			sequencer = MidiSystem.getSequencer();
			sequencer.open();

			// init synthesizer
			synth = MidiSystem.getSynthesizer();
			synth.open();
			
			// DEBUG
			//System.out.print("latency = " + synth.getLatency());
			//System.out.print("max polyphony = " + synth.getMaxPolyphony());

			// get channel for synthesizing: the highest numbered channel.  sets it up
			MidiChannel[] channels = synth.getChannels();
			synthChannel = channels[channels.length - 1];
			setSynthInstrument(MusicManager.SYNTH_INSTRUMENT);
			
		} catch (MidiUnavailableException e) {
			ErrorHandler.display("Cannot play MIDI music");
			sequencer = null; // remember this!  sequencer can be null.
			return;
		}
		
	}
	
	/**
	 * Releases the sequencer.
	 */
	@Override
	protected void finalize() throws Throwable {
		if (synth != null)
			synth.close();
		if (sequencer != null)
			sequencer.close();
		super.finalize();
	}
	
	/**
	 * Plays the supplied Sequence.  Only one MIDI music can play at the same
	 * time.  If a music is currently playing, the previous music will be
	 * stopped first.
	 * @param sequence
	 */
	public void play(Sequence sequence) {
		if (sequencer == null) return;
		
		sequencer.stop();
		sequencer.close();
		try {
			sequencer.open();
		} catch (MidiUnavailableException e) {
			ErrorHandler.display("Cannot play MIDI music");
			return;
		}
		
		try {
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(0);
			sequencer.start();
		} catch (InvalidMidiDataException e) {
			ErrorHandler.display("MIDI music data is invalid");
			// no error recovery
		}
	}
	
	/**
	 * Stops whatever is currently playing.
	 */
	public void stop() {
		if (sequencer == null) return;

		sequencer.stop();
	}
	
	/**
	 * Plays a single note with a default instrument in the synth channel.
	 * @param pitch an int, 0 = C0, 60 = middle C
	 */
	public void playNote(int pitch) {
		synthChannel.noteOn(pitch, MusicManager.SYNTH_NOTE_VELOCITY);
	}

	/**
	 * Stop a single note with a default instrument in the synth channel.
	 * @param pitch an int, 0 = C0, 60 = middle C
	 */
	public void stopNote(int pitch) {
		synthChannel.noteOff(pitch, 127);
	}

	/**
	 * Pedal on in synthesizer.
	 */
	public void pedalDown() {
		synthChannel.controlChange(MusicManager.PEDAL_ID, MusicManager.PEDAL_ON);
	}

	/**
	 * Pedal off in synthesizer.
	 */
	public void pedalUp() {
		synthChannel.controlChange(MusicManager.PEDAL_ID, MusicManager.PEDAL_OFF);
	}
	
	/**
	 * Sets a new instrument for the synthesizer and change the instrument for real.
	 * @param synthInstrument
	 */
	public void setSynthInstrument(int synthInstrument) {
		// no error checking
		this.synthInstrument = synthInstrument;
		synthChannel.programChange(synthInstrument);
	}

	public void decSynthInstrument() {
		if (synthInstrument > 0) {
			setSynthInstrument(synthInstrument - 1);
		} else {
			setSynthInstrument(MusicManager.NUM_INSTRUMENT - 1);
		}
	}
	
	public void incSynthInstrument() {
		if (synthInstrument < MusicManager.NUM_INSTRUMENT - 1) {
			setSynthInstrument(synthInstrument + 1);
		} else {
			setSynthInstrument(0);
		}
	}
	
	public int getSynthInstrument() {
		return synthInstrument;
	}
	
	/**
	 * Returns the name of the current instrument in synthesizer channel.
	 * @return the name of the instrument
	 */
	public String getInstrumentName() {
		return MusicManager.getInstrumentName(synthInstrument);
	}
}
