package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.SearchCitiesQuery
import com.arkangel.lostintravelsharedlibrary.type.CitySearch
import model.ApiResponse

class SearchCitiesUseCase:
    BaseUseCase<List<SearchCitiesQuery.Response>, CitySearch> {

    private suspend fun helper(input: CitySearch): ApiResponse<List<SearchCitiesQuery.Response>> {
        return getApiService().searchCities(input)
    }

    override fun execute(input: CitySearch) = makeFlow(input, ::helper)
}