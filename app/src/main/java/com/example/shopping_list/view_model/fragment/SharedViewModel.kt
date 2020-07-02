package com.example.shopping_list.view_model.fragment

import androidx.lifecycle.ViewModel
import com.example.shopping_list.model.dto.Product
import com.example.shopping_list.model.dto.ShoppingList

class SharedViewModel: ViewModel() {
    var addedProduct: Product? = null
    var editedShoppingList: ShoppingList? = null
}