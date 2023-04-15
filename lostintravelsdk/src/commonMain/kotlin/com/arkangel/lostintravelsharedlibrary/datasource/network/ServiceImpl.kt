package com.arkangel.lostintravelsharedlibrary.datasource.network

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Mutation
import com.arkangel.lostintravelsharedlibrary.*
import com.arkangel.lostintravelsharedlibrary.type.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import model.ApiResponse

class ServiceImpl() : AuthApiService {

    private suspend fun <D : Mutation.Data> executeMutation(model: Mutation<D>): ApolloResponse<D> {
        return Apollo("").apolloClient.mutation(
            model
        ).execute()
    }

    private suspend fun <D : com.apollographql.apollo3.api.Query.Data> executeQuery(model: com.apollographql.apollo3.api.Query<D>): ApolloResponse<D> {
        return Apollo("").apolloClient.query(
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
        return ApiResponse(
            data = response.data?.response,
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors,
        )
    }

    override suspend fun facebookLogin(model: FacebookAuth): ApiResponse<FacebookLoginMutation.Response> {
        val response = executeMutation(FacebookLoginMutation(model))
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
}