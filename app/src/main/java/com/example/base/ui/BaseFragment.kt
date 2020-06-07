package com.example.base.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.base.log.Log

abstract class BaseFragment: Fragment() {

	protected abstract fun getLogTag() : String

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		Log.v(objectTag, "onActivityCreated")
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		mObjectTag = getLogTag() + "(${Log.objectId})"
		Log.v(objectTag, "onAttach()")
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Log.v(objectTag, "onCreate()")
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		Log.v(objectTag, "onCreateView")
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onResume() {
		super.onResume()
		Log.v(objectTag, "onResume")
	}

	override fun onStart() {
		super.onStart()
		Log.v(objectTag, "onStart")
	}

	override fun onPause() {
		super.onPause()
		Log.v(objectTag, "onPause")
	}

	override fun onDetach() {
		super.onDetach()
		Log.v(objectTag, "onDetach")
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		Log.v(objectTag, "onSaveInstanceState")
	}

	override fun onDestroyView() {
		super.onDestroyView()
		Log.v(objectTag, "onDestroyView")
	}

	override fun onDestroy() {
		super.onDestroy()
		Log.v(objectTag, "onDestroy")
	}

	override fun onStop() {
		super.onStop()
		Log.v(objectTag, "onStop")
	}

	protected val objectTag: String
		get() = mObjectTag

	private var mObjectTag = ""
}