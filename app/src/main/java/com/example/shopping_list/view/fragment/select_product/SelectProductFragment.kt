package com.example.shopping_list.view.fragment.select_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.base.ui.BaseFragment
import com.example.shopping_list.R
import com.example.shopping_list.model.dto.Product
import com.example.shopping_list.view.activity.MainActivity
import com.example.shopping_list.view_model.fragment.SharedViewModel
import com.example.shopping_list.view_model.fragment.select_product.SelectProductViewModel
import kotlinx.android.synthetic.main.fragment_select_product.*


class SelectProductFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_select_product, container, false)
    }

    override fun onStart() {
        super.onStart()
        mViewModel.fetchAllProducts()
        mViewModel.productsLiveData.observe(this, Observer {
            mProducts.clear()
            mProducts.addAll(it)
            mAdapter?.notifyDataSetChanged()
        })

        mAdapter = SelectProductAdapter(requireContext(), mProducts)
        add_product_list.adapter = mAdapter
        add_product_list.setOnItemClickListener { _, _, position, _ ->
            val product = mProducts[position]
            (activity as MainActivity).sharedViewModel.addedProduct = product
            findNavController().popBackStack()
        }

        add_product_new_btn.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(R.string.add_product)
            val editText = EditText(requireContext())
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            editText.layoutParams = lp
            editText.setHint(R.string.name)
            builder.setView(editText)

            builder.setPositiveButton(android.R.string.ok, null)
            builder.setOnDismissListener { mDialog = null }
            builder.setMessage("")

            builder.show().let { dialog ->
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    val name = editText.text.toString()
                    if (mProducts.any { p -> p.name == name }) {
                        dialog.setMessage(resources.getString(R.string.this_product_already_exists))
                    } else {
                        val product = Product(name = name, amount = -1)
                        mViewModel.saveProduct(product)
                        dialog.dismiss()
                        (activity as MainActivity).sharedViewModel.addedProduct = product
                        findNavController().popBackStack()
                    }
                }
                mDialog = dialog
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mDialog?.dismiss()
        add_product_new_btn.setOnClickListener(null)
        add_product_list.onItemClickListener = null
    }

    override fun getLogTag(): String {
        return "SelectProductFragment"
    }

    private var mDialog: AlertDialog? = null
    private val mViewModel by viewModels<SelectProductViewModel>()
    private var mProducts = mutableListOf<Product>()
    private var mAdapter: SelectProductAdapter? = null
}