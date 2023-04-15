package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.CreateUserMutation
import com.arkangel.lostintravelsharedlibrary.type.CreateUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import model.ApiResponse

@ExperimentalCoroutinesApi
class CreateUserUseCase : BaseUseCase<CreateUserMutation.Response, CreateUser> {
    override fun execute(input: CreateUser) = makeFlow(input, getApiService()::createUser)
}