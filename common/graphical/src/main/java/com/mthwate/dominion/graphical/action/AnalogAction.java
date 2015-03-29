package com.mthwate.dominion.graphical.action;

import com.jme3.input.controls.AnalogListener;

/**
 * @author mthwate
 */
public abstract class AnalogAction implements Action, AnalogListener {

	protected abstract void onAction(float time);

	@Override
	public void onAnalog(String name, float value, float tpf) {
		onAction(value);
	}

}
