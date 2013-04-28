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
	public static class Level {
		public static final Level Nothing = new Level(0);
		public static final Level Error = new Level(1);
		public static final Level Warm  = new Level(2);
		public static final Level Trace = new Level(3);
		public static final Level Debug = new Level(4);
		public static final Level All = new Level(100);
		private int value;
		Level(final int value){
			this.value = value;
		}
		public static int comparison(Level compare,Level base){
			return (compare.value - base.value);
		}
	}

	private static Level LogLevel = BuildConfig.DEBUG 
			? Level.Error 
			: Level.Error;

	public static void start() {
		if(Level.comparison(LogLevel,Logger.Level.Trace) >= 0){
			StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
			if(currentTack != null)
				Log.i(currentTack.getClassName(), currentTack.getMethodName() + " start");
		}
	}

	public static void end() {
		if(Level.comparison(LogLevel,Logger.Level.Trace) >= 0){
			StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
			if(currentTack != null)
				Log.i(currentTack.getClassName(), currentTack.getMethodName() + " end");
		}
	}

	public static void err(Throwable ex) {
		if(Level.comparison(LogLevel,Logger.Level.Error) >= 0){
			StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
			if(currentTack != null)
				Log.e(currentTack.getClassName(), ex.getMessage(), ex);
		}
	}

	public static void warm(String msg) {
		if(Level.comparison(LogLevel,Logger.Level.Warm) >= 0){
			StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
			if(currentTack != null)
				Log.w(currentTack.getClassName(), msg);
		}
	}

	public static void warm(Throwable ex) {
		if(Level.comparison(LogLevel,Logger.Level.Warm) >= 0){
			StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
			if(currentTack != null)
				Log.w(currentTack.getClassName(), ex.getStackTrace().toString());
		}
	}

	public static void debug(String msg) {
		if(Level.comparison(LogLevel,Logger.Level.Debug) >= 0){
			StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
			if(currentTack != null){
				Log.d(currentTack.getClassName(), msg);
			}
		}
	}

	public static void debug(long l) {
		if(Level.comparison(LogLevel,Logger.Level.Debug) >= 0){
			StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
			if(currentTack != null)
				Log.d(currentTack.getClassName(), "" + l);
		}
	}

	public static void debug(Object obj) {
		if(Level.comparison(LogLevel,Logger.Level.Debug) >= 0){
			StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
			if(currentTack != null)
				Log.d(currentTack.getClassName(), obj != null ? obj.toString() : "obj is null");
		}
	}
	
	public static void heap() {
		if(Level.comparison(LogLevel,Logger.Level.Debug) >= 0){
			StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
			String msg = "heap : Free=" + Long.toString(Debug.getNativeHeapFreeSize() / 1024) + "kb" +   
			"\n Allocated=" + Long.toString(Debug.getNativeHeapAllocatedSize() / 1024) + "kb" +   
			"\n Size=" + Long.toString(Debug.getNativeHeapSize() / 1024) + "kb";  
			if(currentTack != null)
				Log.v(currentTack.getClassName(), msg);
		 }
	}
}