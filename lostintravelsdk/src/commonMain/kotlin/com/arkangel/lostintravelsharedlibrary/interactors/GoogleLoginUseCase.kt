package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.GoogleLoginMutation
import com.arkangel.lostintravelsharedlibrary.type.GoogleAuth
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class GoogleLoginUseCase: BaseUseCase<GoogleLoginMutation.Response, GoogleAuth> {
    override fun execute(input: GoogleAuth) = makeFlow(input, getApiService()::googleLogin)
}