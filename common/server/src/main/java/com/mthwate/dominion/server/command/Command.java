package com.mthwate.dominion.server.command;

import com.mthwate.dominion.server.ServerApp;

/**
 * @author mthwate
 */
public interface Command {

	/**
	 * Gets the name of the command.
	 *
	 * @return the command name
	 */
	public String getName();

	public void run(ServerApp app, String params);
	
}
