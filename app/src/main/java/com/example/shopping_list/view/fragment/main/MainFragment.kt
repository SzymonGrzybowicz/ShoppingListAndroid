package com.example.shopping_list.view.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.base.ui.BaseFragment
import com.example.shopping_list.R
import com.example.shopping_list.model.dto.ShoppingList
import com.example.shopping_list.view_model.fragment.main.MainFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            it.getString(KEY_SELECTED_STATUS)?.let {
                when(it) {
                    ShoppingList.Status.ACTIVE.name -> mSelectedStatus = ShoppingList.Status.ACTIVE
                    ShoppingList.Status.ARCHIVAL.name -> mSelectedStatus = ShoppingList.Status.ARCHIVAL
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        when(mSelectedStatus) {
            ShoppingList.Status.ACTIVE -> main_fragment_active_btn.isChecked = true
            ShoppingList.Status.ARCHIVAL -> main_fragment_archival_btn.isChecked = true
        }
        main_fragment_active_btn.setOnClickListener {
            mSelectedStatus = ShoppingList.Status.ACTIVE
            mViewModel.fetchShoppingLists(ShoppingList.Status.ACTIVE)
        }
        main_fragment_archival_btn.setOnClickListener {
            mSelectedStatus = ShoppingList.Status.ARCHIVAL
            mViewModel.fetchShoppingLists(ShoppingList.Status.ARCHIVAL)
        }
        val adapter = MainFragmentAdapter(requireContext(), mShoppingLists)
        main_fragment_list.adapter = adapter
        mViewModel.fetchShoppingLists(mSelectedStatus)
        mViewModel.shoppingListLiveData.observe(this, Observer {
            mShoppingLists.clear()
            mShoppingLists.addAll(it)
            adapter.notifyDataSetChanged()
        })
        main_fragment_add_btn.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddListFragment())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_SELECTED_STATUS, mSelectedStatus.name)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        main_fragment_active_btn.setOnClickListener(null)
        main_fragment_archival_btn.setOnClickListener(null)
        main_fragment_list.adapter = null
        main_fragment_add_btn.setOnClickListener(null)
    }

    override fun getLogTag(): String {
        return "MainFragment"
    }

    private val mShoppingLists = mutableListOf<ShoppingList>()
    private val mViewModel by viewModels<MainFragmentViewModel>()
    private var mSelectedStatus = ShoppingList.Status.ACTIVE

    companion object {
        private const val KEY_SELECTED_STATUS = "key_selected_status"
    }
}