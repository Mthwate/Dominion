package com.mthwate.dominion.graphical;

import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextureTest {

	@Test
	public void isPowTwo() throws IOException {
		File dir = new File("src/main/resources/textures");
		for (File file : dir.listFiles()) {
			BufferedImage img = ImageIO.read(file);
			int width = img.getWidth();
			int height = img.getHeight();
			Assert.assertTrue((width & -width) == width);//is pow of 2
			Assert.assertTrue((height & -height) == height);//is pow of 2
		}
	}
}