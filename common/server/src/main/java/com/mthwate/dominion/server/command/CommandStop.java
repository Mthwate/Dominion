package com.mthwate.dominion.server.command;

import com.mthwate.dominion.server.ServerApp;

/**
 * @author mthwate
 */
public class CommandStop implements Command {

	@Override
	public String getName() {
		return "stop";
	}

	@Override
	public void run(ServerApp app, String params) {
		app.stop();
	}

}
