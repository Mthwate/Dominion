package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.post.SceneProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.system.JmeSystem;
import com.jme3.texture.FrameBuffer;
import com.jme3.util.BufferUtils;
import com.mthwate.datlib.IOUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author mthwate
 */
@Slf4j
public class ScreenshotAppState extends AbstractAppState implements SceneProcessor {

	private Renderer renderer;

	private ByteBuffer outBuf;

	private int shotIndex = 0;

	private int width;

	private int height;

	private boolean capture = false;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		app.getViewPort().addProcessor(this);
	}

	@Override
	public void initialize(RenderManager rm, ViewPort vp) {
		renderer = rm.getRenderer();
		reshape(vp, vp.getCamera().getWidth(), vp.getCamera().getHeight());
	}

	@Override
	public boolean isInitialized() {
		return super.isInitialized() && renderer != null;
	}

	@Override
	public void reshape(ViewPort vp, int w, int h) {
		outBuf = BufferUtils.createByteBuffer(w * h * 4);
		width = w;
		height = h;
	}

	@Override
	public void preFrame(float tpf) {}

	@Override
	public void postQueue(RenderQueue rq) {}

	@Override
	public void postFrame(FrameBuffer out) {
		if (capture) {
			capture = false;

			renderer.readFrameBuffer(out, outBuf);

			File file = new File("Screenshot-" + (shotIndex++) + ".png");

			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				JmeSystem.writeImageFile(fos, "png", outBuf, width, height);
			} catch (IOException e) {
				log.error("Failed to save screenshot", e);
			} finally {
				IOUtils.close(fos);
			}
		}
	}

	public void takeScreenshot() {
		capture = true;
	}

}