package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.datasource.network.ServiceImpl
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState
import com.arkangel.lostintravelsharedlibrary.util.asCommonFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import model.User
import model.UserModel

@ExperimentalCoroutinesApi
class CreateUserUseCase {
    fun execute(
        userModel: UserModel
    ): CommonFlow<DataState<User>> = flow {
        try {
            emit(DataState.loading())

            val response = getApiService().createUser(userModel)

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
                        com.apollographql.apollo3.api.Error(
                            e.message ?: "An error occurred", null, null, null, null
                        )
                    )
                )
            )
        }
    }.asCommonFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    fun getApiService() = ServiceImpl()
}