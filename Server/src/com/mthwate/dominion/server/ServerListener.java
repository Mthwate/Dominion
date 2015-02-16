package com.mthwate.dominion.server;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.datlib.TimeUtils;
import com.mthwate.datlib.Vector2i;
import com.mthwate.dominion.common.Log;
import com.mthwate.dominion.common.MessageUtils;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.LoginMessage;
import com.mthwate.dominion.common.message.MapMessage;
import com.mthwate.dominion.common.message.TileMessage;

/**
 * @author mthwate
 */
public class ServerListener implements MessageListener<HostedConnection> {
	
	@Override
	public void messageReceived(HostedConnection source, Message m) {
		
		if (m instanceof GZIPCompressedMessage) {
			GZIPCompressedMessage zipped = (GZIPCompressedMessage) m;
			Message msg = zipped.getMessage();
			
			Log.TMP.info("Received message of type " + msg.getClass());
			
			if (msg instanceof LoginMessage) {
				MessageUtils.send(source, new MapMessage(TileStore.get()));
				TimeUtils.sleep(10000);
				MessageUtils.send(source, new TileMessage(new Tile()));
			}
			
		} else {
			Log.MESSAGING.warning("Invalid message type \"" + m.getClass() + "\" received");
		}
		
	}
	
}
