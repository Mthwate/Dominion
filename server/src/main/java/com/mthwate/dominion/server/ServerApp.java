package com.mthwate.dominion.server;

import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.common.log.Log;
import com.mthwate.dominion.common.MessageUtils;
import com.mthwate.dominion.common.SaveUtils;
import com.mthwate.dominion.common.TileStore;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author mthwate
 */
public class ServerApp extends CommonApp {
	
	private Server server;

	private File saveFile = new File("map.dwm");
	
	@Override
	protected void init() {
		
		try {
			server = Network.createServer(6969);
		} catch (IOException e) {
			e.printStackTrace();
			//TODO log this
		}

		if (server != null) {
			TileStore.set(SaveUtils.load(saveFile));
			MessageUtils.register();
			server.addMessageListener(new ServerListener(), GZIPCompressedMessage.class);
			server.start();
		}

		Scanner reader = new Scanner(System.in);
		
		while (true) {
			String line = reader.nextLine();
			System.out.println("Read: " + line);
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
