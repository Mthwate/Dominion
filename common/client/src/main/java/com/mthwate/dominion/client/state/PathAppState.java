package com.mthwate.dominion.client.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.network.Client;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.mthwate.datlib.math.set.Set2i;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.Path;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.common.message.MoveMessage;
import com.mthwate.dominion.common.tile.Tile;
import com.mthwate.dominion.graphical.ClickUtils;
import com.mthwate.dominion.graphical.MaterialUtils;
import com.mthwate.dominion.graphical.MeshUtils;
import com.mthwate.dominion.graphical.highlight.HighlightColors;
import lombok.Setter;

/**
 * @author mthwate
 */
public class PathAppState extends AbstractAppState {

	private Node node = new Node();

	private Client client;

	private Path path;

	private InputManager inputManager;

	private Camera cam;

	@Setter private boolean clicked = false;

	public PathAppState(Client client, Node parent) {
		this.client = client;
		parent.attachChild(node);
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		inputManager = app.getInputManager();
		cam = app.getCamera();
	}

	@Override
	public void update(float tpf) {
		if (clicked) {
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
				node.detachAllChildren();
				if (path.isValid()) {
					MessageUtils.send(client, new MoveMessage(path));
				}
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
		g.setMaterial(MaterialUtils.getHighlightMaterial(HighlightColors.YELLOW));

		Tile tile = TileStore.get(x, y);

		float elev = tile.getElevation();

		g.setLocalTranslation(CoordUtils.getPosCartesian(x, y).setZ(elev * 0.75f + 0.002f));

		node.attachChild(g);
	}

}
