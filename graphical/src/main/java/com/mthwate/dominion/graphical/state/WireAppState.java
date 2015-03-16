package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.mthwate.dominion.graphical.KeyControl;
import com.mthwate.dominion.graphical.KeyHandler;
import com.mthwate.dominion.graphical.node.NodeHandler;

/**
 * Listens for the "toggle wire" key to toggle the wire frame.
 *
 * @author mthwate
 */
public class WireAppState extends GraphicalAppState {

	private KeyHandler keyHandler;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		keyHandler = gapp.getKeyHandler();
	}

	@Override
	public void update(float tpf) {
		if (keyHandler.isPressed(KeyControl.TOGGLE_WIRE)) {
			keyHandler.unpress(KeyControl.TOGGLE_WIRE);
			NodeHandler.toggleWire();
		}
	}

}
