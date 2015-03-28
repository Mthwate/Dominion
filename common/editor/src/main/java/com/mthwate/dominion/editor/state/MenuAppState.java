package com.mthwate.dominion.editor.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.editor.NiftyUtils;
import com.mthwate.dominion.graphical.KeyControl;
import com.mthwate.dominion.graphical.KeyHandler;
import com.mthwate.dominion.graphical.state.GraphicalAppState;

/**
 * @author mthwate
 */
public class MenuAppState extends GraphicalAppState {

	private KeyHandler keyHandler;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		keyHandler = gapp.getKeyHandler();
	}

	@Override
	public void update(float tpf) {
		if (keyHandler.isPressed(KeyControl.MENU)) {
			keyHandler.unpress(KeyControl.MENU);
			if (NiftyUtils.isOnScreen("menu")) {

				int x = NiftyUtils.getMenuInt("width");

				int y = NiftyUtils.getMenuInt("height");

				TileStore.resize(x, y);

				NiftyUtils.gotoScreen("edit");
			} else {
				NiftyUtils.gotoScreen("menu");
			}
		}
	}

}
