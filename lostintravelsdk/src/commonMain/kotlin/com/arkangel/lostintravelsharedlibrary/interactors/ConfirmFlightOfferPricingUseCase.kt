package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.ConfirmFlightOfferPricingMutation
import com.arkangel.lostintravelsharedlibrary.type.FlightOfferPriceInput
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class ConfirmFlightOfferPricingUseCase:
    BaseUseCase<ConfirmFlightOfferPricingMutation.Response, FlightOfferPriceInput> {
    override fun execute(input: FlightOfferPriceInput) =
        makeFlow(input, getApiService()::confirmFlightOfferPricing)
}