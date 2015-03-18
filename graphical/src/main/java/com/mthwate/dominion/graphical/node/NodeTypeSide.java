package com.mthwate.dominion.graphical.node;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.tile.Tile;
import com.mthwate.dominion.graphical.MeshUtils;
import com.mthwate.dominion.graphical.tile.TproUtils;

/**
 * @author mthwate
 */
public class NodeTypeSide extends NodeType {

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
	public void update(Node node, int x, int y) {

		Tile tile = TileStore.get(x, y);

		if (tile.getElevation() == 0) {
			node.detachChildNamed(coordsToName(x, y));
		} else {
			Geometry geomSides = new Geometry();
			geomSides.setMesh(MeshUtils.getSide(tile.getElevation(), ELEV_MOD));
			geomSides.setMaterial(TproUtils.getMaterialSide(tile.getType()));

			attachSpatial(geomSides, node, x, y, 0, 0);
		}
	}

	@Override
	public boolean canOptimize() {
		return true;
	}

}
