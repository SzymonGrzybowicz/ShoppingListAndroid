package com.example.shopping_list.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.example.base.ui.BaseActivity
import com.example.shopping_list.R
import com.example.shopping_list.view_model.fragment.SharedViewModel

class MainActivity : BaseActivity() {

    override fun getLogTag(): String {
        return "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    val sharedViewModel by viewModels<SharedViewModel>()
}
