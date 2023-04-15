package com.arkangel.lostintravelsharedlibrary.datasource.network

import com.arkangel.lostintravelsharedlibrary.datasource.cache.LostInTravelDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(LostInTravelDatabase.Schema, "ccache.db")
    }
}