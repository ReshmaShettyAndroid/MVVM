package com.example.mvvm.network

import com.example.mvvm.model.LoginRequest
import com.example.mvvm.model.LoginResponse
import com.example.mvvm.utils.ApiUrlConstants.LOGIN
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST(LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}
