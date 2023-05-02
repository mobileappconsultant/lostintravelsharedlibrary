package com.arkangel.lostintravelsharedlibrary.datasource.network

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.api.Optional
import com.arkangel.lostintravelsharedlibrary.*
import com.arkangel.lostintravelsharedlibrary.datasource.persistence.SDKSettings
import com.arkangel.lostintravelsharedlibrary.type.*
import model.ApiResponse

class ServiceImpl() : Service {

    private suspend fun <D : Mutation.Data> executeMutation(model: Mutation<D>): ApolloResponse<D> {
        return Apollo(SDKSettings.getToken()).apolloClient.mutation(
            model
        ).execute()
    }

    private suspend fun <D : com.apollographql.apollo3.api.Query.Data> executeQuery(model: com.apollographql.apollo3.api.Query<D>): ApolloResponse<D> {
        return Apollo(SDKSettings.getToken()).apolloClient.query(
            model
        ).execute()
    }

    override suspend fun createUser(model: CreateUser): ApiResponse<CreateUserMutation.Response> {
        val response = executeMutation(CreateUserMutation(model))

        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors
        )
    }

    override suspend fun loginUser(model: Login): ApiResponse<LoginMutation.Response> {

        val response = executeMutation(LoginMutation(model))

        response.data?.response?.let {
            SDKSettings.setToken(it.token)
        }

        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors
        )
    }

    override suspend fun forgotPassword(model: ForgotPassword): ApiResponse<ForgotPasswordMutation.Response> {
        val response = executeMutation(ForgotPasswordMutation(model))
        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun resetPassword(model: ResetPassword): ApiResponse<ResetPasswordMutation.Response> {
        val response = executeMutation(ResetPasswordMutation(model))
        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun verifyEmail(model: VerifyOtp): ApiResponse<VerifyEmailMutation.Response> {
        val response = executeMutation(VerifyEmailMutation(model))
        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun verifyResetOtp(model: VerifyOtp): ApiResponse<VerifyResetOtpMutation.Response> {
        val response = executeMutation(VerifyResetOtpMutation(model))
        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun currencyExchange(model: CurrencyExchange): ApiResponse<CurrencyExchangeMutation.Response> {
        val response = executeMutation(CurrencyExchangeMutation(model))
        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun createNewBudget(model: CreateBudget): ApiResponse<CreateNewBudgetMutation.Response> {
        val response = executeMutation(CreateNewBudgetMutation(model))
        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun googleLogin(model: GoogleAuth): ApiResponse<GoogleLoginMutation.Response> {
        val response = executeMutation(GoogleLoginMutation(model))

        response.data?.response?.let {
            SDKSettings.setToken(it.token)
        }

        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun facebookLogin(model: FacebookAuth): ApiResponse<FacebookLoginMutation.Response> {
        val response = executeMutation(FacebookLoginMutation(model))

        response.data?.response?.let {
            SDKSettings.setToken(it.token)
        }

        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun languageTranslator(model: LanguageTranslate): ApiResponse<LanguageTranslatorMutation.Response> {
        val response = executeMutation(LanguageTranslatorMutation(model))
        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun confirmFlightOfferPricing(model: FlightOfferPriceInput): ApiResponse<ConfirmFlightOfferPricingMutation.Response> {
        val response = executeMutation(ConfirmFlightOfferPricingMutation(model))
        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun createFlightOrder(model: FlightCreateOrder): ApiResponse<CreateFlightOrderMutation.Response> {
        val response = executeMutation(CreateFlightOrderMutation(model))
        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun bookHotel(model: HotelGuestBookingInput): ApiResponse<BookHotelMutation.Response> {
        val response = executeMutation(BookHotelMutation(model))

        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun updateUser(model: UpdateUserInput): ApiResponse<UpdateUserMutation.Response> {
        val response = executeMutation(UpdateUserMutation(model))

        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun getUser(): ApiResponse<GetUserQuery.Response> {
        val response = executeQuery(GetUserQuery())
        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun getCurrencyList(): ApiResponse<List<GetCurrencyListQuery.Response>> {
        val response = executeQuery(GetCurrencyListQuery())
        return ApiResponse(
            data = response.data?.responseFilterNotNull(),
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun getCountryList(): ApiResponse<List<GetCountryListQuery.Response>> {
        val response = executeQuery(GetCountryListQuery())
        return ApiResponse(
            data = response.data?.responseFilterNotNull(),
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun getFullBudgetDetail(): ApiResponse<GetFullBudgetDetailQuery.Response> {
        val response = executeQuery(GetFullBudgetDetailQuery())
        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun searchFlight(model: FlightSearch): ApiResponse<List<SearchFlightQuery.Response>> {
        val response = executeQuery(SearchFlightQuery(model))

        return ApiResponse(
            data = response.data?.responseFilterNotNull(),
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun searchCities(model: CitySearch): ApiResponse<List<SearchCitiesQuery.Response>> {
        val response = executeQuery(SearchCitiesQuery(model))

        return ApiResponse(
            data = response.data?.responseFilterNotNull(),
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun explorePlaces(model: QueryInput): ApiResponse<List<ExplorePlacesQuery.Response>> {
        val response = executeQuery(ExplorePlacesQuery(model))

        return ApiResponse(
            data = response.data?.responseFilterNotNull()?.map { it.copy(imageUrl = it.imageUrl?.replace("http://", "https://")) },
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun recommendedPlaces(): ApiResponse<List<RecommendedPlacesQuery.Response>> {
        val response = executeQuery(RecommendedPlacesQuery())

        return ApiResponse(
            data = response.data?.responseFilterNotNull()?.map { it.copy(imageUrl = it.imageUrl?.replace("http://", "https://")) },
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun searchHotels(model: HotelQueryInput): ApiResponse<List<SearchHotelsQuery.Response>> {
        val response = executeQuery(SearchHotelsQuery(model))

        return ApiResponse(
            data = response.data?.responseFilterNotNull(),
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun checkHotelAvailability(model: HotelQueryDetailInput): ApiResponse<List<CheckHotelAvailabilityQuery.Response>> {
        val response = executeQuery(CheckHotelAvailabilityQuery(model))

        return ApiResponse(
            data = response.data?.responseFilterNotNull(),
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun similarPlacesRecommendations(model: String): ApiResponse<List<SimilarPlacesRecommendationsQuery.Response>> {
        val response = executeQuery(SimilarPlacesRecommendationsQuery(model))

        return ApiResponse(
            data = response.data?.responseFilterNotNull(),
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun homeMedia(): ApiResponse<HomeMediaQuery.Response> {
        val response = executeQuery(HomeMediaQuery())

        return ApiResponse(
            data = response.data?.response,
            error = !response.errors.isNullOrEmpty(),
            errors = response.errors,
        )
    }
}