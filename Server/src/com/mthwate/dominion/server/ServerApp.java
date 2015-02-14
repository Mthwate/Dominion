package com.mthwate.dominion.server;

import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.common.Log;

import java.io.IOException;

/**
 * @author mthwate
 */
public class ServerApp extends CommonApp {
	
	private Server server;
	
	@Override
	protected void init() {
		
		try {
			server = Network.createServer(6969);
		} catch (IOException e) {
			e.printStackTrace();
			//TODO log this
		}

		if (server != null) {
			server.addMessageListener(new ServerListener(), GZIPCompressedMessage.class);
			server.start();
		}

	}

	@Override
	protected void close() {
		Log.MAIN.info("Stopping server");
		if (server != null) {
			server.close();
		}
	}
	
}
