package com.mthwate.dominion.server.command;

import com.jme3.asset.AssetManager;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;

import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class CommandUsers implements Command {

	private static final Logger log = Logger.getLogger(CommandUsers.class.getName());
	
	@Override
	public String getName() {
		return "users";
	}

	@Override
	public void run(Server server, AssetManager assetManager, String params) {
		for (HostedConnection connection : server.getConnections()) {
			log.info(connection.getId() + ": " + connection.getAttribute("username"));
		}
	}
	
}
