package com.mthwate.dominion.common;

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

	TOGGLE_WIRE("toggleWire");
	
	;

	private String name;

	private KeyControl(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
