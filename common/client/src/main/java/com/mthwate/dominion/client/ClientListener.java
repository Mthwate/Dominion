package com.mthwate.dominion.client;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.MapMessage;
import com.mthwate.dominion.common.message.TileMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mthwate
 */
@Slf4j
public class ClientListener implements MessageListener<Client> {
	
	@Override
	public void messageReceived(Client source, Message m) {

		if (m instanceof GZIPCompressedMessage) {
			GZIPCompressedMessage zipped = (GZIPCompressedMessage) m;
			Message msg = zipped.getMessage();

			log.info("Received message of type " + msg.getClass());
			
			if (msg instanceof TileMessage) {
				TileMessage tileMsg = (TileMessage) msg;

				TileStore.set(tileMsg.getTile(), tileMsg.getPos());
			} else if (msg instanceof MapMessage) {
				MapMessage mapMsg = (MapMessage) msg;
				
				TileStore.setTiles(mapMsg.getMap());
			}

		} else {
			log.warn("Invalid message type {} received", m.getClass());
		}
		
	}
	
}
