package com.mthwate.dominion.converter;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import java.io.File;
import java.io.IOException;

/**
 * @author mthwate
 */
public class ConverterApp extends SimpleApplication {
	
	@Override
	public void simpleInitApp() {

		File folder = new File("Common/resouces/obj");
		
		for (String name : folder.list()) {

			System.out.println(name);
			
			File outFile = new File("Common/resouces/j3o/" + name + ".j3o");

			Spatial model = assetManager.loadModel("obj/" + name + ".obj");

			BinaryExporter exporter = BinaryExporter.getInstance();

			try {
				exporter.save(model, outFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
