package com.mthwate.dominion.graphical.state;

import com.jme3.app.state.AbstractAppState;
import com.mthwate.dominion.graphical.node.NodeHandler;

/**
 * @author mthwate
 */
public class NodeAppState extends AbstractAppState {

	@Override
	public void update(float tpf) {
		NodeHandler.update();
	}

}
