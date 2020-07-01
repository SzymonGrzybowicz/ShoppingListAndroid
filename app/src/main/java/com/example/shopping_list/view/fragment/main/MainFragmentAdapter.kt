package com.example.shopping_list.view.fragment.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.shopping_list.R
import com.example.shopping_list.model.dto.ShoppingList
import kotlinx.android.synthetic.main.adapter_main_item.view.*

class MainFragmentAdapter(context: Context, private val shoppingLists: List<ShoppingList>) :
    ArrayAdapter<ShoppingList>(context, R.layout.adapter_main_item, shoppingLists) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item =
            convertView ?:
                LayoutInflater.from(context).inflate(R.layout.adapter_main_item, parent, false)

        val list = shoppingLists[position]
        item.adapter_main_name.text = list.name

        return item
    }
}