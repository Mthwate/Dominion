package com.mthwate.dominion.graphical.node;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import jme3tools.optimize.GeometryBatchFactory;

/**
 * @author mthwate
 */
public class Chunk {
	
	private Node node = new Node();

	private NodeType type;
	
	private Tile[][] tiles;

	private int sizex;
	
	private int sizey;
	
	public Chunk(NodeType type, int sizex, int sizey) {
		this.type = type;
		tiles = new Tile[sizex][sizey];
		this.sizex = sizex;
		this.sizey = sizey;
	}
	
	public void setParent(Node parent) {
		parent.attachChild(node);
	}

	public void update(AssetManager assetManager, int x, int y) {
		boolean update = false;
		
		for (int ix = 0; ix < sizex; ix++) {
			for (int iy = 0; iy < sizey; iy++) {
				if (type.differ(tiles[ix][iy], TileStore.get(ix + x, iy + y))) {
					update = true;
					tiles[ix][iy] = TileStore.get(ix + x, iy + y).clone();
					if (!type.canOptimize()) {
						type.update(node, ix + x, iy + y, assetManager);
					}
				}
			}
		}
		
		if (update && type.canOptimize()) {
			node.detachAllChildren();
			for (int ix = 0; ix < sizex; ix++) {
				for (int iy = 0; iy < sizey; iy++) {
					type.update(node, ix + x, iy + y, assetManager);
				}
			}
			GeometryBatchFactory.optimize(node);
		}
	}
	
}
