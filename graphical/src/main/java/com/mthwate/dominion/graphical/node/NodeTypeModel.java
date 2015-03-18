package com.mthwate.dominion.graphical.node;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.entity.EntityProperties;
import com.mthwate.dominion.common.tile.Tile;
import com.mthwate.dominion.graphical.ModelUtils;

/**
 * @author mthwate
 */
public class NodeTypeModel extends NodeType {

	@Override
	public boolean differ(Tile t1, Tile t2) {
		boolean differ = true;

		if (t1 != null && t2 != null) {
			if (t1.getInhabitant() != null && t2.getInhabitant() != null) {
				EntityProperties e1 = t1.getInhabitant().getType();
				EntityProperties e2 = t2.getInhabitant().getType();
				if (e1 == e2) {
					differ = false;
				} else if (e1 != null && e2 != null) {
					differ = !t1.getInhabitant().equals(t2.getInhabitant());
				}
			}
		}

		return differ;
	}

	@Override
	public void update(Node node, int x, int y, AssetManager assetManager) {
		Tile tile = TileStore.get(x, y);

		if (tile.hasInhabitant()) {
			Spatial model = ModelUtils.getModel(tile.getInhabitant().getType(), assetManager);
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
