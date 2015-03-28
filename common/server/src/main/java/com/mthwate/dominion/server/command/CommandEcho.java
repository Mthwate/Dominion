package com.mthwate.dominion.server.command;

import com.mthwate.dominion.server.ServerApp;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mthwate
 */
@Slf4j
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
