package com.arkangel.lostintravelsharedlibrary.datasource.network

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}