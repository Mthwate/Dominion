package com.mthwate.dominion.client;

import com.jme3.collision.CollisionResult;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.message.GZIPCompressedMessage;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.LoginMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.common.message.MoveMessage;
import com.mthwate.dominion.graphical.GraphicalApp;
import com.mthwate.dominion.graphical.KeyControl;
import com.mthwate.dominion.graphical.MaterialUtils;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class ClientApp extends GraphicalApp {

	private static final Logger log = Logger.getLogger(ClientApp.class.getName());
	
	private Client client;
	
	private static boolean worldChange = false;
	
	private String ip;
	
	public ClientApp(String ip) {
		super();
		this.ip = ip;
	}

	public static void triggerWorldChange() {
		worldChange = true;
	}
	
	@Override
	protected void init() {
		
		super.init();
		
		try {
			client = Network.connectToServer(ip, 6969);
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

		initLight();
	}

	private void initLight() {
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1));
		rootNode.addLight(al);

		DirectionalLight dl = new DirectionalLight();
		dl.setDirection(new Vector3f(1, 0, -1));
		dl.setColor(ColorRGBA.White.mult(1));
		rootNode.addLight(dl);
	}

	private Set2i source;
	
	private void highlight() {
		if (keyHandler.isPressed(KeyControl.CLICK)) {
			keyHandler.onAction(KeyControl.CLICK.getName(), false, 0);
			CollisionResult result = clickCollisions().getClosestCollision();
			if (result != null) {
				Geometry geom = result.getGeometry();
				String name = geom.getName();
				String[] split = name.split(",");
				int x = Integer.parseInt(split[0]);
				int y = Integer.parseInt(split[1]);

				
				highlightNode.detachAllChildren();
				
				if (source == null) {
					Geometry g = new Geometry("selected");
					g.setMesh(hex);
					g.setQueueBucket(RenderQueue.Bucket.Transparent);
					g.setMaterial(MaterialUtils.getHighlightMaterial(assetManager));

					Tile tile = TileStore.get(x, y);

					float elev = tile.getElevation();

					g.setLocalTranslation(CoordUtils.getPosCartesian(x, y).setZ(elev * 0.75f + 0.002f));

					highlightNode.attachChild(g);
					
					source = new Set2i(x, y);
				} else {
					MessageUtils.send(client, new MoveMessage(source, new Set2i(x, y)));
					source = null;
				}
			}
		}
	}
	
	@Override
	public void simpleUpdate(float tpf) {

		zoom(tpf);
		move(tpf);
		listenWire();
		highlight();
		
		if (worldChange) {
			worldChange = false;
			mapUpdate();
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
