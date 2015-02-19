package com.mthwate.dominion.server.command;

import com.jme3.network.Server;
import com.mthwate.dominion.common.log.Log;

/**
 * @author mthwate
 */
public class CommandEcho implements Command {
	
	public String getName() {
		return "echo";
	}
	
	public void run(Server server, String params) {
		Log.CONSOLE.info(params);
	}
	
}
