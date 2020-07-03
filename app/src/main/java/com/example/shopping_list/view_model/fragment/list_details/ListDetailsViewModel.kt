package com.example.shopping_list.view_model.fragment.list_details

import androidx.lifecycle.ViewModel
import com.example.shopping_list.model.dto.ShoppingList
import com.example.shopping_list.model.repository.IProductRepository
import com.example.shopping_list.model.repository.IShoppingListRepository
import com.example.shopping_list.model.repository.android.ProductRepository
import com.example.shopping_list.model.repository.android.ShoppingListRepository

class ListDetailsViewModel(
    private val productRepository: IProductRepository = ProductRepository(),
    private val shoppingListRepository: IShoppingListRepository = ShoppingListRepository()
) :
    ViewModel() {

    fun fetch(listId: Long) {
        productRepository.fetchProductsForList(listId)
    }

    fun saveList(list: ShoppingList) {
        shoppingListRepository.saveList(list)
    }

    val productsLiveData = productRepository.productsLiveData
}