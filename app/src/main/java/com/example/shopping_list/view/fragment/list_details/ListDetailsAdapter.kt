package com.example.shopping_list.view.fragment.list_details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.shopping_list.R
import com.example.shopping_list.model.dto.Product
import kotlinx.android.synthetic.main.adapter_list_details_item.view.*

class ListDetailsAdapter(context: Context, private val products: List<Product>) :
    ArrayAdapter<Product>(context, R.layout.adapter_list_details_item, products) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item =
            convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.adapter_list_details_item, parent, false)

        val product = products[position]

        item.adapter_list_details_name_txt.text = product.name
        item.adapter_list_details_amount_txt.text = product.amount.toString()

        return item
    }
}