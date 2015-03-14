package com.mthwate.dominion.server.command;

import com.jme3.asset.AssetManager;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.MapMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import lombok.extern.java.Log;

/**
 * @author mthwate
 */
@Log
public class CommandSetTile implements Command {
	
	@Override
	public String getName() {
		return "set-tile";
	}

	@Override
	public void run(Server server, AssetManager assetManager, String params) {
		String[] split = params.split(" ");
		
		if (split.length == 3) {
			String sx = split[0];
			String sy = split[1];
			String name = split[2];

			int x = Integer.parseInt(sx);
			int y = Integer.parseInt(sy);

			TileStore.get(x, y).setType(name);

			for (HostedConnection connection : server.getConnections()) {
				MessageUtils.send(connection, new MapMessage(TileStore.getTiles()));
			}
		} else {
			log.severe("Error");
		}
		
	}
	
}
