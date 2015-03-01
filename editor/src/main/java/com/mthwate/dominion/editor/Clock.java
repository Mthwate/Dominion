package com.mthwate.dominion.editor;

/**
 * @author mthwate
 */
public class Clock {
	
	private final float cycle;
	
	private float progress = 0;

	/**
	 * Initializes a new clock with the supplied cycle time.
	 *
	 * @param cycle the time in seconds for the clock to make a full cycle
	 */
	public Clock(float cycle) {
		this.cycle = cycle;
	}
	
	public float getRatio() {
		return progress / cycle;
	}

	public float getCycle() {
		return cycle;
	}

	public float getProgress() {
		return progress;
	}
	
	public void update(float amt) {
		progress += amt;
		while (progress >= cycle) {
			progress -= cycle;
		}
	}
	
}
