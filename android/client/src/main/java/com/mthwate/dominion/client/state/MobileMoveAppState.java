package com.mthwate.dominion.client.state;

import com.jme3.math.Vector2f;
import com.jme3.system.AppSettings;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.client.NiftyUtils;
import com.mthwate.dominion.graphical.KeyControl;
import com.mthwate.dominion.graphical.state.MouseAppState;
import de.lessvoid.nifty.screen.Screen;

/**
 * @author mthwate
 */
public class MobileMoveAppState extends MouseAppState {

	private int height;

	public MobileMoveAppState(AppSettings settings) {
		height = settings.getHeight();
	}

	@Override
	protected void update(float tpf, Set2i pos, boolean clickedL, boolean clickedR) {
		Vector2f click2d = inputManager.getCursorPosition();
		Screen screen = NiftyUtils.getNifty().getScreen("game");
		control(screen, (int) click2d.getX(), (int) click2d.getY(), clickedL, KeyControl.UP);
		control(screen, (int) click2d.getX(), (int) click2d.getY(), clickedL, KeyControl.DOWN);
		control(screen, (int) click2d.getX(), (int) click2d.getY(), clickedL, KeyControl.LEFT);
		control(screen, (int) click2d.getX(), (int) click2d.getY(), clickedL, KeyControl.RIGHT);
	}

	private void control(Screen screen, int x, int y, boolean clicked, KeyControl key) {
		if (clicked && screen.findElementByName(key.getName()).isMouseInsideElement(x, height - y)) {
			keyHandler.press(key);
		} else {
			keyHandler.unpress(key);
		}
	}

}
