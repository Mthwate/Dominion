package com.mthwate.dominion.server.command;

import com.jme3.network.Server;
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
	public void run(Server server, String params) {
		log.info(params);
	}
	
}