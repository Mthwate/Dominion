package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.mthwate.dominion.graphical.GraphicalApp;

/**
 * @author mthwate
 */
public abstract class GraphicalAppState extends AbstractAppState {

	protected GraphicalApp gapp;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		gapp = (GraphicalApp) app;
	}

}
