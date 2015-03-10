package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.mthwate.dominion.graphical.GraphicalApp;
import com.mthwate.dominion.graphical.KeyHandler;

/**
 * @author mthwate
 */
public abstract class InputAppState extends AbstractAppState {

	protected KeyHandler keyHandler;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		GraphicalApp gapp = (GraphicalApp) app;
		keyHandler = gapp.getKeyHandler();
	}

}
