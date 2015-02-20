package com.mthwate.dominion.client;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.dominion.common.log.Log;
import com.mthwate.dominion.graphical.GraphicalApp;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.common.message.LoginMessage;

import java.io.IOException;
import java.util.Random;

/**
 * @author mthwate
 */
public class ClientApp extends GraphicalApp {
	
	private Client client;
	
	private static boolean worldChange = false;
	
	public static void triggerWorldChange() {
		worldChange = true;
	}
	
	@Override
	protected void init() {
		
		super.init();
		
		try {
			client = Network.connectToServer("localhost", 6969);
		} catch (IOException e) {
			e.printStackTrace();
			//TODO log this
		}
		
		if (client != null) {
			MessageUtils.register();
			client.addMessageListener(new ClientListener(), GZIPCompressedMessage.class);
			client.start();
			Random rand = new Random();
			MessageUtils.send(client, new LoginMessage("Tester" + rand.nextInt(10)));
		}

		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1));
		rootNode.addLight(al);

		DirectionalLight dl = new DirectionalLight();
		dl.setDirection(new Vector3f(1, 0, -1));
		dl.setColor(ColorRGBA.White.mult(1));
		rootNode.addLight(dl);
	}
	
	@Override
	public void simpleUpdate(float tpf) {

		zoom(tpf);
		move(tpf);
		listenWire();
		
		if (worldChange) {
			worldChange = false;
			mapUpdate();
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
