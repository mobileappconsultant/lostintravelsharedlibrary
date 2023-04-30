package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.BookHotelMutation
import com.arkangel.lostintravelsharedlibrary.type.HotelGuestBookingInput
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class BookHotelUseCase: BaseUseCase<BookHotelMutation.Response, HotelGuestBookingInput> {
    override fun execute(input: HotelGuestBookingInput): CommonFlow<DataState<BookHotelMutation.Response>> =
        makeFlow(input, getApiService()::bookHotel)
}