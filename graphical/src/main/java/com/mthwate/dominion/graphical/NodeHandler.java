package com.mthwate.dominion.graphical;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.mthwate.dominion.common.CoordUtils;

/**
 * @author mthwate
 */
public class NodeHandler {

	private static final Node tileNode = new Node();

	private static final Node sideNode = new Node();

	private static final Node wireNode = new Node();

	private static final Node modelNode = new Node();

	private static final Node collisionNode = new Node();

	public static void init(Node rootNode) {
		rootNode.attachChild(tileNode);
		rootNode.attachChild(sideNode);
		rootNode.attachChild(wireNode);
		rootNode.attachChild(modelNode);
	}

	@Deprecated
	public static void attach(Geometry tile, Geometry side, Geometry wire, Spatial model, int x, int y, float z) {
		attachSpatial(tile, tileNode, x, y, z);

		Geometry collide = new Geometry();
		collide.setMesh(tile.getMesh());
		attachSpatial(collide, collisionNode, x, y, z);

		attachSpatial(wire, wireNode, x, y, z + 0.002f);

		if (side != null) {
			attachSpatial(side, sideNode, x, y, 0);
		}

		if (model != null) {
			attachSpatial(model, modelNode, x, y, z + 0.004f);
		}
	}

	@Deprecated
	public static void detach(int x, int y) {
		tileNode.detachChildNamed(coordsToName(x, y));
		wireNode.detachChildNamed(coordsToName(x, y));
		sideNode.detachChildNamed(coordsToName(x, y));
		modelNode.detachChildNamed(coordsToName(x, y));
		collisionNode.detachChildNamed(coordsToName(x, y));
	}

	@Deprecated
	private static String coordsToName(int x, int y) {
		return x + "," + y;
	}

	@Deprecated
	private static void attachSpatial(Spatial spatial, Node node, int x, int y, float z) {
		spatial.setName(coordsToName(x, y));
		spatial.setLocalTranslation(CoordUtils.getPosCartesian(x, y).setZ(z));
		node.attachChild(spatial);
	}

	@Deprecated
	public static void clear() {
		tileNode.detachAllChildren();
		sideNode.detachAllChildren();
		wireNode.detachAllChildren();
		modelNode.detachAllChildren();
		collisionNode.detachAllChildren();
	}

	public static void collide(Ray ray, CollisionResults results) {
		collisionNode.collideWith(ray, results);
	}

	public static void toggleWire(Node rootNode) {
		if (wireNode.getParent() == null) {
			rootNode.attachChild(wireNode);
		} else {
			wireNode.removeFromParent();
		}
	}

}
