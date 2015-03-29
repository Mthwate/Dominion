package com.mthwate.dominion.graphical;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class KeyHandler implements ActionListener {

	private Map<String, Boolean> pressed = new HashMap<String, Boolean>();

	public KeyHandler(InputManager im) {
		setUpKeys(im);
	}

	private void setUpKeys(InputManager im) {
		addKeyListener(im, KeyControl.LEFT, KeyInput.KEY_A);
		addKeyListener(im, KeyControl.RIGHT, KeyInput.KEY_D);
		addKeyListener(im, KeyControl.UP, KeyInput.KEY_W);
		addKeyListener(im, KeyControl.DOWN, KeyInput.KEY_S);

		addMouseListener(im, KeyControl.LEFT_CLICK, MouseInput.BUTTON_LEFT);
		addMouseListener(im, KeyControl.RIGHT_CLICK, MouseInput.BUTTON_RIGHT);

		addKeyListener(im, KeyControl.SCREENSHOT, KeyInput.KEY_P);
	}

	private void addMouseListener(InputManager im, KeyControl control, int button) {
		addListener(im, control, new MouseButtonTrigger(button));
	}

	private void addAxisListener(InputManager im, KeyControl control, int axis, boolean negative) {
		addListener(im, control, new MouseAxisTrigger(axis, negative));
	}

	private void addKeyListener(InputManager im, KeyControl control, int key) {
		addListener(im, control, new KeyTrigger(key));
	}

	private void addListener(InputManager im, KeyControl control, Trigger trigger) {
		String name = control.getName();
		im.addMapping(name, trigger);
		im.addListener(this, name);
		pressed.put(name, false);
	}

	public boolean isPressed(KeyControl key) {
		return pressed.get(key.getName());
	}

	@Deprecated
	public void press(KeyControl key) {
		onAction(key.getName(), true, 0);
	}

	@Deprecated
	public void unpress(KeyControl key) {
		onAction(key.getName(), false, 0);
	}

	@Override
	public void onAction(String binding, boolean isPressed, float tpf) {
		pressed.put(binding, isPressed);
	}
}
