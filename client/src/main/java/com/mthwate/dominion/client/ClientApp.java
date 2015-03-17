package com.mthwate.dominion.client;

import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.message.GZIPCompressedMessage;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.Path;
import com.mthwate.dominion.common.tile.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.LoginMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.common.message.MoveMessage;
import com.mthwate.dominion.graphical.*;
import com.mthwate.dominion.graphical.highlight.HighlightColors;
import com.mthwate.dominion.graphical.node.NodeHandler;
import com.mthwate.dominion.graphical.node.NodeTypeCollide;
import com.mthwate.dominion.graphical.node.NodeTypeModel;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.logging.Level;

/**
 * @author mthwate
 */
@Log
public class ClientApp extends GraphicalApp {
	
	private Client client;

	private Path path;

	@Deprecated
	private Node highlightNode = new Node();
	
	@Override
	protected void init() {
		
		super.init();

		NiftyUtils.init(this);

		NodeHandler.init("model", new NodeTypeModel(), rootNode);
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
		}
	}

	private void highlight() {
		if (keyHandler.isPressed(KeyControl.CLICK)) {
			Set2i pos = ClickUtils.clickCollisionPos(inputManager, cam);
			if (pos != null) {
				if (path == null) {
					path = new Path(pos);
					addHighlight(pos);
				} else {
					Set2i last = path.getLast();
					if (!last.equals(pos) && CoordUtils.isAdjacentCartesian(pos, last)) {
						path.add(pos);
						addHighlight(pos);
					}
				}
			}
		} else {
			if (path != null) {
				highlightNode.detachAllChildren();
				MessageUtils.send(client, new MoveMessage(path));
				path = null;
			}
		}
	}

	private void addHighlight(Set2i pos) {
		int x = pos.getX();
		int y = pos.getY();

		Geometry g = new Geometry("selected");
		g.setMesh(MeshUtils.getTile());
		g.setQueueBucket(RenderQueue.Bucket.Transparent);
		g.setMaterial(MaterialUtils.getHighlightMaterial(HighlightColors.YELLOW, assetManager));

		Tile tile = TileStore.get(x, y);

		float elev = tile.getElevation();

		g.setLocalTranslation(CoordUtils.getPosCartesian(x, y).setZ(elev * 0.75f + 0.002f));

		highlightNode.attachChild(g);
	}
	
	@Override
	public void simpleUpdate(float tpf) {

		if (NiftyUtils.canJoin()) {
			NiftyUtils.setJoin(false);
			join();
		}

		if (client != null) {
			highlight();
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
