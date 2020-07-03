package com.example.shopping_list.view.fragment.add_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.base.ui.BaseFragment
import com.example.shopping_list.R
import com.example.shopping_list.model.dto.Product
import com.example.shopping_list.model.dto.ShoppingList
import com.example.shopping_list.view.activity.MainActivity
import com.example.shopping_list.view_model.activity.SharedViewModel
import com.example.shopping_list.view_model.fragment.add_list.AddListViewModel
import kotlinx.android.synthetic.main.add_list_fragment.*

class EditListFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        savedInstanceState?.getParcelable<ShoppingList>(SharedViewModel.KEY_LIST_SAVED_STATE)?.let {
            (activity as MainActivity).sharedViewModel.selectedShoppingList = it
        }
        return inflater.inflate(R.layout.add_list_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()

        (activity as MainActivity).sharedViewModel.selectedShoppingList?.let {
            it.id?.let {
                mViewModel.fetch(it)
            }
            add_list_name_edit_text.setText(it.name)
                EditListAdapter(requireContext(), it.products).let {
                    add_list_products.adapter = it
                    mAdapter = it
                }

            mViewModel.productsLiveData.observe(this, Observer { products ->
                products.forEach { product ->
                    if (!it.products.contains(product)) {
                        it.products.add(product)
                    }
                }
                mAdapter?.notifyDataSetChanged()
            })
        }

        add_list_add_product_btn.setOnClickListener {
            (activity as MainActivity).sharedViewModel.selectedShoppingList = constructListFromView()
            findNavController().navigate(EditListFragmentDirections.actionAddListFragmentToSelectProductFragment())
        }

        add_list_save_btn.setOnClickListener {
            if (validateAndMarkMistakes()) {
                val list = constructListFromView()
                (activity as MainActivity).sharedViewModel.selectedShoppingList = list
                mViewModel.save(list)
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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(SharedViewModel.KEY_LIST_SAVED_STATE, constructListFromView())
        super.onSaveInstanceState(outState)
    }

    private fun validateAndMarkMistakes(): Boolean {
        if (add_list_name_edit_text.text.toString().isEmpty()) {
            add_list_name_edit_text.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
            return false
        }
        if ((activity as MainActivity).sharedViewModel.selectedShoppingList?.products?.isNotEmpty() == false) {
            Toast.makeText(requireContext(), R.string.there_is_no_products, Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return mAdapter?.validate() ?: false
    }

    private fun constructListFromView(): ShoppingList {
        return ShoppingList(
            id = (activity as MainActivity).sharedViewModel.selectedShoppingList?.id,
            name = add_list_name_edit_text.text.toString(),
            status = ShoppingList.Status.ACTIVE,
            products = (activity as MainActivity).sharedViewModel.selectedShoppingList?.products ?: mutableListOf()
        )
    }

    override fun getLogTag(): String {
        return "EditListFragment"
    }

    private val mViewModel by viewModels<AddListViewModel>()
    private var mAdapter: EditListAdapter? = null
}