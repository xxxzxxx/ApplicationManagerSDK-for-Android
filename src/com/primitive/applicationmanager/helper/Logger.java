/**
 * Logger
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.applicationmanager.helper;

import android.os.Debug;
import android.util.Log;

import com.primitive.applicationmanager.BuildConfig;

/**
 * Logger
 */
public class Logger {
	private static boolean DEBUG = BuildConfig.DEBUG;

	public static void debug(final String msg) {
		if (Logger.DEBUG) {
			final StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			Log.d(currentTack.getClassName(), msg);
		}
	}

	public static void debug(final Object obj) {
		if (Logger.DEBUG) {
			final StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if(obj == null){
				Log.d(currentTack.getClassName(), "Object is NULL");
			}else{
				Log.d(currentTack.getClassName(), obj.toString());
			}
		}
	}

	public static void debug(final byte[] msg) {
		if (Logger.DEBUG) {
			final StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			Log.d(currentTack.getClassName(), msg.toString());
		}
	}

	public static void end() {
		if (Logger.DEBUG) {
			final StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			Log.v(currentTack.getClassName(), currentTack.getMethodName()
					+ " end");
		}
	}

	public static void err(final Throwable ex) {
		final StackTraceElement currentTack = Thread.currentThread()
				.getStackTrace()[3];
		Log.e(currentTack.getClassName(), ex.getMessage(), ex);
	}

	public static void heap() {
		if (Logger.DEBUG) {
			final StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			final String msg = "heap : Free="
					+ Long.toString(Debug.getNativeHeapFreeSize() / 1024)
					+ "kb" + "\n Allocated="
					+ Long.toString(Debug.getNativeHeapAllocatedSize() / 1024)
					+ "kb" + "\n Size="
					+ Long.toString(Debug.getNativeHeapSize() / 1024) + "kb";
			Log.v(currentTack.getClassName(), msg);
		}
	}

	public static void start() {
		if (Logger.DEBUG) {
			final StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			Log.v(currentTack.getClassName(), currentTack.getMethodName()
					+ " start");
		}
	}

	public static void warm(final String msg) {
		final StackTraceElement currentTack = Thread.currentThread()
				.getStackTrace()[3];
		Log.w(currentTack.getClassName(), msg);
	}

	public static void warm(final Throwable ex) {
		final StackTraceElement currentTack = Thread.currentThread()
				.getStackTrace()[3];
		Log.w(currentTack.getClassName(), ex.getStackTrace().toString());
	}
}
