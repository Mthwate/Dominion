package com.mthwate.dominion.editor.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.mthwate.dominion.editor.EditorApp;
import com.mthwate.dominion.graphical.GraphicalApp;

/**
 * @author mthwate
 */
public class EditorAppState extends AbstractAppState {

	protected EditorApp eapp;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		eapp = (EditorApp) app;
	}

}
