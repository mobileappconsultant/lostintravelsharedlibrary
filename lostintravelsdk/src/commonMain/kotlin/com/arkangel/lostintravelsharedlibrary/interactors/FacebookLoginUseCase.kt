package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.FacebookLoginMutation
import com.arkangel.lostintravelsharedlibrary.type.FacebookAuth
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class FacebookLoginUseCase: BaseUseCase<FacebookLoginMutation.Response, FacebookAuth> {
    override fun execute(input: FacebookAuth) = makeFlow(input, getApiService()::facebookLogin)
}