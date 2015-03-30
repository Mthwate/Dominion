package com.mthwate.dominion.graphical.action.move;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.action.ActionKey;

/**
 * @author mthwate
 */
public class MoveRightAction extends MoveAction {

	public MoveRightAction(Camera camera) {
		super(camera);
	}

	@Override
	protected Vector3f getDirection() {
		return cam.getLeft().negate();
	}

	@Override
	public ActionKey getKey() {
		return ActionKey.RIGHT;
	}

}