package com.mthwate.dominion.graphical.action;

import com.jme3.input.controls.InputListener;

/**
 * @author mthwate
 */
public interface Action extends InputListener {

	ActionKey getKey();

}
