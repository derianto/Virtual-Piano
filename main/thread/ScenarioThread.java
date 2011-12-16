package main.thread;

/**
 * A Thread the executes a scenario until interrupted or until finished.
 * @author Derianto Kusuma
 */
public class ScenarioThread extends Thread {

	private Scenario scenario;
	
	/**
	 * Creates a new ScenarioThread with specified scenario to execute.
	 * @param scenario the scenario to execute
	 */
	public ScenarioThread(Scenario scenario) {
		this.scenario = scenario;
	}
	
	/**
	 * Executes the scenario.
	 */
	@Override
	public void run() {
		try {
			scenario.execute();
		} catch (InterruptedException e) {
			return; // interruption is normal
		}
	}
	
}
