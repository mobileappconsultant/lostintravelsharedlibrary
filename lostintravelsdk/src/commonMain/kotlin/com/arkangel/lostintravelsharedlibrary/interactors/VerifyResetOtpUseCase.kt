package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.VerifyResetOtpMutation
import com.arkangel.lostintravelsharedlibrary.type.VerifyOtp
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class VerifyResetOtpUseCase: BaseUseCase<VerifyResetOtpMutation.Response, VerifyOtp> {
    override fun execute(input: VerifyOtp) = makeFlow(input, getApiService()::verifyResetOtp)
}