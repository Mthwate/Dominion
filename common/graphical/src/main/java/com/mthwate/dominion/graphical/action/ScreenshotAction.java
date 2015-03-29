package com.mthwate.dominion.graphical.action;

import com.jme3.app.state.AppStateManager;
import com.mthwate.dominion.graphical.state.ScreenshotAppState;

/**
 * @author mthwate
 */
public class ScreenshotAction extends PressAction {

	private ScreenshotAppState state = new ScreenshotAppState();

	public ScreenshotAction(AppStateManager stateManager) {
		stateManager.attach(state);
	}

	@Override
	protected void onAction(boolean isPressed) {
		if (isPressed) {
			state.takeScreenshot();
		}
	}

	@Override
	public ActionKey getKey() {
		return ActionKey.SCREENSHOT;
	}

}
