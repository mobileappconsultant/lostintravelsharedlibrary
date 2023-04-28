package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.datasource.persistence.SDKSettings

class UserLoggedInUseCase {
    fun isLoggedIn(): Boolean = SDKSettings.getToken().isNotEmpty()
}