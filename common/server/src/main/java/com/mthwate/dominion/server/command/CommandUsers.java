package com.mthwate.dominion.server.command;

import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import lombok.extern.java.Log;

/**
 * @author mthwate
 */
@Log
public class CommandUsers implements Command {
	
	@Override
	public String getName() {
		return "users";
	}

	@Override
	public void run(Server server, String params) {
		for (HostedConnection connection : server.getConnections()) {
			log.info(connection.getId() + ": " + connection.getAttribute("username"));
		}
	}
	
}