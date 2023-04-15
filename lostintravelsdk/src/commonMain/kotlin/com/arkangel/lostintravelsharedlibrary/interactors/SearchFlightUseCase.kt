package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.SearchFlightQuery
import com.arkangel.lostintravelsharedlibrary.type.FlightSearch
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class SearchFlightUseCase: BaseUseCase<List<SearchFlightQuery.Response>, FlightSearch> {
    override fun execute(input: FlightSearch) = makeFlow(input, getApiService()::searchFlight)
}