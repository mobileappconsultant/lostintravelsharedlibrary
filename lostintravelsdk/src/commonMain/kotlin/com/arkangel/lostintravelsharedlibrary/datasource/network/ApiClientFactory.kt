package com.arkangel.lostintravelsharedlibrary.datasource.network

import io.ktor.client.*

expect class ApiClientFactory {
    fun build(): HttpClient
}