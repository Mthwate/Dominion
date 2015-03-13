package com.mthwate.dominion.server.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.network.Server;
import com.mthwate.dominion.server.ServerApp;

/**
 * @author mthwate
 */
public class ServerAppState extends AbstractAppState {

	protected Server server;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		ServerApp sapp = (ServerApp) app;
		server = sapp.getServer();
	}

}