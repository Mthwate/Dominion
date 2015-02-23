package com.mthwate.dominion.common.log;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author mthwate
 */
public class StandardFormatter extends Formatter {

	String newline = System.getProperty("line.separator");

	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();

		String[] split = record.getSourceClassName().split("\\.");

		String name = split[split.length - 1];

		appendTime(sb);
		sb.append(' ');
		sb.append('[');
		sb.append(record.getLevel());
		sb.append(']');
		sb.append(' ');
		sb.append('(');
		sb.append(name);
		sb.append(')');
		sb.append(' ');
		sb.append(formatMessage(record));
		sb.append(newline);

		return sb.toString();
	}

	private void appendTime(StringBuilder sb) {

		Calendar cal = Calendar.getInstance();

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		int milli = cal.get(Calendar.MILLISECOND);

		sb.append(hour);
		sb.append(':');
		sb.append(min);
		sb.append(':');
		sb.append(sec);
		sb.append(':');

		if (milli < 100) {
			sb.append(0);
		}

		if (milli < 10) {
			sb.append(0);
		}

		sb.append(milli);
	}

}
