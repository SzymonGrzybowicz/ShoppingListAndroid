package com.example.shopping_list.view.fragment.add_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import com.example.shopping_list.R
import com.example.shopping_list.model.dto.Product
import kotlinx.android.synthetic.main.adapter_products_item.view.*

class AddListAdapter(context: Context, private val products: MutableList<Product>) :
    ArrayAdapter<Product>(context, R.layout.adapter_products_item, products) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item =
            convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.adapter_products_item, parent, false)

        val product = products[position]

        item.adapter_product_name_text.text = product.name
        val text = item.adapter_product_amount_edit_text.text.toString()
        if (text.isEmpty() || text.toInt() < 1) {
            item.adapter_product_amount_edit_text.setBackgroundColor(
                context.resources.getColor(android.R.color.holo_red_light)
            )
        }

        item.adapter_product_amount_edit_text.doOnTextChanged { typedText, _, _, _ ->
            if (typedText?.isEmpty() == true || typedText.toString().toInt() < 1) {
                item.adapter_product_amount_edit_text.setBackgroundColor(
                    context.resources.getColor(
                        android.R.color.holo_red_light
                    )
                )
            } else {
                item.adapter_product_amount_edit_text.setBackgroundColor(
                    context.resources.getColor(
                        android.R.color.transparent
                    )
                )
                product.amount = typedText.toString().toInt()
            }
        }

        item.adapter_product_delete_btn.setOnClickListener {
            products.remove(product)
            notifyDataSetChanged()
        }

        return item
    }

    fun validate(): Boolean {
        if (products.filter { p -> p.amount < 1 }.isNotEmpty()) {
            return false
        }
        return true
    }
}