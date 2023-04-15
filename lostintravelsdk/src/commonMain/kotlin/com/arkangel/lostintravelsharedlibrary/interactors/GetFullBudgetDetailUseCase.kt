package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.GetFullBudgetDetailQuery
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState
import model.ApiResponse

class GetFullBudgetDetailUseCase: BaseUseCase<GetFullBudgetDetailQuery.Response, Unit?> {
    private suspend fun helper(input: Unit?): ApiResponse<GetFullBudgetDetailQuery.Response> {
        return getApiService().getFullBudgetDetail()
    }

    override fun execute(input: Unit?) = makeFlow(input, ::helper)
}