package com.arkangel.lostintravelsharedlibrary.datasource.network

import com.arkangel.lostintravelsharedlibrary.CreateNewUserMutation
import com.arkangel.lostintravelsharedlibrary.LoginUserMutation
import com.arkangel.lostintravelsharedlibrary.type.CreateUser
import com.arkangel.lostintravelsharedlibrary.type.LocationInput
import com.arkangel.lostintravelsharedlibrary.type.Login
import kotlinx.coroutines.ExperimentalCoroutinesApi
import model.*

@ExperimentalCoroutinesApi
class ServiceImpl() : AuthApiService {

    override suspend fun createUser(userModel: UserModel): ApiResponse<User> {
        val response = Apollo("").apolloClient.mutation(
            CreateNewUserMutation(
                CreateUser(
                    email = userModel.email,
                    full_name = userModel.fullName!!,
                    location = LocationInput(
                        userModel.location.latitude, userModel.location.longitude
                    ),
                    password = userModel.password!!
                )
            )
        ).execute()

        return ApiResponse(
            data = User(
                _id = response.data?.createNewUser?._id!!,
                country = response.data?.createNewUser?.country!!,
                created_at = response.data?.createNewUser?.created_at.toString(),
                email = response.data?.createNewUser?.email!!,
                full_name = response.data?.createNewUser?.full_name!!,
                is_password_reset = response.data?.createNewUser?.is_password_reset!!,
                is_verified = response.data?.createNewUser?.is_verified!!,
                location = Location(
                    longitude = response.data?.createNewUser?.location?.longitude!!,
                    latitude = response.data?.createNewUser?.location?.longitude!!
                ),
                updated_at = response.data?.createNewUser?.updated_at.toString(),
                password = ""
            ),
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors
        )
    }

    override suspend fun loginUser(userModel: UserModel): ApiResponse<LoginResponse> {
        val response = Apollo("").apolloClient.mutation(
            LoginUserMutation(
                Login(
                    email = userModel.email, password = userModel.password!!
                )
            )
        ).execute()


        return ApiResponse(
            data = LoginResponse(
                token = response.data?.loginUser?.token!!,
            ),
            error = response.errors.isNullOrEmpty().not(),
            errors = response.errors
        )
    }
}