package com.mthwate.dominion.graphical.node;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.Tile;

/**
 * @author mthwate
 */
public abstract class NodeType {

	protected static final float ELEV_MOD = 0.75f;

	public abstract boolean differ(Tile t1, Tile t2);

	public abstract void update(Node node, int x, int y, AssetManager assetManager);

	protected static void attachSpatial(Spatial spatial, Node node, int x, int y, int z, float zmod) {
		spatial.setName(coordsToName(x, y));
		spatial.setLocalTranslation(CoordUtils.getPosCartesian(x, y).setZ(z * ELEV_MOD + zmod));
		node.detachChildNamed(coordsToName(x, y));
		node.attachChild(spatial);
	}

	protected static String coordsToName(int x, int y) {
		return x + "," + y;
	}

	public abstract boolean canOptimize();
}
