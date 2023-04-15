package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.ResetPasswordMutation
import com.arkangel.lostintravelsharedlibrary.type.ResetPassword
import model.ApiResponse

class ResetPasswordUseCase: BaseUseCase<ResetPasswordMutation.Response, ResetPassword> {
    override fun execute(input: ResetPassword) = makeFlow(input, getApiService()::resetPassword)
}