package com.example.shopping_list.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: Long? = null,
    val name: String,
    var amount: Int = 0
) : Parcelable