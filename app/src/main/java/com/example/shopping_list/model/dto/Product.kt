package com.example.shopping_list.model.dto

data class Product(
    val id: Long? = null,
    val name: String,
    var amount: Int = 0
)