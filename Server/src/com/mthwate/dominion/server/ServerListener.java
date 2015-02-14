package com.mthwate.dominion.server;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.dominion.common.Log;

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
			
		} else {
			Log.MESSAGING.warning("Invalid message type \"" + m.getClass() + "\" received");
		}
		
	}
	
}
