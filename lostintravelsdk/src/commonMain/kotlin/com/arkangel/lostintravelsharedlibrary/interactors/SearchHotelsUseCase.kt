package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.SearchHotelsQuery
import com.arkangel.lostintravelsharedlibrary.type.HotelQueryInput
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class SearchHotelsUseCase: BaseUseCase<List<SearchHotelsQuery.Response>, HotelQueryInput> {
    override fun execute(input: HotelQueryInput): CommonFlow<DataState<List<SearchHotelsQuery.Response>>> =
        makeFlow(input, getApiService()::searchHotels)
}