package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;

import java.util.Random;

/**
 * @author mthwate
 */
public class CompassAppState extends GraphicalAppState {

	private Node node = new Node();

	private AssetManager assetManager;

	private Camera cam;

	private int width;

	private int height;

	private float size;

	Picture pic = new Picture("compass");

	private float mod = 0;

	private Vector3f prevLoc = new Vector3f();

	private Random rand = new Random();

	public CompassAppState(Node parentNode, AppSettings settings) {
		parentNode.attachChild(node);
		width = settings.getWidth();
		height = settings.getHeight();
		size = (width + height) / 20f;
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		assetManager = gapp.getAssetManager();
		cam = gapp.getCamera();


		pic.setImage(assetManager, "textures/compass.png", true);
		pic.setWidth(size);
		pic.setHeight(size);
		node.setLocalTranslation(width - (size * 0.75f), height - (size * 0.75f), 0);

		node.attachChild(pic);

		pic.center();
	}

	@Override
	public void update(float tpf) {
		Vector3f direction3d = cam.getDirection().normalize();
		Vector2f direction2d = new Vector2f(-direction3d.getX(), direction3d.getY());
		Quaternion rotation = new Quaternion();



		Vector3f location = cam.getLocation();
		float diff = prevLoc.distance(location);
		prevLoc = cam.getLocation().clone();

		if (diff == 0 && mod != 0) {
			mod = 0;
		} else {
			mod += (rand.nextInt(5) - 2) * diff * tpf;
			mod = Math.min(0.1f, mod);
			mod = Math.max(-0.1f, mod);
		}

		rotation.fromAngles(0, 0, new Vector2f(mod, 1).angleBetween(direction2d));




		node.setLocalRotation(rotation);
	}

}
