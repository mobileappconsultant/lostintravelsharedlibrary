package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.CurrencyExchangeMutation
import com.arkangel.lostintravelsharedlibrary.type.CurrencyExchange
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class CurrencyExchangeUseCase: BaseUseCase<CurrencyExchangeMutation.Response, CurrencyExchange> {
    override fun execute(input: CurrencyExchange) =
        makeFlow(input, getApiService()::currencyExchange)
}