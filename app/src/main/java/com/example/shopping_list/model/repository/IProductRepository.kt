package com.example.shopping_list.model.repository

import androidx.lifecycle.LiveData
import com.example.shopping_list.model.dto.Product

interface IProductRepository {
    fun fetchAllProducts()
    fun saveProduct(product: Product)
    fun fetchProductsForList(listId: Long)
    fun saveProductsForList(listId: Long, products: List<Product>)

    val productsLiveData: LiveData<List<Product>>
}