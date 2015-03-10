package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.GraphicalApp;
import com.mthwate.dominion.graphical.KeyControl;

/**
 * Listens for the "home" key to return the camera to the home position.
 *
 * @author mthwate
 */
public class HomeAppState extends InputAppState {

	private Camera cam;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		cam = app.getCamera();
	}

	@Override
	public void update(float tpf) {
		if (keyHandler.isPressed(KeyControl.GOTO_HOME)) {
			keyHandler.unpress(KeyControl.GOTO_HOME);
			cam.setLocation(GraphicalApp.CAM_ORIGIN.setZ(cam.getLocation().getZ()));
		}
	}

}
