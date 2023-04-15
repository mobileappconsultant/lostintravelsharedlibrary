package com.arkangel.lostintravelsharedlibrary.datasource.network

import com.arkangel.lostintravelsharedlibrary.datasource.cache.LostInTravelDatabase

internal class Database(factory: DatabaseDriverFactory) {
    private val database = LostInTravelDatabase(factory.createDriver())
    private val queries = database.lostInTravelDatabaseQueries

    internal fun clearLaunches() {
        queries.transaction {
            queries.removeAllLaunches()
        }
    }
}