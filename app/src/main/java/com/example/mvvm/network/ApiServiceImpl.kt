package com.example.mvvm.network

import com.example.mvvm.model.LoginRequest
import com.example.mvvm.model.LoginResponse
import retrofit2.http.Body
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse = apiService.login(loginRequest)
}