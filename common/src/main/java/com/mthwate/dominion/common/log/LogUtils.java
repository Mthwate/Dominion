package com.mthwate.dominion.common.log;

import com.mthwate.datlib.DualOutputStream;
import com.mthwate.datlib.IOUtils;
import lombok.Cleanup;

import java.io.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class LogUtils {
	
	public static void init() {

		FileInputStream fis = null;
		try {
			fis = new FileInputStream("logging.properties");
			LogManager.getLogManager().readConfiguration(fis);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(fis);
		}

		Logger root = Logger.getLogger("");

		for (Handler handler : root.getHandlers()) {
			root.removeHandler(handler);
		}

		LiveStreamHandler lsh = new LiveStreamHandler(getOutputStream(), new StandardFormatter());

		root.addHandler(lsh);

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
