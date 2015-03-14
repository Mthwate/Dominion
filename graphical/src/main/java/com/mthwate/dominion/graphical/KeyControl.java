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

	NORTH("north"),
	SOUTH("south"),
	WEST("west"),
	EAST("east"),
	
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
