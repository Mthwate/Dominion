package com.mthwate.dominion.server.command;

import com.jme3.network.Server;

/**
 * @author mthwate
 */
public interface Command {

	public String getName();

	public void run(Server server, String params);
	
}
