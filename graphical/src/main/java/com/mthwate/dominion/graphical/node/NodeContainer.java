package com.mthwate.dominion.graphical.node;

import com.jme3.asset.AssetManager;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Node;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;

import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class NodeContainer {

	private static final Logger log = Logger.getLogger(NodeContainer.class.getName());

	private Node node = new Node();

	private Tile[][] tiles;

	private NodeType type;

	private Node parent;

	public NodeContainer(NodeType type) {
		this.type = type;
	}

	public void setParent(Node parent) {
		this.parent = parent;
		parent.attachChild(node);
	}

	public void update(AssetManager assetManager) {

		int x = TileStore.sizeX();
		int y = TileStore.sizeY();

		if (tiles == null || tiles.length != x || tiles[0].length != y) {
			tiles = new Tile[x][y];
			node.detachAllChildren();
		}

		for (int ix = 0; ix < x; ix++) {
			for (int iy = 0; iy < y; iy++) {
				if (type.differ(tiles[ix][iy], TileStore.get(ix, iy))) {
					tiles[ix][iy] = TileStore.get(ix, iy).clone();
					type.update(node, ix, iy, assetManager);
				}
			}
		}
	}

	public void collide(Collidable collidable, CollisionResults results) {
		node.collideWith(collidable, results);
	}

	public void toggle() {
		if (node.getParent() == null) {
			parent.attachChild(node);
		} else {
			node.removeFromParent();
		}
	}

}