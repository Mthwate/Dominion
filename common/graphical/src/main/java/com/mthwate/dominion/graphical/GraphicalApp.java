package com.mthwate.dominion.graphical;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.common.tile.TproLoader;
import com.mthwate.dominion.graphical.action.ActionRegistry;
import com.mthwate.dominion.graphical.action.HomeAction;
import com.mthwate.dominion.graphical.action.ScreenshotAction;
import com.mthwate.dominion.graphical.action.WireToggleAction;
import com.mthwate.dominion.graphical.action.look.LookLeftAction;
import com.mthwate.dominion.graphical.action.look.LookNorthAction;
import com.mthwate.dominion.graphical.action.look.LookRightAction;
import com.mthwate.dominion.graphical.action.move.MoveDownAction;
import com.mthwate.dominion.graphical.action.move.MoveLeftAction;
import com.mthwate.dominion.graphical.action.move.MoveRightAction;
import com.mthwate.dominion.graphical.action.move.MoveUpAction;
import com.mthwate.dominion.graphical.action.zoom.ZoomInAction;
import com.mthwate.dominion.graphical.action.zoom.ZoomOutAction;
import com.mthwate.dominion.graphical.node.NodeHandler;
import com.mthwate.dominion.graphical.node.NodeTypeSide;
import com.mthwate.dominion.graphical.node.NodeTypeTile;
import com.mthwate.dominion.graphical.node.NodeTypeWire;
import com.mthwate.dominion.graphical.state.CompassAppState;
import com.mthwate.dominion.graphical.state.NodeAppState;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mthwate
 */
@Slf4j
public abstract class GraphicalApp extends CommonApp {

	public static final Vector3f CAM_ORIGIN = new Vector3f(0, -10, 15);

	public static final Vector3f UP = new Vector3f(0, 0, 1);

	@Override
	protected void init() {
		assetManager.registerLoader(TproLoader.class, "tpro");


		TextureUtils.init(assetManager);
		MaterialUtils.init(assetManager);
		ModelUtils.init(assetManager);


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



		ActionRegistry.init(inputManager);


		ActionRegistry.register(new MoveUpAction(cam));
		ActionRegistry.register(new MoveDownAction(cam));
		ActionRegistry.register(new MoveRightAction(cam));
		ActionRegistry.register(new MoveLeftAction(cam));


		ActionRegistry.register(new LookRightAction(cam));
		ActionRegistry.register(new LookLeftAction(cam));
		ActionRegistry.register(new LookNorthAction(cam));


		ActionRegistry.register(new HomeAction(cam));
		ActionRegistry.register(new WireToggleAction());


		ActionRegistry.register(new ZoomInAction(cam));
		ActionRegistry.register(new ZoomOutAction(cam));


		ActionRegistry.register(new ScreenshotAction(stateManager));

		log.info("Initializing common app states");

		stateManager.attach(new NodeAppState());
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
