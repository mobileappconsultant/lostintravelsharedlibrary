package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.GetCurrencyListQuery
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState
import model.ApiResponse

class GetCurrencyListUseCase: BaseUseCase<List<GetCurrencyListQuery.Response>, Unit?> {
    private suspend fun helper(input: Unit?): ApiResponse<List<GetCurrencyListQuery.Response>> {
        return getApiService().getCurrencyList()
    }

    override fun execute(input: Unit?) = makeFlow(input, ::helper)
}