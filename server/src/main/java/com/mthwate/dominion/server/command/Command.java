package com.mthwate.dominion.server.command;

import com.jme3.asset.AssetManager;
import com.jme3.network.Server;

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

	public void run(Server server, AssetManager assetManager, String params);
	
}
