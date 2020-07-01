package com.example.shopping_list.view.fragment.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.base.ui.BaseFragment
import com.example.shopping_list.R

class AddListFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.add_list_fragment, container, false)
    }

    override fun getLogTag(): String {
        return "AddListFragment"
    }
}