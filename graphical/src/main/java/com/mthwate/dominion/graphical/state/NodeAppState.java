package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.mthwate.dominion.graphical.node.NodeHandler;

/**
 * @author mthwate
 */
public class NodeAppState extends AbstractAppState {

	private AssetManager assetManager;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		assetManager = app.getAssetManager();
	}

	@Override
	public void update(float tpf) {
		NodeHandler.update(assetManager);
	}

}
