package com.example.shopping_list.view.fragment.add_list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.base.ui.BaseFragment
import com.example.shopping_list.R
import com.example.shopping_list.model.dto.Product
import com.example.shopping_list.model.dto.ShoppingList
import com.example.shopping_list.view.activity.MainActivity
import com.example.shopping_list.view_model.fragment.SharedViewModel
import com.example.shopping_list.view_model.fragment.add_list.AddListViewModel
import kotlinx.android.synthetic.main.add_list_fragment.*

class AddListFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.add_list_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()

        AddListAdapter(requireContext(), mProducts).let {
            add_list_products.adapter = it
            mAdapter = it
        }

        val list = (activity as MainActivity).sharedViewModel.editedShoppingList
        list?.let {
            add_list_name_edit_text.setText(it.name)
        }
        val listId = list?.id
        if(listId != null) {
            mViewModel.fetch(listId)
        } else {
            (activity as MainActivity).sharedViewModel.addedProduct?.let {
                mProducts.add(it)
                (activity as MainActivity).sharedViewModel.addedProduct = null
            }
        }

        mViewModel.productsLiveData.observe(this, Observer {
            mProducts.clear()
            mProducts.addAll(it)
            (activity as MainActivity).sharedViewModel.addedProduct?.let {
                mProducts.add(it)
                (activity as MainActivity).sharedViewModel.addedProduct = null
            }
            mAdapter?.notifyDataSetChanged()
        })

        add_list_add_product_btn.setOnClickListener {
            findNavController().navigate(AddListFragmentDirections.actionAddListFragmentToSelectProductFragment())
        }

        add_list_save_btn.setOnClickListener {
            if (validateAndMarkMistakes()) {
                mViewModel.save(constructListFromView())
                findNavController().popBackStack()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        add_list_add_product_btn.setOnClickListener(null)
        add_list_save_btn.setOnClickListener(null)
        add_list_products.adapter = null
    }

    private fun validateAndMarkMistakes(): Boolean {
        if (add_list_name_edit_text.text.toString().isEmpty()) {
            add_list_name_edit_text.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
            return false
        }
        if (mProducts.isEmpty()) {
            Toast.makeText(requireContext(), R.string.there_is_no_products, Toast.LENGTH_SHORT).show()
            return false
        }
        return mAdapter?.validate() ?: false
    }

    private fun constructListFromView(): ShoppingList {
        return ShoppingList(
            name = add_list_name_edit_text.text.toString(),
            status = ShoppingList.Status.ACTIVE,
            products = mProducts
        )
    }

    override fun getLogTag(): String {
        return "AddListFragment"
    }

    private val mViewModel by viewModels<AddListViewModel>()
    private var mAdapter: AddListAdapter? = null
    private var mProducts = mutableListOf<Product>()
}