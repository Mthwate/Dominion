package com.mthwate.dominion.common.log;

import com.jme3.util.JmeFormatter;
import com.mthwate.datlib.DualOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author mthwate
 */
public class Log {
	
	public static void init() {
		Logger root = Logger.getLogger("");

		for (Handler handler : root.getHandlers()) {
			root.removeHandler(handler);
		}

		root.addHandler(new LiveStreamHandler(getOutputStream(), new StandardFormatter()));

		root.setLevel(Level.ALL);
	}
	
	private static OutputStream getOutputStream() {
		
		File file = new File("log.txt");
		
		if (file.exists()) {
			file.delete();
		}

		FileOutputStream fos = null;
		
		try {
			file.createNewFile();
			fos = new FileOutputStream(file);
		} catch (IOException e) {}
		
		OutputStream out = System.out;
		
		if (fos != null) {
			out = new DualOutputStream(System.out, fos);
		}
		
		return out;
	}
	
}
