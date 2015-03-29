package com.mthwate.dominion.graphical.action.move;

import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.action.AnalogAction;

/**
 * @author mthwate
 */
public abstract class MoveAction extends AnalogAction {

	protected float MOVE_SPEED = 2f;

	protected Camera cam;

	public MoveAction(Camera camera) {
		cam = camera;
	}

}
