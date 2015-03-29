package com.mthwate.dominion.client.action;

import com.jme3.app.Application;
import com.jme3.network.Client;
import com.jme3.scene.Node;
import com.mthwate.dominion.client.state.PathAppState;
import com.mthwate.dominion.graphical.action.ActionKey;
import com.mthwate.dominion.graphical.action.PressAction;

/**
 * @author mthwate
 */
public class LeftClickAction extends PressAction {

	private PathAppState state;

	public LeftClickAction(Client client, Node parent, Application app) {
		state = new PathAppState(client, parent, app);
		app.getStateManager().attach(state);
	}

	@Override
	protected void onAction(boolean isPressed) {
		state.setClicked(isPressed);
	}

	@Override
	public ActionKey getKey() {
		return ActionKey.LEFT_CLICK;
	}

}
