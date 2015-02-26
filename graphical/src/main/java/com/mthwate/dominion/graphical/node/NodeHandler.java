package com.mthwate.dominion.graphical.node;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.scene.Node;

/**
 * @author mthwate
 */
public class NodeHandler {

	private static final NodeContainer tileNode = new NodeContainer(new NodeTypeTile());

	private static final NodeContainer sideNode = new NodeContainer(new NodeTypeSide());

	private static final NodeContainer wireNode = new NodeContainer(new NodeTypeWire());

	private static final NodeContainer modelNode = new NodeContainer(new NodeTypeModel());

	private static final NodeContainer collisionNode = new NodeContainer(new NodeTypeTile());

	public static void init(Node rootNode) {
		tileNode.setParent(rootNode);
		sideNode.setParent(rootNode);
		wireNode.setParent(rootNode);
		modelNode.setParent(rootNode);
	}

	public static void update(AssetManager assetManager) {
		tileNode.update(assetManager);
		sideNode.update(assetManager);
		wireNode.update(assetManager);
		modelNode.update(assetManager);
		collisionNode.update(assetManager);
	}

	public static void collide(Ray ray, CollisionResults results) {
		collisionNode.collide(ray, results);
	}

	public static void toggleWire() {
		wireNode.toggle();
	}

}
