package com.mthwate.dominion.client;

import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.dominion.client.action.LeftClickAction;
import com.mthwate.dominion.common.message.LoginMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.graphical.GraphicalApp;
import com.mthwate.dominion.graphical.action.ActionRegistry;
import com.mthwate.dominion.graphical.node.NodeHandler;
import com.mthwate.dominion.graphical.node.NodeTypeCollide;
import com.mthwate.dominion.graphical.node.NodeTypeInhabitant;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author mthwate
 */
@Slf4j
public class ClientApp extends GraphicalApp {
	
	protected Client client;
	
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
			log.warn("Cannot connect to server", e);
		}

		if (client != null) {
			MessageUtils.register();
			client.addMessageListener(new ClientListener(), GZIPCompressedMessage.class);
			client.start();
			MessageUtils.send(client, new LoginMessage(NiftyUtils.getMenuStr("username")));

			initLight();

			NiftyUtils.gotoScreen("game");

			onJoin();
		}
	}

	protected void onJoin() {
		ActionRegistry.register(new LeftClickAction(client, rootNode, stateManager));
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
