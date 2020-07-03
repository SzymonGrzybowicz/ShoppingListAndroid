package com.example.shopping_list.view_model.activity

import androidx.lifecycle.ViewModel
import com.example.shopping_list.model.dto.Product
import com.example.shopping_list.model.dto.ShoppingList

class SharedViewModel: ViewModel() {
    var selectedShoppingList: ShoppingList? = null

    companion object {
        const val KEY_LIST_SAVED_STATE = "key_list_saved_state"
    }
}