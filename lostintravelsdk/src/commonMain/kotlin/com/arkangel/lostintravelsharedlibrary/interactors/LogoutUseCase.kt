package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.datasource.persistence.SDKSettings

class LogoutUseCase {
    fun execute() {
        SDKSettings.removeToken()
    }
}