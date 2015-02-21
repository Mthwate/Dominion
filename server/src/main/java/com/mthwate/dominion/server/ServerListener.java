package com.mthwate.dominion.server;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.epro.EproUtils;
import com.mthwate.dominion.common.log.Log;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.LoginMessage;
import com.mthwate.dominion.common.message.MapMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.common.message.MoveMessage;

/**
 * @author mthwate
 */
public class ServerListener implements MessageListener<HostedConnection> {
	
	@Override
	public void messageReceived(HostedConnection connection, Message m) {
		
		if (m instanceof GZIPCompressedMessage) {
			GZIPCompressedMessage zipped = (GZIPCompressedMessage) m;
			Message msg = zipped.getMessage();
			
			if (msg instanceof LoginMessage) {
				LoginMessage loginMsg = (LoginMessage) msg;
				connection.setAttribute("username", loginMsg.getUsername());
				MessageUtils.send(connection, new MapMessage(TileStore.get()));
				
			} else if (msg instanceof MoveMessage) {
				MoveMessage moveMsg = (MoveMessage) msg;

				Set2i sourcePos = moveMsg.getSource();
				Set2i targetPos = moveMsg.getTarget();

				Tile sourceTile = TileStore.get(sourcePos);
				Tile targetTile = TileStore.get(targetPos);
				
				if (sourceTile.hasInhabitant() && !targetTile.hasInhabitant()) {
					if (sourceTile.getInhabitant().moveable) {
						targetTile.setInhabitant(sourceTile.getInhabitant());
						sourceTile.setInhabitant(null);
						MessageUtils.send(connection, new MapMessage(TileStore.get()));
					}
				}
			} else {
				Log.MESSAGING.warning("Invalid message type \"" + m.getClass() + "\" received");
			}
			
		} else {
			Log.MESSAGING.warning("Invalid message type \"" + m.getClass() + "\" received");
		}
		
	}
	
}
