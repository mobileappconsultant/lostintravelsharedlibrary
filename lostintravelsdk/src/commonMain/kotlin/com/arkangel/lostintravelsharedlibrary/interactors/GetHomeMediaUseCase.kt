package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.HomeMediaQuery
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class GetHomeMediaUseCase: BaseUseCase<HomeMediaQuery.Response, Unit?> {

    private suspend fun helper(input: Unit?) = getApiService().homeMedia()

    override fun execute(input: Unit?): CommonFlow<DataState<HomeMediaQuery.Response>> =
        makeFlow(input, ::helper)
}