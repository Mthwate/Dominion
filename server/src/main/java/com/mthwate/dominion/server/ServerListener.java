package com.mthwate.dominion.server;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.dominion.common.log.Log;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.LoginMessage;
import com.mthwate.dominion.common.message.MapMessage;
import com.mthwate.dominion.common.message.MessageUtils;

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
			}
			
		} else {
			Log.MESSAGING.warning("Invalid message type \"" + m.getClass() + "\" received");
		}
		
	}
	
}
