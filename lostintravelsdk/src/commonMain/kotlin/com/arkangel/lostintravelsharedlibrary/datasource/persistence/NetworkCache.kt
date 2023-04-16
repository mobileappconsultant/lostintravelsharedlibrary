package com.arkangel.lostintravelsharedlibrary.datasource.persistence

import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory

object NetworkCache {
    val cache = SqlNormalizedCacheFactory("lost_in_travel_cache.db")
}