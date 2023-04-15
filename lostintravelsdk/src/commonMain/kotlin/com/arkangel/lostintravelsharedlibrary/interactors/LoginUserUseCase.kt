package com.arkangel.lostintravelsharedlibrary.interactors


import com.arkangel.lostintravelsharedlibrary.LoginMutation
import com.arkangel.lostintravelsharedlibrary.type.Login
import kotlinx.coroutines.ExperimentalCoroutinesApi
import model.ApiResponse

@ExperimentalCoroutinesApi
class LoginUserUseCase: BaseUseCase<LoginMutation.Response, Login> {
    override fun execute(
        input: Login
    ) = makeFlow(input, getApiService()::loginUser)
}