package com.example.shopping_list.view_model.fragment.main

import androidx.lifecycle.ViewModel
import com.example.shopping_list.model.dto.ShoppingList
import com.example.shopping_list.model.repository.IShoppingListRepository
import com.example.shopping_list.model.repository.android.ShoppingListRepository

class MainFragmentViewModel(
    private val shoppingListRepository: IShoppingListRepository = ShoppingListRepository()
): ViewModel() {

    fun fetchShoppingLists(status: ShoppingList.Status){
        shoppingListRepository.fetchLists(status)
    }

    val shoppingListLiveData = shoppingListRepository.shoppingListsLiveData
}