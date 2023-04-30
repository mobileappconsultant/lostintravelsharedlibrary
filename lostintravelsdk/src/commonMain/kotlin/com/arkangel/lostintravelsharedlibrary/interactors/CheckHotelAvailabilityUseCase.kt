package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.CheckHotelAvailabilityQuery
import com.arkangel.lostintravelsharedlibrary.type.HotelQueryDetailInput
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class CheckHotelAvailabilityUseCase: BaseUseCase<List<CheckHotelAvailabilityQuery.Response>, HotelQueryDetailInput> {

    override fun execute(input: HotelQueryDetailInput): CommonFlow<DataState<List<CheckHotelAvailabilityQuery.Response>>> =
        makeFlow(input, getApiService()::checkHotelAvailability)
}