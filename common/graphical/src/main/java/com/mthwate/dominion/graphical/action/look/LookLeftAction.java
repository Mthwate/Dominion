package com.mthwate.dominion.graphical.action.look;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.GraphicalApp;
import com.mthwate.dominion.graphical.action.ActionKey;

/**
 * @author mthwate
 */
public class LookLeftAction extends LookAction {

	public LookLeftAction(Camera camera) {
		super(camera);
	}

	@Override
	protected int getMult() {
		return -1;
	}

	@Override
	public ActionKey getKey() {
		return ActionKey.LOOK_LEFT;
	}

}
