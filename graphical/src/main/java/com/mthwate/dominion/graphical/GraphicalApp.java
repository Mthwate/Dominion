package com.mthwate.dominion.graphical;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.common.tile.TproLoader;
import com.mthwate.dominion.graphical.node.NodeHandler;
import com.mthwate.dominion.graphical.node.NodeTypeSide;
import com.mthwate.dominion.graphical.node.NodeTypeTile;
import com.mthwate.dominion.graphical.node.NodeTypeWire;
import com.mthwate.dominion.graphical.state.*;
import lombok.Getter;
import lombok.extern.java.Log;

/**
 * @author mthwate
 */
@Log
public abstract class GraphicalApp extends CommonApp {

	@Getter protected KeyHandler keyHandler;

	public static final Vector3f CAM_ORIGIN = new Vector3f(0, -10, 15);

	public static final Vector3f UP = new Vector3f(0, 0, 1);

	@Override
	protected void init() {
		assetManager.registerLoader(TproLoader.class, "tpro");


		TextureUtils.init(assetManager);
		MaterialUtils.init(assetManager);
		ModelUtils.init(assetManager);


		keyHandler = new KeyHandler(inputManager);


		log.info("Setting up nodes");
		NodeHandler.init("tile", new NodeTypeTile(), rootNode);
		NodeHandler.init("side", new NodeTypeSide(), rootNode);
		NodeHandler.init("wire", new NodeTypeWire(), rootNode);
		

		log.info("Disabling the default fly camera");
		flyCam.setEnabled(false);


		log.info("Setting initial location");
		cam.setLocation(CAM_ORIGIN);


		log.info("Setting initial camera direction");
		cam.lookAtDirection(new Vector3f(0, 0.5f, -1), new Vector3f(0, 0, 1));


		log.info("Setting background color");
		viewPort.setBackgroundColor(ColorRGBA.Black);


		log.info("Initializing common app states");
		stateManager.attach(new MoveAppState());
		stateManager.attach(new ZoomAppState());
		stateManager.attach(new LookAppState());
		stateManager.attach(new HomeAppState());
		stateManager.attach(new WireAppState());
		stateManager.attach(new NodeAppState());
		stateManager.attach(new ScreenshotAppState());
		stateManager.attach(new CompassAppState(guiNode, settings));
	}

	protected void initLight() {
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1));
		rootNode.addLight(al);

		DirectionalLight dl = new DirectionalLight();
		dl.setDirection(new Vector3f(1, 0, -1));
		dl.setColor(ColorRGBA.White.mult(1));
		rootNode.addLight(dl);
	}

}
