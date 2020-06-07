package com.example.base.log

import com.example.shopping_list.BuildConfig

object Log {

	fun d(tag: String, msg: String) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.DEBUG) {
			android.util.Log.d(tag, msg)
		}
	}

	fun d(tag: String, msg: String, tr: Throwable) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.DEBUG) {
			android.util.Log.d(tag, msg, tr)
		}
	}

	fun e(tag: String, msg: String) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.ERROR) {
			android.util.Log.e(tag, msg)
		}
	}

	fun e(tag: String, msg: String, tr: Throwable) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.ERROR) {
			android.util.Log.e(tag, msg, tr)
		}
	}

	fun e(tag: String, tr: Throwable) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.ERROR) {
			android.util.Log.e(tag, "", tr)
		}
	}

	fun i(tag: String, msg: String) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.INFO) {
			android.util.Log.i(tag, msg)
		}
	}

	fun i(tag: String, msg: String, tr: Throwable) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.INFO) {
			android.util.Log.i(tag, msg, tr)
		}
	}

	fun v(tag: String, msg: String) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.VERBOSE) {
			android.util.Log.v(tag, msg)
		}
	}

	fun v(tag: String, msg: String, tr: Throwable) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.VERBOSE) {
			android.util.Log.v(tag, msg, tr)
		}
	}

	fun w(tag: String, msg: String) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.WARN) {
			android.util.Log.w(tag, msg)
		}
	}

	fun w(tag: String, msg: String, tr: Throwable) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.WARN) {
			android.util.Log.w(tag, msg, tr)
		}
	}

	fun w(tag: String, tr: Throwable) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.WARN) {
			android.util.Log.w(tag, tr)
		}
	}

	fun wtf(tag: String, msg: String) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.ASSERT) {
			android.util.Log.wtf(tag, msg)
		}
	}

	fun wtf(tag: String, tr: Throwable) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.ASSERT) {
			android.util.Log.wtf(tag, tr)
		}
	}

	fun wtf(tag: String, msg: String, tr: Throwable) {
		if (BuildConfig.LOG_LEVEL <= android.util.Log.ASSERT) {
			android.util.Log.wtf(tag, msg, tr)
		}
	}

	val objectId: Long?
		get() {
			sObjectId++
			return sObjectId
		}
	private var sObjectId: Long = 0L
}
