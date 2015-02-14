package com.mthwate.dominion.client;

import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.common.Log;

import java.io.IOException;

/**
 * @author mthwate
 */
public class ClientApp extends CommonApp {
	
	private Client client;

	@Override
	protected void init() {
		
		try {
			client = Network.connectToServer("localhost", 6969);
		} catch (IOException e) {
			e.printStackTrace();
			//TODO log this
		}

		if (client != null) {
			client.addMessageListener(new ClientListener(), GZIPCompressedMessage.class);
			client.start();
		}
	}

	@Override
	protected void close() {
		Log.MAIN.info("Stopping application");
		if (client != null) {
			client.close();
		}
	}
	
}
