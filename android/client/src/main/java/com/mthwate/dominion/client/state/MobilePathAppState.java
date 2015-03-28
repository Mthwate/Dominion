package com.mthwate.dominion.client.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.math.Vector2f;
import com.jme3.network.Client;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.client.NiftyUtils;

/**
 * @author mthwate
 */
public class MobilePathAppState extends PathAppState {

	private InputManager inputManager;

	private int height;

	public MobilePathAppState(Client client, Node parent, AppSettings settings) {
		super(client, parent);
		height = settings.getHeight();
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		inputManager = app.getInputManager();
	}

	@Override
	protected void update(float tpf, Set2i pos, boolean clickedL, boolean clickedR) {
		Vector2f click2d = inputManager.getCursorPosition();
		int x = (int) click2d.getX();
		int y = height - (int) click2d.getY();
		boolean onDpad = NiftyUtils.getNifty().getScreen("game").findElementByName("dpad").isMouseInsideElement(x, y);
		super.update(tpf, pos, clickedL && !onDpad, clickedR);
	}

}
