package com.mthwate.dominion.graphical;

import com.jme3.scene.Node;
import com.mthwate.dominion.common.TileStore;

/**
 * @author mthwate
 */
public class NodeContainer {

	private Node node = new Node();

	private Node[][] nodes;

	private static final int SIZE = 10;

	public NodeContainer() {
		int x = TileStore.sizeX();
		int y = TileStore.sizeY();

		int sizex = x / SIZE;
		int sizey = y / SIZE;

		if (x % SIZE != 0) {
			sizex++;
		}

		if (y % SIZE != 0) {
			sizey++;
		}

		nodes = new Node[x][y];
	}



}