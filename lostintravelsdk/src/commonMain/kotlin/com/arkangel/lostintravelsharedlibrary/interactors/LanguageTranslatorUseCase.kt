package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.LanguageTranslatorMutation
import com.arkangel.lostintravelsharedlibrary.type.LanguageTranslate
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class LanguageTranslatorUseCase: BaseUseCase<LanguageTranslatorMutation.Response, LanguageTranslate> {
    override fun execute(input: LanguageTranslate) =
        makeFlow(input, getApiService()::languageTranslator)
}