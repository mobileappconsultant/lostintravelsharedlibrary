package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.ForgotPasswordMutation
import com.arkangel.lostintravelsharedlibrary.type.ForgotPassword
import model.ApiResponse

class ForgotPasswordUseCase: BaseUseCase<ForgotPasswordMutation.Response, ForgotPassword> {
    override fun execute(input: ForgotPassword) = makeFlow(input, getApiService()::forgotPassword)
}