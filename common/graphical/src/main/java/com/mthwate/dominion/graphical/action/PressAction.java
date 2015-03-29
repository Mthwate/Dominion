package com.mthwate.dominion.graphical.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author mthwate
 */
public abstract class PressAction implements Action, ActionListener {

	protected abstract void onAction();

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		onAction();
	}
}
