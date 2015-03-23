package com.mthwate.dominion.client;

import com.mthwate.dominion.client.state.MobileMoveAppState;
import com.mthwate.dominion.client.state.MobilePathAppState;

/**
 * @author mthwate
 */
public class MobileClientApp extends ClientApp {

	@Override
	protected void onJoin() {
		stateManager.attach(new MobileMoveAppState(settings));
		stateManager.attach(new MobilePathAppState(client, rootNode, settings));
	}

}
