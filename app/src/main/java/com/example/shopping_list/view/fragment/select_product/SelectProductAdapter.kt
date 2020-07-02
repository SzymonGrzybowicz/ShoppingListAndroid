package com.example.shopping_list.view.fragment.select_product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.shopping_list.R
import com.example.shopping_list.model.dto.Product
import kotlinx.android.synthetic.main.adapter_select_product_item.view.*

class SelectProductAdapter(context: Context, private val products: List<Product>) :
    ArrayAdapter<Product>(context, R.layout.adapter_select_product_item, products) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item =
            convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.adapter_select_product_item, parent, false)

        val product = products[position]

        item.adapter_product_name_text.text = product.name

        return item
    }
}