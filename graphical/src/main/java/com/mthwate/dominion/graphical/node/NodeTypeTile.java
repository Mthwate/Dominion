package com.mthwate.dominion.graphical.node;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.tile.Tile;
import com.mthwate.dominion.graphical.MeshUtils;
import com.mthwate.dominion.graphical.tile.TproUtils;
import lombok.extern.java.Log;

/**
 * @author mthwate
 */
@Log
public class NodeTypeTile extends NodeType {

	@Override
	public boolean differ(Tile t1, Tile t2) {
		boolean differ = true;

		if (t1 != null && t2 != null) {
			differ = !t1.getType().equals(t2.getType());
			differ = differ || t1.getElevation() != t2.getElevation();
		}

		return differ;
	}

	@Override
	public void update(Node node, int x, int y, AssetManager assetManager) {
		Tile tile = TileStore.get(x, y);

		Geometry geom = new Geometry();
		geom.setMesh(MeshUtils.getTile());
		geom.setMaterial(TproUtils.getMaterialFace(tile.getType(), assetManager));

		attachSpatial(geom, node, x, y, tile.getElevation(), 0);
	}

	@Override
	public boolean canOptimize() {
		return true;
	}

}
