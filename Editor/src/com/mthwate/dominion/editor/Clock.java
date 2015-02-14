package com.mthwate.dominion.editor;

/**
 * @author mthwate
 */
public class Clock {
	
	private final float cycle;
	
	private float count = 0;
	
	public Clock(float cycle) {
		this.cycle = cycle;
	}
	
	public float getRatio() {
		return count / cycle;
	}

	public float getCycle() {
		return cycle;
	}

	public float getCount() {
		return count;
	}
	
	public void update(float amt) {
		count += amt;
		while (count > cycle) {
			count -= cycle;
		}
	}
	
}
