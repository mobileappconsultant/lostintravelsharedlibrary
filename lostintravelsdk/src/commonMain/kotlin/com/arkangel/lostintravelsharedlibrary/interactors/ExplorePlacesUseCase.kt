package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.ExplorePlacesQuery
import com.arkangel.lostintravelsharedlibrary.type.QueryInput
import model.ApiResponse

class ExplorePlacesUseCase:
    BaseUseCase<List<ExplorePlacesQuery.Response>, QueryInput> {

    private suspend fun helper(input: QueryInput): ApiResponse<List<ExplorePlacesQuery.Response>> {
        return getApiService().explorePlaces(input)
    }

    override fun execute(input: QueryInput) = makeFlow(input, ::helper)
}