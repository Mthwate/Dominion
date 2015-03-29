package com.mthwate.dominion.graphical.action;

import com.mthwate.dominion.graphical.node.NodeHandler;

/**
 * @author mthwate
 */
public class WireToggleAction extends PressAction {

	@Override
	protected void onAction() {
		NodeHandler.toggleWire();
	}

	@Override
	public ActionKey getKey() {
		return ActionKey.TOGGLE_WIRE;
	}

}
