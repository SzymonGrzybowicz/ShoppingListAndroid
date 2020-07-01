package com.example.shopping_list.model.repository.android

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.log.Log
import com.example.shopping_list.model.database.ShoppingListDatabase
import com.example.shopping_list.model.dto.ShoppingList
import com.example.shopping_list.model.repository.IShoppingListRepository

class ShoppingListRepository(
    private val mDatabase: SQLiteDatabase? = ShoppingListDatabase.database,
    private val mProductRepository: ProductRepository = ProductRepository()
) : IShoppingListRepository {

    override fun fetchLists(status: ShoppingList.Status) {
        Log.d(TAG, "fetchProducts()")
        mDatabase?.let { db ->
            val columns = arrayOf(
                ShoppingListDatabase.LISTS_KEY_ID,
                ShoppingListDatabase.LISTS_KEY_NAME
            )

            val cursor = db.query(
                ShoppingListDatabase.LISTS_TABLE_NAME, columns,
                "${ShoppingListDatabase.LISTS_KEY_STATUS} = ?", arrayOf(status.name),
                null, null, null, null
            )

            val shoppingLists = mutableListOf<ShoppingList>()

            while (cursor.moveToNext()) {
                shoppingLists.add(
                    ShoppingList(
                        cursor.getLong(columns.indexOf(ShoppingListDatabase.LISTS_KEY_ID)),
                        cursor.getString(columns.indexOf(ShoppingListDatabase.LISTS_KEY_NAME)),
                        status
                    )
                )
            }

            cursor.close()
            mShoppingListLiveData.value = shoppingLists
            return
        }
        mShoppingListLiveData.value = listOf()
    }

    override fun saveList(list: ShoppingList) {
        mDatabase?.let { db ->
            val contentValues = ContentValues()
            contentValues.put(ShoppingListDatabase.LISTS_KEY_NAME, list.name)
            contentValues.put(ShoppingListDatabase.LISTS_KEY_STATUS, list.status.name)
            val id = db.insert(ShoppingListDatabase.LISTS_TABLE_NAME, null, contentValues)
            list.products?.let { products ->
                mProductRepository.saveProductsForList(id, products)
            }
        }
    }

    override val shoppingListsLiveData: LiveData<List<ShoppingList>>
        get() = mShoppingListLiveData

    private val mShoppingListLiveData = MutableLiveData<List<ShoppingList>>()

    companion object {
        private const val TAG = "ShoppingListRepository"
    }
}