package com.example.shopping_list.model.dto

data class ShoppingList(
    val id: Long? = null,
    val name: String,
    var status: Status,
    var products: List<Product>? = null
){

    enum class Status{
        ACTIVE, ARCHIVAL
    }
}