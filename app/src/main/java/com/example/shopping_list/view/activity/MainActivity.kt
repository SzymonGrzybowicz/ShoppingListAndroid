package com.example.shopping_list.view.activity

import android.os.Bundle
import com.example.base.ui.BaseActivity
import com.example.shopping_list.R

class MainActivity : BaseActivity() {

    override fun getLogTag(): String {
        return "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
