package com.mthwate.dominion.common;

import com.mthwate.datlib.IOUtils;
import com.mthwate.naturallog.Level;
import com.mthwate.naturallog.LogWriter;
import com.mthwate.naturallog.Logger;
import com.mthwate.naturallog.StdLogWriter;

/**
 * @author mthwate
 */
public class Log {

	private static LogWriter logWriter = new StdLogWriter(System.out);

	public static final Logger MAIN = new Logger("conk", Level.ALL, logWriter);

	public static final Logger CONFIG = MAIN.createChild("config");

	public static final Logger MESSAGING = MAIN.createChild("messaging");

	public static final Logger ENGINE = MAIN.createChild("engine", Level.OFF);

	public static final Logger TMP = MAIN.createChild("tmp");//TODO remove this

	public static void close() {
		IOUtils.close(MAIN);//Closing main will close all child loggers
	}
	
}
