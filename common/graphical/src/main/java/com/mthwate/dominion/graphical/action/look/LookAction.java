package com.mthwate.dominion.graphical.action.look;

import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.action.AnalogAction;

/**
 * @author mthwate
 */
public abstract class LookAction extends AnalogAction {

	protected Camera cam;

	public LookAction(Camera camera) {
		cam = camera;
	}

}
