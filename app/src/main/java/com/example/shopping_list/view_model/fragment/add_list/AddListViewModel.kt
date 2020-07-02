package com.example.shopping_list.view_model.fragment.add_list

import androidx.lifecycle.ViewModel
import com.example.shopping_list.model.dto.Product
import com.example.shopping_list.model.dto.ShoppingList
import com.example.shopping_list.model.repository.IProductRepository
import com.example.shopping_list.model.repository.IShoppingListRepository
import com.example.shopping_list.model.repository.android.ProductRepository
import com.example.shopping_list.model.repository.android.ShoppingListRepository

class AddListViewModel(
    private val shoppingListRepository: IShoppingListRepository = ShoppingListRepository(),
    private val productRepository: IProductRepository = ProductRepository()
) :
    ViewModel() {

    fun fetch(listId: Long) {
        productRepository.fetchProductsForList(listId)
    }

    fun save(list: ShoppingList) {
        shoppingListRepository.saveList(list)
    }

    val productsLiveData = productRepository.productsLiveData
}