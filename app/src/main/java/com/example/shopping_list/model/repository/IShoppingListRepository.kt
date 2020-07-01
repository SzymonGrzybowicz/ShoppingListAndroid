package com.example.shopping_list.model.repository

import androidx.lifecycle.LiveData
import com.example.shopping_list.model.dto.ShoppingList

interface IShoppingListRepository {
    fun fetchLists(status: ShoppingList.Status)
    fun saveList(list: ShoppingList)

    val shoppingListsLiveData: LiveData<List<ShoppingList>>
}