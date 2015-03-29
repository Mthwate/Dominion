package com.mthwate.dominion.editor.action;

import com.mthwate.dominion.editor.NiftyUtils;
import com.mthwate.dominion.graphical.action.ActionKey;
import com.mthwate.dominion.graphical.action.PressAction;

/**
 * @author mthwate
 */
public class IncreaseBrushAction extends PressAction {

	@Override
	protected void onAction(boolean isPressed) {
		if (isPressed) {
			NiftyUtils.setMenuInt("brushSize", NiftyUtils.getMenuInt("brushSize") + 1);
		}
	}

	@Override
	public ActionKey getKey() {
		return ActionKey.INCREASE_BRUSH;
	}

}
