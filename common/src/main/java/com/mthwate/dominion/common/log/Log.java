package com.mthwate.dominion.common.log;

import com.mthwate.datlib.DualOutputStream;
import com.mthwate.datlib.IOUtils;
import com.mthwate.naturallog.Level;
import com.mthwate.naturallog.LogWriter;
import com.mthwate.naturallog.Logger;
import com.mthwate.naturallog.PrefixLogWriter;
import com.mthwate.naturallog.StdLogWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author mthwate
 */
public class Log {
	private static LogWriter logWriter = new StdLogWriter(getOutputStream());

	public static final Logger MAIN = new Logger("dominion", Level.ALL, logWriter);

	public static final Logger CONFIG = MAIN.createChild("config");

	public static final Logger MESSAGING = MAIN.createChild("messaging");

	public static final Logger CONSOLE = MAIN.createChild("console", Level.ALL, new PrefixLogWriter(System.out, "  "));

	public static final Logger ENGINE = MAIN.createChild("engine", Level.OFF);

	public static final Logger TMP = MAIN.createChild("tmp");//TODO remove this
	
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
	
	public static void close() {
		IOUtils.close(MAIN);//Closing main will close all child loggers
	}
	
}
