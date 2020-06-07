package com.example.shopping_list.model.application

import android.app.Application
import com.example.base.log.Log

class ShoppingListApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG, "onTerminate()")
    }

    companion object {
        private const val TAG = "ShoppingListApplication"
    }
}