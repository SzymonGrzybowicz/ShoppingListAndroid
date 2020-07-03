package com.example.shopping_list

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.MutableLiveData
import com.example.shopping_list.model.database.ShoppingListDatabase
import com.example.shopping_list.model.dto.Product
import com.example.shopping_list.model.repository.android.ProductRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class ProductRepositoryTestSuite {

    @Mock
    private lateinit var database: SQLiteDatabase
    @Mock
    private lateinit var cursor: Cursor
    @Mock
    private lateinit var liveData: MutableLiveData<List<Product>>
    @Mock
    private lateinit var mockContentValues: ContentValues

    private lateinit var repository: ProductRepository


        @Before
        fun before() {
            MockitoAnnotations.initMocks(this)
            repository = ProductRepository(database)
            repository.mProductsLiveData = liveData
        }

    @Test
    fun testFetchAllProducts() {
        Mockito.`when`(
            database.query(
                Mockito.eq(ShoppingListDatabase.PRODUCTS_TABLE_NAME),
                Mockito.any(Array<String>::class.java),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(String::class.java),
                Mockito.any()
            )
        ).thenReturn(cursor)

        Mockito.`when`(
            cursor.moveToNext()
        ).thenReturn(false)

        repository.fetchAllProducts()

        Mockito.verify(liveData).setValue(listOf())
    }

    @Test
    fun testFetchProductForList(){
        Mockito.`when`(
            database.query(
                Mockito.eq(ShoppingListDatabase.JUNCTION_LIST_PRODUCT_TABLE_NAME),
                Mockito.any(Array<String>::class.java),
                Mockito.eq("${ShoppingListDatabase.JUNCTION_KEY_LIST_ID} = ?"),
                Mockito.any(Array<String>::class.java),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(String::class.java),
                Mockito.any()
            )
        ).thenReturn(cursor)

        Mockito.`when`(
            cursor.moveToNext()
        ).thenReturn(false)

        repository.fetchProductsForList(1)

        Mockito.verify(liveData).setValue(listOf())
    }

    @Test
    fun testSaveProduct() {
        repository.saveProduct(Product(name = "test", amount = -1))

        Mockito.verify(database).insert(Mockito.eq(ShoppingListDatabase.PRODUCTS_TABLE_NAME), Mockito.any(), Mockito.any())
    }

    @Test
    fun testSaveProductsForList() {
        repository.saveProductsForList(1, listOf(Product(name = "test", amount = 1)))

        Mockito.verify(database).insert(Mockito.eq(ShoppingListDatabase.JUNCTION_LIST_PRODUCT_TABLE_NAME), Mockito.any(), Mockito.any())
    }

}