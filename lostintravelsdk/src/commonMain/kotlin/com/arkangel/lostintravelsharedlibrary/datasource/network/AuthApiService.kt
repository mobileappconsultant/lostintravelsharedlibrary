package com.arkangel.lostintravelsharedlibrary.datasource.network

import model.ApiResponse
import model.LoginResponse
import model.User
import model.UserModel

interface AuthApiService {
    suspend fun createUser(userModel: UserModel): ApiResponse<User>
    suspend fun loginUser(userModel: UserModel): ApiResponse<LoginResponse>

}