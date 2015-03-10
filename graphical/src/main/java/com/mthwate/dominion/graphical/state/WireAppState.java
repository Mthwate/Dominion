package com.mthwate.dominion.graphical.state;

import com.mthwate.dominion.graphical.KeyControl;
import com.mthwate.dominion.graphical.node.NodeHandler;

/**
 * Listens for the "toggle wire" key to toggle the wire frame.
 *
 * @author mthwate
 */
public class WireAppState extends InputAppState {

	@Override
	public void update(float tpf) {
		if (keyHandler.isPressed(KeyControl.TOGGLE_WIRE)) {
			keyHandler.unpress(KeyControl.TOGGLE_WIRE);
			NodeHandler.toggleWire();
		}
	}

}
