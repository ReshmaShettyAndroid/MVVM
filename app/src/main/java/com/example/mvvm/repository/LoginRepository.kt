package com.example.mvvm.repository

import com.example.mvvm.model.LoginRequest
import com.example.mvvm.model.LoginResponse
import com.example.mvvm.network.ApiServiceImpl
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {
    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return apiServiceImpl.login(loginRequest)
    }
}