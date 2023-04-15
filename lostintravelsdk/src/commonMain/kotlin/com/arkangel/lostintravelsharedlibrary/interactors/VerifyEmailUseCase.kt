package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.VerifyEmailMutation
import com.arkangel.lostintravelsharedlibrary.type.VerifyOtp

class VerifyEmailUseCase: BaseUseCase<VerifyEmailMutation.Response, VerifyOtp> {
    override fun execute(input: VerifyOtp) = makeFlow(input, getApiService()::verifyEmail)
}