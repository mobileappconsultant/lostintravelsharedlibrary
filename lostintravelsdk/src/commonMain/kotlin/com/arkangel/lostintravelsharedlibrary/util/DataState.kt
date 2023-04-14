package com.arkangel.lostintravelsharedlibrary.util

data class DataState<T>(
    val errors: List<com.apollographql.apollo3.api.Error>? = emptyList(),
    val data: T? = null,
    val isLoading: Boolean = false,
    val success: Boolean = false
) {

    companion object {

        fun <T> error(
            errors: List<com.apollographql.apollo3.api.Error>?,
        ): DataState<T> {
            return DataState(
                errors = errors, data = null, success = false
            )
        }

        fun <T> data(
            errors: List<com.apollographql.apollo3.api.Error>?,
            data: T? = null,
            success: Boolean = true
        ): DataState<T> {
            return DataState(
                data = data, success = success
            )
        }

        fun <T> loading() = DataState<T>(isLoading = true)
    }
}