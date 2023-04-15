package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.CreateFlightOrderMutation
import com.arkangel.lostintravelsharedlibrary.type.FlightCreateOrder
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class CreateFlightOrderUseCase: BaseUseCase<CreateFlightOrderMutation.Response, FlightCreateOrder> {
    override fun execute(input: FlightCreateOrder) =
        makeFlow(input, getApiService()::createFlightOrder)

}