package com.mthwate.dominion.graphical.node;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.mthwate.datlib.math.Set2i;
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
		return (!t1.getType().equals(t2.getType())) || (t1.getElevation() != t2.getElevation());
	}

	@Override
	public void update(Node node, int x, int y) {

		Tile tile = TileStore.get(x, y);

		if (tile.getElevation() == 0) {
			node.detachChildNamed(coordsToName(x, y));
		} else {
			Geometry geomSides = new Geometry();
			geomSides.setMesh(MeshUtils.getSide(tile.getElevation(), ELEV_MOD));
			geomSides.setMaterial(TproUtils.getMaterialSide(tile.getType(), new Set2i(x, y)));

			attachSpatial(geomSides, node, x, y, 0, 0);
		}
	}

	@Override
	public boolean canOptimize() {
		return true;
	}

}
