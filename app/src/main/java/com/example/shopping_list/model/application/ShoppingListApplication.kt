package com.example.shopping_list.model.application

import android.app.Application
import com.example.base.log.Log
import com.example.shopping_list.model.database.ShoppingListDatabase

class ShoppingListApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")
        ShoppingListDatabase.open(applicationContext)
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG, "onTerminate()")
        ShoppingListDatabase.release()
    }

    companion object {
        private const val TAG = "ShoppingListApplication"
    }
}