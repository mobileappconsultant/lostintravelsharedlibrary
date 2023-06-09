package com.arkangel.lostintravelsharedlibrary.interactors

import com.apollographql.apollo3.api.Error
import com.arkangel.lostintravelsharedlibrary.datasource.network.Service
import com.arkangel.lostintravelsharedlibrary.datasource.network.ServiceImpl
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState
import com.arkangel.lostintravelsharedlibrary.util.asCommonFlow
import kotlinx.coroutines.flow.flow
import model.ApiResponse

internal interface BaseUseCase<Response, Input> {

    fun execute(input: Input): CommonFlow<DataState<Response>>

    fun getApiService(): Service = ServiceImpl()

    fun makeFlow(
        input: Input,
        responseMaker: suspend (input: Input) -> ApiResponse<Response>
    ):
            CommonFlow<DataState<Response>> = flow {
        try {
            emit(DataState.loading())

            val response = responseMaker(input)

            if (response.error) {
                emit(DataState.error(errors = response.errors))
            } else {
                emit(
                    DataState.data(
                        errors = response.errors,
                        data = response.data,
                        success = true
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                DataState.error(
                    errors = listOf(
                        Error(
                            e.message ?: "An error occurred", null, null, null, null
                        )
                    )
                )
            )
        }
    }.asCommonFlow()
}