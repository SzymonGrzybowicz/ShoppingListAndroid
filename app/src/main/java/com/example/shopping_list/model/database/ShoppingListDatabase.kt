package com.example.shopping_list.model.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.base.log.Log

class ShoppingListDatabase(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "onCreate()")
        db.beginTransaction()
        db.execSQL(CREATE_LISTS_TABLE_QUERY)
        db.execSQL(CREATE_PRODUCT_TABLE_QUERY)
        db.execSQL(CREATE_JUNCTION_LIST_PRODUCT_TABLE_QUERY)
        db.setTransactionSuccessful()
        db.endTransaction()

        db.beginTransaction()
        db.execSQL(CREATE_INDEX_JUNCTION_LIST)
        db.setTransactionSuccessful()
        db.endTransaction()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "onUpgrade($db, $oldVersion, $newVersion)")
    }

    companion object {
        var database: SQLiteDatabase? = null
            private set

        fun open(context: Context) {
            if (database == null)
                database = ShoppingListDatabase(context).writableDatabase
        }

        fun release() {
            if (database != null) {
                database!!.close()
                database = null
            }
        }

        const val LISTS_TABLE_NAME = "SHOPPING_LISTS"
        const val LISTS_KEY_ID = "_id"
        const val LISTS_KEY_NAME = "name"
        const val LISTS_KEY_STATUS = "status"

        const val PRODUCTS_TABLE_NAME = "PRODUCTS"
        const val PRODUCTS_KEY_ID = "_id"
        const val PRODUCTS_KEY_NAME = "name"

        const val JUNCTION_LIST_PRODUCT_TABLE_NAME = "JUNCTION_LIST_PRODUCT"
        const val JUNCTION_KEY_ID = "_id"
        const val JUNCTION_KEY_LIST_ID = "list_id"
        const val JUNCTION_KEY_PRODUCT_ID = "product_id"
        const val JUNCTION_KEY_AMOUNT = "amount"
        private const val JUNCTION_INDEX_LIST = "JUNCTION_INDEX_LIST"

        private const val TAG = "ShoppingListDatabase"
        private const val DATABASE_NAME = "SHOPPING_LIST_DATABASE"
        private const val DATABASE_VERSION = 1

        private const val CREATE_LISTS_TABLE_QUERY = "CREATE TABLE $LISTS_TABLE_NAME (" +
                "$LISTS_KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$LISTS_KEY_NAME TEXT NOT NULL, " +
                "$LISTS_KEY_STATUS TEXT NOT NULL);"

        private const val CREATE_PRODUCT_TABLE_QUERY = "CREATE TABLE $PRODUCTS_TABLE_NAME (" +
                "$PRODUCTS_KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$PRODUCTS_KEY_NAME TEXT NOT NULL);"

        private const val CREATE_JUNCTION_LIST_PRODUCT_TABLE_QUERY = "CREATE TABLE $JUNCTION_LIST_PRODUCT_TABLE_NAME (" +
                "$JUNCTION_KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$JUNCTION_KEY_LIST_ID INTEGER NOT NULL, " +
                "$JUNCTION_KEY_PRODUCT_ID INTEGER NOT NULL, " +
                "$JUNCTION_KEY_AMOUNT INTEGER NOT NULL, " +
                "FOREIGN KEY ($JUNCTION_KEY_LIST_ID) REFERENCES $LISTS_TABLE_NAME($LISTS_KEY_ID), " +
                "FOREIGN KEY ($JUNCTION_KEY_PRODUCT_ID) REFERENCES $PRODUCTS_TABLE_NAME($PRODUCTS_KEY_ID));"

        private const val CREATE_INDEX_JUNCTION_LIST = "CREATE INDEX $JUNCTION_INDEX_LIST ON $JUNCTION_LIST_PRODUCT_TABLE_NAME ($JUNCTION_KEY_LIST_ID);"
    }
}