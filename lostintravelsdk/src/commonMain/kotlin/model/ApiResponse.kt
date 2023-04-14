package model

import com.apollographql.apollo3.api.Error


data class ApiResponse<T>(

    val statusCode: Int? = null,


    val errors: List<Error>?,


    val data: T? = null,


    val error: Boolean
)