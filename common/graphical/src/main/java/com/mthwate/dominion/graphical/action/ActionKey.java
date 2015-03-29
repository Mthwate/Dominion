package com.mthwate.dominion.graphical.action;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import lombok.Getter;

/**
 * @author mthwate
 */
public enum ActionKey {

	UP("up", new KeyTrigger(KeyInput.KEY_W)),
	DOWN("down", new KeyTrigger(KeyInput.KEY_S)),
	LEFT("left", new KeyTrigger(KeyInput.KEY_A)),
	RIGHT("right", new KeyTrigger(KeyInput.KEY_D)),

	LOOK_RIGHT("lookRight", new KeyTrigger(KeyInput.KEY_RIGHT)),
	LOOK_LEFT("lookLeft", new KeyTrigger(KeyInput.KEY_LEFT)),
	LOOK_NORTH("lookNorth", new KeyTrigger(KeyInput.KEY_UP)),


	MENU("menu", new KeyTrigger(KeyInput.KEY_LMENU)),

	ZOOM_IN("zoomIn", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false)),
	ZOOM_OUT("zoomOut", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true)),

	LEFT_CLICK("leftClick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT)),
	RIGHT_CLICK("rightClick", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT)),

	INCREASE_BRUSH("increaseBrush", new KeyTrigger(KeyInput.KEY_RBRACKET)),
	DECREASE_BRUSH("decreaseBrush", new KeyTrigger(KeyInput.KEY_LBRACKET)),

	TOGGLE_WIRE("toggleWire", new KeyTrigger(KeyInput.KEY_SPACE)),

	GOTO_HOME("gotoHome", new KeyTrigger(KeyInput.KEY_H)),

	SCREENSHOT("screenshot", new KeyTrigger(KeyInput.KEY_P)),

	;

	@Getter private final String name;
	@Getter private final Trigger trigger;

	ActionKey(String name, Trigger trigger) {
		this.name = name;
		this.trigger = trigger;
	}

}
