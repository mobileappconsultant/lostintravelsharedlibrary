package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.GetCountryListQuery
import model.ApiResponse

class GetCountryListUseCase: BaseUseCase<List<GetCountryListQuery.Response>, Unit?> {
    private suspend fun helper(input: Unit?): ApiResponse<List<GetCountryListQuery.Response>> {
        return getApiService().getCountryList()
    }

    override fun execute(input: Unit?) = makeFlow(input, ::helper)
}