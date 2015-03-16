package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.GraphicalApp;
import com.mthwate.dominion.graphical.KeyControl;
import com.mthwate.dominion.graphical.KeyHandler;

/**
 * Listens for the "home" key to return the camera to the home position.
 *
 * @author mthwate
 */
public class HomeAppState extends GraphicalAppState {

	private KeyHandler keyHandler;

	private Camera cam;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		keyHandler = gapp.getKeyHandler();
		cam = gapp.getCamera();
	}

	@Override
	public void update(float tpf) {
		if (keyHandler.isPressed(KeyControl.GOTO_HOME)) {
			keyHandler.unpress(KeyControl.GOTO_HOME);
			cam.setLocation(GraphicalApp.CAM_ORIGIN.setZ(cam.getLocation().getZ()));
		}
	}

}
