package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.SimilarPlacesRecommendationsQuery
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class SimilarPlacesRecommendationsUseCase: BaseUseCase<List<SimilarPlacesRecommendationsQuery.Response>, String> {
    override fun execute(input: String): CommonFlow<DataState<List<SimilarPlacesRecommendationsQuery.Response>>> =
        makeFlow(input, getApiService()::similarPlacesRecommendations)
}