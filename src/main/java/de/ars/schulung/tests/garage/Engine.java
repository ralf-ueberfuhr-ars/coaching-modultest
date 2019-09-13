package de.ars.schulung.tests.garage;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An engine.
 */
public class Engine {

	// not static, not final, not private -> testability
	Logger logger = LogManager.getLogger(Engine.class);

	private final double hp;
	private final Thread engineThread;
	private boolean engineStarted;

	public Engine() {
		this.hp = 190;
		this.engineThread = new Thread() {
			@Override
			public void run() {
				while (engineStarted) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						logger.warn(
								this
										+ " is not working correctly at the moment! Stop the engine when this problem occurs again.");
					}
					logger.log(Level.INFO, this + " is currently running");
				}
			}
		};
	}

	/**
	 * Returns the horse power.
	 * 
	 * @return the horse power.
	 */
	public double getHp() {
		return hp;
	}

	public boolean isEngineStarted() {
		return engineStarted;
	}

	/**
	 * Starts the engine.
	 */
	public void start() {
		engineStarted = true;
		engineThread.start();
		logger.info("Engine started.");
	}

	/**
	 * Stops the engine.
	 */
	public void stop() {
		engineStarted = false;
	}

}
