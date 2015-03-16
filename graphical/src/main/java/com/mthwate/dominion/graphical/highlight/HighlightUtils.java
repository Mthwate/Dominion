package com.mthwate.dominion.graphical.highlight;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.graphical.MaterialUtils;
import com.mthwate.dominion.graphical.MeshUtils;

/**
 * @author mthwate
 */
public class HighlightUtils {

	public static void highlightTile(int x, int y, ColorRGBA color, Node node, AssetManager assetManager) {
		Geometry g = new Geometry();
		g.setMesh(MeshUtils.getTile());
		g.setQueueBucket(RenderQueue.Bucket.Transparent);
		g.setMaterial(MaterialUtils.getHighlightMaterial(color, assetManager));

		Tile tile = TileStore.get(x, y);

		float elev = tile.getElevation();

		g.setLocalTranslation(CoordUtils.getPosCartesian(x, y).setZ(elev * 0.75f + 0.002f));
		node.attachChild(g);
	}

}
