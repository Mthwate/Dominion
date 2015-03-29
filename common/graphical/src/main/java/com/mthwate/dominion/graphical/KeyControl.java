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

	LEFT_CLICK("leftClick"),
	RIGHT_CLICK("rightClick"),

	SCREENSHOT("screenshot"),
	
	;

	@Getter private final String name;

	KeyControl(String name) {
		this.name = name;
	}
}
