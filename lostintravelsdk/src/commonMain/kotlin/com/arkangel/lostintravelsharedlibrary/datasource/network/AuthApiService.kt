package com.arkangel.lostintravelsharedlibrary.datasource.network

import com.arkangel.lostintravelsharedlibrary.*
import com.arkangel.lostintravelsharedlibrary.type.*
import model.ApiResponse

interface AuthApiService {

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

    // Queries
    suspend fun getUser(): ApiResponse<GetUserQuery.Response>
    suspend fun getCurrencyList(): ApiResponse<List<GetCurrencyListQuery.Response>>
    suspend fun getCountryList(): ApiResponse<List<GetCountryListQuery.Response>>
    suspend fun getFullBudgetDetail(): ApiResponse<GetFullBudgetDetailQuery.Response>
    suspend fun searchFlight(model: FlightSearch): ApiResponse<List<SearchFlightQuery.Response>>

}