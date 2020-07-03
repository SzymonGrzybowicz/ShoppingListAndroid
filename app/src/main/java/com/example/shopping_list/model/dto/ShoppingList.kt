package com.example.shopping_list.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShoppingList(
    val id: Long? = null,
    val name: String,
    var status: Status,
    val products: MutableList<Product> = mutableListOf()
): Parcelable {

    enum class Status{
        ACTIVE, ARCHIVAL
    }
}