package com.mthwate.dominion.graphical.action;

import com.jme3.input.controls.ActionListener;

/**
 * @author mthwate
 */
public abstract class PressAction implements Action, ActionListener {

	protected abstract void onAction(boolean isPressed);

	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		onAction(isPressed);
	}
}
