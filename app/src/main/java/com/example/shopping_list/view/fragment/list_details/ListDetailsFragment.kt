package com.example.shopping_list.view.fragment.list_details

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
import com.example.shopping_list.view.activity.MainActivity
import com.example.shopping_list.view_model.activity.SharedViewModel
import com.example.shopping_list.view_model.fragment.list_details.ListDetailsViewModel
import kotlinx.android.synthetic.main.fragment_list_details.*

class ListDetailsFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        savedInstanceState?.getParcelable<ShoppingList>(SharedViewModel.KEY_LIST_SAVED_STATE)?.let {
            (activity as MainActivity).sharedViewModel.selectedShoppingList = it
        }
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_list_details, container, false)
    }

    override fun onStart() {
        super.onStart()
        val list = (activity as MainActivity).sharedViewModel.selectedShoppingList
        list_details_name_txt.text = list?.name
        list_details_status_txt.text = list?.status?.name
        mAdapter = ListDetailsAdapter(requireContext(), list?.products ?: listOf())
        list_details_products_list.adapter = mAdapter
        list?.id?.let {
            mViewModel.fetch(it)
        }
        mViewModel.productsLiveData.observe(this, Observer {
            it.forEach {
                if (list?.products?.contains(it) == false) {
                    list.products.add(it)
                }
            }
            mAdapter?.notifyDataSetChanged()
        })

        if (list?.status == ShoppingList.Status.ARCHIVAL) {
            list_details_archive_btn.visibility = View.GONE
            list_details_edit_btn.visibility = View.GONE
        }

        list_details_archive_btn.setOnClickListener {
            list?.let {
                it.status = ShoppingList.Status.ARCHIVAL
                mViewModel.saveList(it)
                list_details_archive_btn.visibility = View.GONE
                list_details_edit_btn.visibility = View.GONE
                list_details_status_txt.text = it.status.name
            }
        }
        list_details_edit_btn.setOnClickListener{
            findNavController().navigate(ListDetailsFragmentDirections.actionListDetailsFragmentToEditListFragment())
        }
    }

    override fun onStop() {
        super.onStop()
        list_details_products_list.adapter = null
        list_details_archive_btn.setOnClickListener(null)
        list_details_edit_btn.setOnClickListener(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(SharedViewModel.KEY_LIST_SAVED_STATE,  (activity as MainActivity).sharedViewModel.selectedShoppingList)
        super.onSaveInstanceState(outState)
    }

    override fun getLogTag(): String {
        return "ListDetailsFragment"
    }

    private val mViewModel by viewModels<ListDetailsViewModel>()
    private var mAdapter: ListDetailsAdapter? = null
}