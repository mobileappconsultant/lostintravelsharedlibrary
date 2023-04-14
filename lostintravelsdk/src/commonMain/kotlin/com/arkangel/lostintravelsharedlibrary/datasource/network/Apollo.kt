package com.arkangel.lostintravelsharedlibrary.datasource.network


import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import com.apollographql.apollo3.network.http.LoggingInterceptor
import com.arkangel.lostintravelsharedlibrary.interceptors.ResponseInterceptor

class Apollo(token: String) {
    private  val SERVER_URL = "https://ldabs6rj.connect.remote.it/graphql"

    val apolloClient = ApolloClient.Builder()
        .networkTransport(
            HttpNetworkTransport.Builder().addInterceptor(
                interceptor = AuthorizationInterceptor(token)
            ).addInterceptor(ResponseInterceptor())
                .serverUrl(SERVER_URL)
                .build()
        )
        .build()
}

class AuthorizationInterceptor(val token: String) : HttpInterceptor {
    private val AUTHORIZATION = "Authorization"

    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain
    ): HttpResponse {
        return chain.proceed(request.newBuilder().addHeader(AUTHORIZATION, token).build())
    }
}