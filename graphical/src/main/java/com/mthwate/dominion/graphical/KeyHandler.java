package com.mthwate.dominion.graphical;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;

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
		
		addKeyListener(im, KeyControl.NORTH, KeyInput.KEY_UP);
		addKeyListener(im, KeyControl.SOUTH, KeyInput.KEY_DOWN);
		addKeyListener(im, KeyControl.WEST, KeyInput.KEY_LEFT);
		addKeyListener(im, KeyControl.EAST, KeyInput.KEY_RIGHT);
		
		addKeyListener(im, KeyControl.MENU, KeyInput.KEY_LMENU);
		
		addAxisListener(im, KeyControl.ZOOM_IN, MouseInput.AXIS_WHEEL, false);
		addAxisListener(im, KeyControl.ZOOM_OUT, MouseInput.AXIS_WHEEL, true);
		
		addMouseListener(im, KeyControl.CLICK, MouseInput.BUTTON_LEFT);

		addKeyListener(im, KeyControl.INCREASE_BRUSH, KeyInput.KEY_RBRACKET);
		addKeyListener(im, KeyControl.DECREASE_BRUSH, KeyInput.KEY_LBRACKET);

		addKeyListener(im, KeyControl.TOGGLE_WIRE, KeyInput.KEY_SPACE);

		addKeyListener(im, KeyControl.GOTO_HOME, KeyInput.KEY_H);
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

	@Override
	public void onAction(String binding, boolean isPressed, float tpf) {
		pressed.put(binding, isPressed);
	}
}
