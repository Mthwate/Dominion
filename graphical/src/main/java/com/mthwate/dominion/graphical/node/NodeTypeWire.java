package com.mthwate.dominion.graphical.node;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.graphical.MaterialUtils;
import com.mthwate.dominion.graphical.MeshUtils;

/**
 * @author mthwate
 */
public class NodeTypeWire extends NodeType {

	@Override
	public boolean differ(Tile t1, Tile t2) {
		boolean differ = true;

		if (t1 != null && t2 != null) {
			differ = t1.getElevation() != t2.getElevation();
		}

		return differ;
	}

	@Override
	public void update(Node node, int x, int y, AssetManager assetManager) {
		Tile tile = TileStore.get(x, y);

		Geometry wire = new Geometry();
		wire.setMesh(MeshUtils.getWire(tile.getElevation(), ELEV_MOD));
		wire.setMaterial(MaterialUtils.getWireMaterial(assetManager));

		attachSpatial(wire, node, x, y, tile.getElevation(), 0.002f);
	}

	@Override
	public boolean canOptimize() {
		return true;
	}

}
