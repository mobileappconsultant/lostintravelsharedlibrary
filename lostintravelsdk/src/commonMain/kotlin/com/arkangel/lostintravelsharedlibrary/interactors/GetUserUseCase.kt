package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.GetUserQuery
import model.ApiResponse

class GetUserUseCase: BaseUseCase<GetUserQuery.Response, Unit?> {
    private suspend fun helper(input: Unit?): ApiResponse<GetUserQuery.Response> {
        return getApiService().getUser()
    }
    override fun execute(input: Unit?) = makeFlow(input, ::helper)

}