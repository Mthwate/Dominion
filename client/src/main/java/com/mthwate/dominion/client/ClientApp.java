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
import com.mthwate.dominion.common.Path;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.LoginMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.common.message.MoveMessage;
import com.mthwate.dominion.graphical.*;
import com.mthwate.dominion.graphical.node.NodeHandler;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class ClientApp extends GraphicalApp {

	private static final Logger log = Logger.getLogger(ClientApp.class.getName());
	
	private Client client;
	
	private String ip;
	
	public ClientApp(String ip) {
		super();
		this.ip = ip;
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
					g.setMesh(MeshUtils.getTile());
					g.setQueueBucket(RenderQueue.Bucket.Transparent);
					g.setMaterial(MaterialUtils.getHighlightMaterial(Highlighter.YELLOW, assetManager));

					Tile tile = TileStore.get(x, y);

					float elev = tile.getElevation();

					g.setLocalTranslation(CoordUtils.getPosCartesian(x, y).setZ(elev * 0.75f + 0.002f));

					highlightNode.attachChild(g);
					
					source = new Set2i(x, y);
				} else {
					Set2i dest = new Set2i(x, y);
					if (CoordUtils.isAdjacentCartesian(source, dest)) {
						Path path = new Path(source);
						path.add(dest);
						MessageUtils.send(client, new MoveMessage(path));
					}
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
		screenshot();
		
		NodeHandler.update(assetManager);
	}

	@Override
	protected void close() {
		log.info("Stopping application");
		if (client != null) {
			client.close();
		}
	}
	
}
