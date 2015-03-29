package com.mthwate.dominion.graphical.action.look;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.GraphicalApp;
import com.mthwate.dominion.graphical.action.ActionKey;

/**
 * @author mthwate
 */
public class LookNorthAction extends LookAction {

	public LookNorthAction(Camera camera) {
		super(camera);
	}

	@Override
	protected void onAction(float time) {
		cam.lookAtDirection(new Vector3f(0, 1, -1), GraphicalApp.UP);
	}

	@Override
	public ActionKey getKey() {
		return ActionKey.LOOK_NORTH;
	}

}
