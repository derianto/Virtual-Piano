package main.thread;

import java.util.List;

/**
 * Encapsulates a scenario.  A scenario is an ordered list of TimedEvent.
 * See TimedEvent.  Every event (TimedEvent object) in the scenario will
 * be executed one by one in order until all events are executed.  
 * @author Derianto Kusuma
 *
 */
public class Scenario {
	
	private List<TimedEvent> events;

	/**
	 * Creates a new Scenario that will execute the list of events supplied.
	 * @param events the list of events to execute one by one
	 */
	public Scenario(List<TimedEvent> events) {
		this.events = events;
	}
	
	/**
	 * Executes each event in the event list, one by one.  Each event execution
	 * consists of waiting for a certain millisecond and running a function.
	 * It will synchronously wait for the function to finish before it starts
	 * the waiting process for the new event.  Terminates when the last
	 * event finishes its execution. 
	 * @throws InterruptedException when the Thread is terminated while waiting
	 */
	public void execute() throws InterruptedException {
		for (TimedEvent event: events)
			event.execute(); // InterruptedException may be thrown here
	}
	
}
