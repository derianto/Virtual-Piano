package music;

/**
 * Encapsulates a single abstract musical note (only the model).
 * Currently only used as a namespace that contains static methods.
 * 
 * @author Derianto Kusuma
 */
public class MusicalNote {
	
	public static final int OCTAVE_PITCH_DELTA = 12;
	
	/**
	 * Returns whether the specified pitch is chromatic.  Chromatic notes:
	 * C#, D#, F#, Ab, Bb.
	 * @param pitch
	 * @return true is chromatic, and false otherwise
	 */
	public static boolean isChromatic(int pitch) {
		int basePitch = pitch % MusicalNote.OCTAVE_PITCH_DELTA;
		return (
				basePitch == 1 ||
				basePitch == 3 ||
				basePitch == 6 ||
				basePitch == 8 ||
				basePitch == 10
		);
	}
	
}
