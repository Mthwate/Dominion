package com.mthwate.dominion.common.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.logging.Formatter;
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

		if (record.getThrown() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			record.getThrown().printStackTrace(pw);
			pw.close();
			sb.append(sw.toString());
		}

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
