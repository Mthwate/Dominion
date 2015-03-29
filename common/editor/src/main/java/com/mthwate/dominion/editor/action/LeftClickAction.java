package com.mthwate.dominion.editor.action;

import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.mthwate.dominion.editor.state.BrushAppState;
import com.mthwate.dominion.graphical.action.ActionKey;
import com.mthwate.dominion.graphical.action.PressAction;

/**
 * @author mthwate
 */
public class LeftClickAction extends PressAction {

	private BrushAppState state;

	public LeftClickAction(AppStateManager stateManager, Node parent) {
		state = new BrushAppState(parent);
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
