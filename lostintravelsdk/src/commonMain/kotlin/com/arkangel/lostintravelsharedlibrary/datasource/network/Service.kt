package com.arkangel.lostintravelsharedlibrary.datasource.network

import com.arkangel.lostintravelsharedlibrary.*
import com.arkangel.lostintravelsharedlibrary.type.*
import model.ApiResponse

interface Service {

    // Mutations
    suspend fun createUser(model: CreateUser): ApiResponse<CreateUserMutation.Response>
    suspend fun loginUser(model: Login): ApiResponse<LoginMutation.Response>
    suspend fun forgotPassword(model: ForgotPassword): ApiResponse<ForgotPasswordMutation.Response>
    suspend fun resetPassword(model: ResetPassword): ApiResponse<ResetPasswordMutation.Response>
    suspend fun verifyEmail(model: VerifyOtp): ApiResponse<VerifyEmailMutation.Response>
    suspend fun verifyResetOtp(model: VerifyOtp): ApiResponse<VerifyResetOtpMutation.Response>
    suspend fun currencyExchange(model: CurrencyExchange): ApiResponse<CurrencyExchangeMutation.Response>
    suspend fun createNewBudget(model: CreateBudget): ApiResponse<CreateNewBudgetMutation.Response>
    suspend fun googleLogin(model: GoogleAuth): ApiResponse<GoogleLoginMutation.Response>
    suspend fun facebookLogin(model: FacebookAuth): ApiResponse<FacebookLoginMutation.Response>
    suspend fun languageTranslator(model: LanguageTranslate): ApiResponse<LanguageTranslatorMutation.Response>
    suspend fun confirmFlightOfferPricing(model: FlightOfferPriceInput): ApiResponse<ConfirmFlightOfferPricingMutation.Response>
    suspend fun createFlightOrder(model: FlightCreateOrder): ApiResponse<CreateFlightOrderMutation.Response>
    suspend fun bookHotel(model: HotelGuestBookingInput): ApiResponse<BookHotelMutation.Response>
    suspend fun updateUser(model: UpdateUserInput): ApiResponse<UpdateUserMutation.Response>

    // Queries
    suspend fun getUser(): ApiResponse<GetUserQuery.Response>
    suspend fun getCurrencyList(): ApiResponse<List<GetCurrencyListQuery.Response>>
    suspend fun getCountryList(): ApiResponse<List<GetCountryListQuery.Response>>
    suspend fun getFullBudgetDetail(): ApiResponse<GetFullBudgetDetailQuery.Response>
    suspend fun searchFlight(model: FlightSearch): ApiResponse<List<SearchFlightQuery.Response>>
    suspend fun searchCities(model: CitySearch): ApiResponse<List<SearchCitiesQuery.Response>>
    suspend fun explorePlaces(model: QueryInput): ApiResponse<List<ExplorePlacesQuery.Response>>
    suspend fun recommendedPlaces(): ApiResponse<List<RecommendedPlacesQuery.Response>>
    suspend fun searchHotels(model: HotelQueryInput): ApiResponse<List<SearchHotelsQuery.Response>>
    suspend fun checkHotelAvailability(model: HotelQueryDetailInput): ApiResponse<List<CheckHotelAvailabilityQuery.Response>>
    suspend fun similarPlacesRecommendations(model: String): ApiResponse<List<SimilarPlacesRecommendationsQuery.Response>>
    suspend fun homeMedia(): ApiResponse<HomeMediaQuery.Response>


}