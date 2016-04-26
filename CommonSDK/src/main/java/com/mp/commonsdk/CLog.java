package com.mp.commonsdk;

import android.util.Log;

public class CLog {
	public static boolean enabled = BuildConfig.DEBUG;

	public static String makeLogTag(Class<?> cls) {
		return cls.getSimpleName();
	}

	public static void v(final String message) {
		if (enabled) {
			Log.v("LogInfo", message);
		}
	}

	public static void d(final String message) {
		if (enabled) {
			Log.d("LogInfo", message);
		}
	}

	public static void e(final String message) {
		if (enabled) {
			Log.e("LogInfo", message);
		}
	}

	public static void i(final String message) {
		if (enabled) {
			Log.i("LogInfo", message);
		}
	}

	public static void v(final String tag, final String message) {
		if (enabled) {
			Log.v("LogInfo_" + tag, message);
		}
	}

	public static void d(final String tag, final String message) {
		if (enabled) {
			Log.d("LogInfo_" + tag, message);
		}
	}

	public static void e(final String tag, final String message) {
		if (enabled) {
			Log.e("LogInfo_" + tag, message);
		}
	}

	public static void i(final String tag, final String message) {
		if (enabled) {
			Log.i("LogInfo_" + tag, message);
		}
	}

	public static void v(String tag, String msg, Object... args) {
		if (!enabled)
			return;
		if (args.length > 0) {
			msg = String.format(msg, args);
		}
		Log.v("LogInfo_" + tag, msg);
	}

	public static void d(String tag, String msg, Object... args) {
		if (!enabled)
			return;
		if (args.length > 0) {
			msg = String.format(msg, args);
		}
		Log.d("LogInfo_" + tag, msg);
	}

	public static void e(String tag, String msg, Object... args) {
		if (!enabled)
			return;
		if (args.length > 0) {
			msg = String.format(msg, args);
		}
		Log.e("LogInfo_" + tag, msg);
	}

	public static void i(String tag, String msg, Object... args) {
		if (!enabled)
			return;
		if (args.length > 0) {
			msg = String.format(msg, args);
		}
		Log.i("LogInfo_" + tag, msg);
	}

	public static void v(String tag, String msg, Throwable throwable) {
		if (!enabled)
			return;
		Log.v(tag, msg, throwable);
	}

	public static void d(String tag, String msg, Throwable throwable) {
		if (!enabled)
			return;
		Log.v(tag, msg, throwable);
	}

	public static void e(String tag, String msg, Throwable throwable) {
		if (!enabled)
			return;
		Log.v(tag, msg, throwable);
	}

	public static void i(String tag, String msg, Throwable throwable) {
		if (!enabled)
			return;
		Log.v(tag, msg, throwable);
	}

	
}
