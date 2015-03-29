package com.mthwate.dominion.graphical.action.zoom;

import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.action.AnalogAction;

/**
 * @author mthwate
 */
public abstract class ZoomAction extends AnalogAction {

	protected final float ZOOM_SPEED = 3f;

	protected final int ZOOM_MAX = 250;

	protected final int ZOOM_MIN = 3;

	protected Camera cam;

	public ZoomAction(Camera camera) {
		cam = camera;
	}

}
