package com.mthwate.dominion.server.messagehandler;

import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import com.mthwate.dominion.common.Path;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.MoveMessage;
import com.mthwate.dominion.common.tile.Tile;
import com.mthwate.dominion.server.ConnectionUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mthwate
 */
@Slf4j
public class MoveMessageHandler implements MessageHandler<MoveMessage> {

	@Override
	public void onMessage(Server server, HostedConnection connection, MoveMessage message) {
		Path path = message.getPath();

		if (path.isValid()) {
			Tile sourceTile = TileStore.get(path.getCurrent());

			if (sourceTile.hasInhabitant()) {
				if (sourceTile.getInhabitant().getOwner().equals(ConnectionUtils.getUsername(connection)) && sourceTile.getInhabitant().getType().isMoveable()) {
					sourceTile.getInhabitant().setPath(path);
				}
			}
		} else {
			log.warn("Invalid path received from {} (id {})", ConnectionUtils.getUsername(connection), connection.getId());
		}
	}

}
