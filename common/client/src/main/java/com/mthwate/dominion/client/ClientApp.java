package com.mthwate.dominion.client;

import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.message.GZIPCompressedMessage;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.client.state.PathAppState;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.Path;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.LoginMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.common.message.MoveMessage;
import com.mthwate.dominion.common.tile.Tile;
import com.mthwate.dominion.graphical.*;
import com.mthwate.dominion.graphical.highlight.HighlightColors;
import com.mthwate.dominion.graphical.node.NodeHandler;
import com.mthwate.dominion.graphical.node.NodeTypeCollide;
import com.mthwate.dominion.graphical.node.NodeTypeInhabitant;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.logging.Level;

/**
 * @author mthwate
 */
@Log
public class ClientApp extends GraphicalApp {
	
	private Client client;
	
	@Override
	protected void init() {
		
		super.init();

		NiftyUtils.init(this);

		NodeHandler.init("inhabitant", new NodeTypeInhabitant(), rootNode);
		NodeHandler.init("collision", new NodeTypeCollide(), null);
	}

	private void join() {

		try {
			client = Network.connectToServer(NiftyUtils.getMenuStr("ip"), 6969);
		} catch (IOException e) {
			log.log(Level.WARNING, "Cannot connect to server", e);
		}

		if (client != null) {
			MessageUtils.register();
			client.addMessageListener(new ClientListener(), GZIPCompressedMessage.class);
			client.start();
			MessageUtils.send(client, new LoginMessage(NiftyUtils.getMenuStr("username")));

			initLight();

			NiftyUtils.gotoScreen("game");

			stateManager.attach(new PathAppState(client, rootNode));
		}
	}
	
	@Override
	public void simpleUpdate(float tpf) {

		if (NiftyUtils.canJoin()) {
			NiftyUtils.setJoin(false);
			join();
		}

	}

	@Override
	protected void close() {
		log.info("Stopping application");
		if (client != null) {
			client.close();
		}
	}
	
}