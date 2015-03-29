package com.mthwate.dominion.graphical.action;

import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.GraphicalApp;

/**
 * @author mthwate
 */
public class HomeAction extends PressAction {

	private Camera cam;

	public HomeAction(Camera camera) {
		cam = camera;
	}

	@Override
	protected void onAction() {
		cam.setLocation(GraphicalApp.CAM_ORIGIN.setZ(cam.getLocation().getZ()));
	}

	@Override
	public ActionKey getKey() {
		return ActionKey.GOTO_HOME;
	}

}
