package com.mthwate.dominion.graphical;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import com.mthwate.dominion.common.entity.EntityProperties;

/**
 * @author mthwate
 */
public class ModelUtils {

	private static AssetManager assetManager;

	public static void init(AssetManager assetManager) {
		ModelUtils.assetManager = assetManager;
	}

	public static Spatial getModel(EntityProperties entity) {
		Spatial model = assetManager.loadModel("models/" + entity.getModel() + ".j3o");
		model.setLocalScale(0.1f, 0.1f, 0.1f);
		model.setLocalRotation(getModelRotation());
		model.setQueueBucket(RenderQueue.Bucket.Transparent);
		model.setMaterial(MaterialUtils.getTexturedMaterial(entity.getTexture()));
		return model;
	}

	private static Quaternion getModelRotation() {
		Quaternion rotation = new Quaternion();
		rotation.fromAngleAxis(FastMath.PI / 2, new Vector3f(1, 0, 0));
		return rotation;
	}

}
