package com.mthwate.dominion.client.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.math.Vector2f;
import com.jme3.system.AppSettings;
import com.mthwate.dominion.client.NiftyUtils;
import com.mthwate.dominion.graphical.KeyControl;
import com.mthwate.dominion.graphical.KeyHandler;
import com.mthwate.dominion.graphical.state.GraphicalAppState;
import de.lessvoid.nifty.screen.Screen;

/**
 * @author mthwate
 */
public class MobileMoveAppState extends GraphicalAppState {

	private KeyHandler keyHandler;

	private InputManager inputManager;

	private int height;

	public MobileMoveAppState(AppSettings settings) {
		height = settings.getHeight();
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		keyHandler = gapp.getKeyHandler();
		inputManager = app.getInputManager();
	}

	@Override
	public void update(float tpf) {
		boolean clicked = keyHandler.isPressed(KeyControl.CLICK);
		Vector2f click2d = inputManager.getCursorPosition();
		Screen screen = NiftyUtils.getNifty().getScreen("game");
		control(screen, (int) click2d.getX(), (int) click2d.getY(), clicked, KeyControl.UP);
		control(screen, (int) click2d.getX(), (int) click2d.getY(), clicked, KeyControl.DOWN);
		control(screen, (int) click2d.getX(), (int) click2d.getY(), clicked, KeyControl.LEFT);
		control(screen, (int) click2d.getX(), (int) click2d.getY(), clicked, KeyControl.RIGHT);
	}

	private void control(Screen screen, int x, int y, boolean clicked, KeyControl key) {
		if (clicked && screen.findElementByName(key.getName()).isMouseInsideElement(x, height - y)) {
			keyHandler.press(key);
		} else {
			keyHandler.unpress(key);
		}
	}

}
