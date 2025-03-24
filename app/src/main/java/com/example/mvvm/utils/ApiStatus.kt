package com.example.mvvm.utils

sealed class ApiStatus<out T> {
    object IdleState : ApiStatus<Nothing>()
    object Loading : ApiStatus<Nothing>()
    class Failure(val msg: String) : ApiStatus<Nothing>()
    class Success<out T>(val data: T) : ApiStatus<T>()
}
