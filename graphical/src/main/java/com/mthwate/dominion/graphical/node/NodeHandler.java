package com.mthwate.dominion.graphical.node;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.scene.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class NodeHandler {

	private static final Map<String, NodeContainer> nodes = new HashMap<>();

	public static void init(String name, NodeType type, Node parent) {
		NodeContainer node = new NodeContainer(type);

		if (parent != null) {
			node.setParent(parent);
		}

		nodes.put(name, node);
	}

	@Deprecated
	public static void init(Node rootNode) {
		init("tile", new NodeTypeTile(), rootNode);
		init("side", new NodeTypeSide(), rootNode);
		init("wire", new NodeTypeWire(), rootNode);
		init("model", new NodeTypeModel(), rootNode);
		init("collision", new NodeTypeCollide(), null);
	}

	public static void update(AssetManager assetManager) {
		for (NodeContainer node : nodes.values()) {
			node.update(assetManager);
		}
	}

	public static void collide(Ray ray, CollisionResults results) {
		nodes.get("collision").collide(ray, results);
	}

	public static void toggleWire() {
		nodes.get("wire").toggle();
	}

}
