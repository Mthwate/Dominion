package com.mthwate.dominion.server.command;

import com.mthwate.dominion.server.ServerApp;
import lombok.extern.java.Log;

/**
 * @author mthwate
 */
@Log
public class CommandEcho implements Command {

	@Override
	public String getName() {
		return "echo";
	}

	@Override
	public void run(ServerApp app, String params) {
		log.info(params);
	}
	
}
