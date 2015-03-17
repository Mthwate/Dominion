package com.mthwate.dominion.graphical;

import lombok.Getter;

/**
 * @author mthwate
 */
public enum KeyControl {

	UP("up"),
	DOWN("down"),
	LEFT("left"),
	RIGHT("right"),

	LOOK_RIGHT("lookRight"),
	LOOK_LEFT("lookLeft"),
	LOOK_NORTH("lookNorth"),

	
	MENU("menu"),
	
	ZOOM_IN("zoomIn"),
	ZOOM_OUT("zoomOut"),
	
	CLICK("click"),
	
	INCREASE_BRUSH("increaseBrush"),
	DECREASE_BRUSH("decreaseBrush"),

	TOGGLE_WIRE("toggleWire"),

	GOTO_HOME("gotoHome"),

	SCREENSHOT("screenshot"),
	
	;

	@Getter private final String name;

	private KeyControl(String name) {
		this.name = name;
	}
}
