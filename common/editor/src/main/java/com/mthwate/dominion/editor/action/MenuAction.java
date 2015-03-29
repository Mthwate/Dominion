package com.mthwate.dominion.editor.action;

import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.editor.NiftyUtils;
import com.mthwate.dominion.graphical.action.ActionKey;
import com.mthwate.dominion.graphical.action.PressAction;

/**
 * @author mthwate
 */
public class MenuAction extends PressAction {

	@Override
	protected void onAction() {
		if (NiftyUtils.isOnScreen("menu")) {

			int x = NiftyUtils.getMenuInt("width");

			int y = NiftyUtils.getMenuInt("height");

			TileStore.resize(x, y);

			NiftyUtils.gotoScreen("edit");
		} else {
			NiftyUtils.gotoScreen("menu");
		}
	}

	@Override
	public ActionKey getKey() {
		return ActionKey.MENU;
	}

}
