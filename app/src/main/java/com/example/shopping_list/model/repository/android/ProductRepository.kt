package com.example.shopping_list.model.repository.android

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shopping_list.model.database.ShoppingListDatabase
import com.example.shopping_list.model.dto.Product
import com.example.shopping_list.model.repository.IProductRepository

class ProductRepository(private val mDatabase: SQLiteDatabase? = ShoppingListDatabase.database) :
    IProductRepository {

    override fun fetchAllProducts() {
        Log.d(TAG, "fetchProducts()")
        mDatabase?.let { db ->
            val columns = arrayOf(
                ShoppingListDatabase.PRODUCTS_KEY_ID,
                ShoppingListDatabase.PRODUCTS_KEY_NAME
            )

            val cursor = db.query(
                ShoppingListDatabase.PRODUCTS_TABLE_NAME, columns,
                null, null, null, null, ShoppingListDatabase.PRODUCTS_KEY_NAME, null
            )

            val products = mutableListOf<Product>()

            while (cursor.moveToNext()) {
                products.add(
                    Product(
                        cursor.getLong(columns.indexOf(ShoppingListDatabase.PRODUCTS_KEY_ID)),
                        cursor.getString(columns.indexOf(ShoppingListDatabase.PRODUCTS_KEY_NAME))
                    )
                )
            }

            cursor.close()
            mProductsLiveData.value = products
            return
        }
        mProductsLiveData.value = listOf()
    }

    override fun saveProduct(product: Product): Product? {
        mDatabase?.let { db ->
            val contentValues = ContentValues()
            contentValues.put(ShoppingListDatabase.PRODUCTS_KEY_NAME, product.name)

            val id = db.insert(ShoppingListDatabase.PRODUCTS_TABLE_NAME, null, contentValues)
            return Product(id, product.name)
        }
        return null
    }

    override fun fetchProductsForList(listId: Long) {
        Log.d(TAG, "fetchProductsForList($listId)")
        mDatabase?.let { db ->
            val columns = arrayOf(
                ShoppingListDatabase.JUNCTION_KEY_PRODUCT_ID,
                ShoppingListDatabase.JUNCTION_KEY_AMOUNT
            )

            val cursor = db.query(
                ShoppingListDatabase.JUNCTION_LIST_PRODUCT_TABLE_NAME, columns,
                "${ShoppingListDatabase.JUNCTION_KEY_LIST_ID} = ?", arrayOf(listId.toString()),
                null, null, ShoppingListDatabase.PRODUCTS_KEY_NAME, null
            )

            val products = mutableListOf<Product>()
            while (cursor.moveToNext()) {
                getProductById(cursor.getLong(columns.indexOf(ShoppingListDatabase.JUNCTION_KEY_PRODUCT_ID)))?.let {
                    it.amount = cursor.getInt(columns.indexOf(ShoppingListDatabase.JUNCTION_KEY_AMOUNT))
                    products.add(it)
                }
            }
            cursor.close()
            mProductsLiveData.value = products
            return
        }
        mProductsLiveData.value = listOf()
    }

    override fun saveProductsForList(listId: Long, products: List<Product>) {
        mDatabase?.let { db ->
            db.delete(
                ShoppingListDatabase.JUNCTION_LIST_PRODUCT_TABLE_NAME,
                "${ShoppingListDatabase.JUNCTION_KEY_LIST_ID} = ?",
                arrayOf(listId.toString())
            )

            products.forEach { product ->
                val contentValues = ContentValues()
                contentValues.put(ShoppingListDatabase.JUNCTION_KEY_LIST_ID, listId)
                contentValues.put(ShoppingListDatabase.JUNCTION_KEY_PRODUCT_ID, product.id)
                contentValues.put(ShoppingListDatabase.JUNCTION_KEY_AMOUNT, product.amount)
                db.insert(ShoppingListDatabase.JUNCTION_LIST_PRODUCT_TABLE_NAME, null, contentValues)
            }
        }
    }

    private fun getProductById(id: Long): Product? {
        mDatabase?.let { db ->
            val columns = arrayOf(
                ShoppingListDatabase.PRODUCTS_KEY_NAME
            )

            val cursor = db.query(
                ShoppingListDatabase.PRODUCTS_TABLE_NAME, columns,
                null, null, null, null, null, null
            )


            if (cursor.moveToFirst()) {
                val name = cursor.getString(columns.indexOf(ShoppingListDatabase.PRODUCTS_KEY_NAME))
                cursor.close()
                return Product(
                    id,
                    name
                )
            }
        }
        return null
    }

    override val productsLiveData: LiveData<List<Product>>
        get() = mProductsLiveData

    var mProductsLiveData = MutableLiveData<List<Product>>()

    companion object {
        private const val TAG = "ProductRepository"
    }
}