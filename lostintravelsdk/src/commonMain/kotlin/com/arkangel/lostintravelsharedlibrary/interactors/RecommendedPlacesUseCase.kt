package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.RecommendedPlacesQuery
import model.ApiResponse

class RecommendedPlacesUseCase:
    BaseUseCase<List<RecommendedPlacesQuery.Response>, Unit> {

    private suspend fun helper(input: Unit): ApiResponse<List<RecommendedPlacesQuery.Response>> {
        return getApiService().recommendedPlaces()
    }

    override fun execute(input: Unit) = makeFlow(input, ::helper)
}