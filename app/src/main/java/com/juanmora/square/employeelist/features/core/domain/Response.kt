package com.juanmora.square.employeelist.features.core.domain

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val exception: Throwable) : Response<Nothing>()
}
