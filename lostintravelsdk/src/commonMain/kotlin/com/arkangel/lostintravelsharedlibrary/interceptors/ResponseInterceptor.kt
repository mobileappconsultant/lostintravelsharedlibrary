package com.arkangel.lostintravelsharedlibrary.interceptors

import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain

class ResponseInterceptor : HttpInterceptor {
    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain
    ): HttpResponse {
        val httpResponse = chain.proceed(request)

        val body = httpResponse.body?.readByteString()
        if (body != null) {
            println("[[Our own Logs: ]]: ${body.utf8()}")
        }

        return HttpResponse.Builder(statusCode = httpResponse.statusCode)
            .also { if (body != null) it.body(body) }
            .addHeaders(httpResponse.headers)
            .build()
    }
}