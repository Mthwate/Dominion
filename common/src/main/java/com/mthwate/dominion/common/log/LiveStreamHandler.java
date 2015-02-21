package com.mthwate.dominion.common.log;

import java.io.OutputStream;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * @author mthwate
 */
public class LiveStreamHandler extends StreamHandler {
	
	public LiveStreamHandler(OutputStream out, Formatter formatter) {
		super(out, formatter);
	}
	
	@Override
	public void publish(LogRecord logRecord) {
		super.publish(logRecord);
		this.flush();
	}
	
}
