package com.example.shopping_list.view_model.fragment.select_product

import androidx.lifecycle.ViewModel
import com.example.shopping_list.model.dto.Product
import com.example.shopping_list.model.repository.IProductRepository
import com.example.shopping_list.model.repository.android.ProductRepository

class SelectProductViewModel(private val productRepository: IProductRepository = ProductRepository()) :
    ViewModel() {

    fun fetchAllProducts() {
        productRepository.fetchAllProducts()
    }

    fun saveProduct(product: Product): Product? {
        return productRepository.saveProduct(product)
    }

    val productsLiveData = productRepository.productsLiveData
}