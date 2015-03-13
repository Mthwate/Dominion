package com.mthwate.dominion.graphical.node;

import com.jme3.asset.AssetManager;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Node;
import com.mthwate.dominion.common.TileStore;
import lombok.extern.java.Log;

/**
 * @author mthwate
 */
@Log
public class NodeContainer {

	private Node node = new Node();

	private NodeType type;

	private Node parent;
	
	private Chunk[][] chunks;
	
	private static final int CHUNK_SIZE = 10;

	private int sizex = 0;
	
	private int sizey = 0;

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
		
		if (sizex != x || sizey != y) {
			sizex = x;
			sizey = y;
			node.detachAllChildren();
			chunks = new Chunk[getChunksForSize(x)][getChunksForSize(y)];
			for (int ix = 0; ix < getChunksForSize(x); ix++) {
				for (int iy = 0; iy < getChunksForSize(y); iy++) {
					int csizex = Math.min(x, (ix + 1) * CHUNK_SIZE) % CHUNK_SIZE;
					int csizey = Math.min(y, (iy + 1) * CHUNK_SIZE) % CHUNK_SIZE;
					csizex = csizex == 0 ? CHUNK_SIZE : csizex;
					csizey = csizey == 0 ? CHUNK_SIZE : csizey;
					chunks[ix][iy] = new Chunk(type, csizex, csizey);
					chunks[ix][iy].setParent(node);
				}
			}
		}

		if (x != 0 && y != 0) {
			for (int ix = 0; ix < chunks.length; ix++) {
				for (int iy = 0; iy < chunks[0].length; iy++) {
					chunks[ix][iy].update(assetManager, ix * CHUNK_SIZE, iy * CHUNK_SIZE);
				}
			}
		}
	}
	
	private int getChunksForSize(int i) {
		int chunks = (i - 1) / CHUNK_SIZE;
		return chunks + 1;
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