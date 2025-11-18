package com.picpay.desafio.android.ui

sealed class ViewState<out T> {
    data class Success<T>(val data: T) : ViewState<T>()
    data class Error(val throwable: Throwable) : ViewState<Nothing>()
    data object Loading : ViewState<Nothing>()
}
