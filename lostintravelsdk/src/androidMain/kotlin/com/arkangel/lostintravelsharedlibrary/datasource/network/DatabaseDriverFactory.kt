package com.arkangel.lostintravelsharedlibrary.datasource.network

import android.content.Context
import com.arkangel.lostintravelsharedlibrary.datasource.cache.LostInTravelDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(LostInTravelDatabase.Schema, context, "ccache.db")
    }
}