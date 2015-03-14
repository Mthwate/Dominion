package com.mthwate.dominion.server.command;

import com.jme3.asset.AssetManager;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.entity.Entity;
import com.mthwate.dominion.common.entity.EproUtils;
import com.mthwate.dominion.common.message.MapMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import lombok.extern.java.Log;

/**
 * @author mthwate
 */
@Log
public class CommandSetInhabitant implements Command {
	
	@Override
	public String getName() {
		return "set-inhabitant";
	}

	@Override
	public void run(Server server, AssetManager assetManager, String params) {
		String[] split = params.split(" ");

		if (split.length == 4) {
			String sx = split[0];
			String sy = split[1];
			String name = split[2];
			String owner = split[3];

			int x = Integer.parseInt(sx);
			int y = Integer.parseInt(sy);
			
			TileStore.get(x, y).setInhabitant(new Entity(EproUtils.getProperties(name, assetManager), owner));

			for (HostedConnection connection : server.getConnections()) {
				MessageUtils.send(connection, new MapMessage(TileStore.getTiles()));
			}
		} else {
			log.severe("Error");
		}
	}
	
}
