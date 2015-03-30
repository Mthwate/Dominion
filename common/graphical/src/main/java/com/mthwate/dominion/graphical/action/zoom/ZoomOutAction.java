package com.mthwate.dominion.graphical.action.zoom;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.action.ActionKey;

/**
 * @author mthwate
 */
public class ZoomOutAction extends ZoomAction {

	public ZoomOutAction(Camera camera) {
		super(camera);
	}

	@Override
	protected int getMult() {
		return 1;
	}

	@Override
	public ActionKey getKey() {
		return ActionKey.ZOOM_OUT;
	}

}
