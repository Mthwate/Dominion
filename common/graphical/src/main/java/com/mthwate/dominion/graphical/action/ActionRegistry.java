package com.mthwate.dominion.graphical.action;

import com.jme3.input.InputManager;
import com.jme3.input.controls.Trigger;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mthwate
 */
@Slf4j
public class ActionRegistry {

	private static InputManager inputManager;

	public static void init(InputManager inputManager) {
		ActionRegistry.inputManager = inputManager;
	}

	public static void register(Action action) {
		log.info("Registering action {}", action.getClass().getSimpleName());
		ActionKey key = action.getKey();
		Trigger trigger = key.getTrigger();
		String name = key.getName();

		inputManager.addMapping(name, trigger);
		inputManager.addListener(action, name);
	}
}
