package com.example.base.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.base.log.Log

abstract class BaseActivity: AppCompatActivity() {

	protected abstract fun getLogTag() : String

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		Log.v(objectTag, "onActivityResult()")
		super.onActivityResult(requestCode, resultCode, data)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		mObjectTag = getLogTag() + "(${Log.objectId})"
		Log.v(objectTag, "savedInstanceState($savedInstanceState), onCreate")
		super.onCreate(savedInstanceState)
	}

	override fun onResume() {
		Log.v(objectTag, "onResume()")
		super.onResume()
	}

	override fun onStart() {
		Log.v(objectTag, "onStart()")
		super.onStart()
	}

	override fun onStop() {
		Log.v(objectTag, "onStop()")
		super.onStop()
	}

	override fun onPause() {
		Log.v(objectTag, "onPause()")
		super.onPause()
	}

	override fun onDestroy() {
		Log.v(objectTag, "onDestroy")
		super.onDestroy()
	}

	override fun finish() {
		Log.v(objectTag, "finish")
		super.finish()
	}

	override fun onConfigurationChanged(newConfig: Configuration) {
		Log.v(objectTag, "onConfigurationChanged()")
		super.onConfigurationChanged(newConfig)
	}

	override fun attachBaseContext(base: Context) {
		Log.v(objectTag, "attachBaseContext()")
		super.attachBaseContext(base)
	}

	protected val objectTag
		get() = mObjectTag

	private var mObjectTag : String = ""
}