package main.thread;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import util.ErrorHandler;
import util.Utilities;

/**
 * Encapsulates an event and a duration in millisecond, which is the number
 * of millisecond to wait before the event is executed.
 * The event is a Runnable.
 * 
 * As a consequence of the specific implementation, a TimedEvent cannot
 * be used by more than one Thread.
 * 
 * @author Derianto Kusuma
 *
 */
public class TimedEvent {

	static public final int MAX_SHORT_DELAY_MILLIS = 1000;
	
	private Runnable runnable;
	private int delay;
	
	/**
	 * Creates a new TimedEvent with
	 * @param runnable the Runnable containing the run method to run
	 * @param delay the delay in millisecond before the run method is run
	 */
	public TimedEvent(Runnable runnable, int delay) {
		this.runnable = runnable;
		this.delay = delay;
	}
	
	/**
	 * Waits for the specified delay millisecond and calls the run method
	 * in the contained Runnable.  The call is asynchronous (waits until
	 * the call exits).
	 * @throws InterruptedException if interrupted while waiting
	 */
	public void execute() throws InterruptedException {
		
		longWait(delay); // InterruptedException may be thrown

		runnable.run();
		
		// but the use of Swing Thread causes problem: events can still happen
		// even if the Thread is already finished
		//SwingUtilities.invokeLater(runnable); // may throw InterruptedException

		/*try {
			// streamlined and async to avoid shared memory problem
			SwingUtilities.invokeAndWait(runnable); // may throw InterruptedException
			
		} catch (InvocationTargetException e) {
			// exception in the run method
			ErrorHandler.display("Unknown error in Thread " + Thread.currentThread());
		}*/
	}
	
	/**
	 * Performs a wait with long delay by chopping it into many short-duration
	 * waits.
	 * @param delay in millisecond
	 * @throws InterruptedException
	 */
	public synchronized void longWait(int delay) throws InterruptedException {
		//Utilities.trace("Thread " + Thread.currentThread().getName() +
		//		": TimedEvent longWait delay = " + delay);
		
		// for sync reason this method needs to be synchronized in order to be
		// able to use wait().  Possible consequences: one TimedEvent object
		// cannot be executed by two Threads at the same time
		
		// wait n times, each wait's delay is (delay / n) 
		int n = delay / TimedEvent.MAX_SHORT_DELAY_MILLIS; 
		for (int i = 0; i < n; i++)
			wait(TimedEvent.MAX_SHORT_DELAY_MILLIS);

		// wait for the remainder
		int remaining = delay % TimedEvent.MAX_SHORT_DELAY_MILLIS;
		if (remaining > 0)
			wait(remaining);

	}
	
}
