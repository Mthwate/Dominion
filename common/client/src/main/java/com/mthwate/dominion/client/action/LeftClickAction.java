package com.mthwate.dominion.client.action;

import com.jme3.app.state.AppStateManager;
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

	public LeftClickAction(Client client, Node parent, AppStateManager stateManager) {
		state = new PathAppState(client, parent);
		stateManager.attach(state);
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
