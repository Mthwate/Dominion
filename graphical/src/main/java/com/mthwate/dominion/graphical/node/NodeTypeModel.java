package com.mthwate.dominion.graphical.node;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.entity.EproUtils;
import com.mthwate.dominion.common.tile.Tile;
import com.mthwate.dominion.common.tile.TileProperties;
import com.mthwate.dominion.graphical.MeshUtils;
import com.mthwate.dominion.graphical.ModelUtils;
import com.mthwate.dominion.graphical.tile.TproUtils;

/**
 * @author mthwate
 */
public class NodeTypeModel extends NodeType {

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
		String modelName = tile.getType().getModel();

		if (modelName != null) {
			Spatial model = ModelUtils.getModel(EproUtils.getProperties(modelName));
			attachSpatial(model, node, x, y, tile.getElevation(), 0.004f);
		} else {
			node.detachChildNamed(coordsToName(x, y));
		}
	}

	@Override
	public boolean canOptimize() {
		return false;
	}

}