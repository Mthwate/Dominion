package com.mthwate.dominion.graphical.node;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.RandUtils;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.entity.EproUtils;
import com.mthwate.dominion.common.tile.Tile;
import com.mthwate.dominion.graphical.ModelUtils;

/**
 * @author mthwate
 */
public class NodeTypeModel extends NodeType {

	@Override
	public boolean differ(Tile t1, Tile t2) {
		return (!t1.getType().equals(t2.getType())) || (t1.getElevation() != t2.getElevation() || (t1.getRotation() != t2.getRotation()));
	}

	@Override
	public void update(Node node, int x, int y) {
		Tile tile = TileStore.get(x, y);
		String modelName = tile.getType().getModel();

		if (modelName != null) {
			Spatial model = ModelUtils.getModel(EproUtils.getProperties(modelName));

			Quaternion rotation = new Quaternion();

			int r = tile.getRotation();

			if (r == -1) {
				r = RandUtils.randInt(new Set2i(x, y), 6);
			}

			float angle = FastMath.PI / 3 * r;
			rotation.fromAngleAxis(angle, new Vector3f(0, 1, 0));
			model.rotate(rotation);

			attachSpatial(model, node, x, y, tile.getElevation(), 0.004f);
		} else {
			node.detachChildNamed(coordsToName(x, y));
		}
	}

	@Override
	public boolean canOptimize() {
		return true;
	}

}