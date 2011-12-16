package main.thread;

/**
 * A Thread that executes a TimedEvent repeatedly at a regular interval,
 * until interrupted.  Note that interruption can be done inside the run
 * method itself.
 * @author Derianto Kusuma
 */
public class TimerThread extends Thread {

	private TimedEvent event;
	
	/**
	 * Creates a new TimerThread.
	 * @param event the event to execute repeatedly
	 */
	public TimerThread(TimedEvent event) {
		this.event = event;
	}
	
	/**
	 * Executes the TimedEvent repeatedly until interrupted.
	 */
	@Override
	public void run() {
		try {
			while (true)
				event.execute(); // may throw InterruptedException
			                     // execute() is an async function
		} catch (InterruptedException e) {
			return; // interruption is normal
		}
	}
}
