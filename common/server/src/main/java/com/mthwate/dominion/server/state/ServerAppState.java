package com.mthwate.dominion.server.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.mthwate.dominion.server.ServerApp;

/**
 * @author mthwate
 */
public class ServerAppState extends AbstractAppState {

	protected ServerApp sapp;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		sapp = (ServerApp) app;
	}

}