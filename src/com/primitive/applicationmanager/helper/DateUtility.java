/**
 * DateUtility
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.applicationmanager.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * DateUtility
 */
public class DateUtility {
	protected static final SimpleDateFormat UtcDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	public static Date getCurrentUTCDate() {
		final TimeZone tz = TimeZone.getTimeZone("UTC");
		final Calendar cal = Calendar.getInstance(tz);
		return cal.getTime();
	}

	public static Date getUTCDate(final String dateString)
			throws ParseException {
		DateUtility.UtcDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return DateUtility.UtcDateFormat.parse(dateString);
	}
}
