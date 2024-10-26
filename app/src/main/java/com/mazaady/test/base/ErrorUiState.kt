package com.mazaady.test.base

sealed interface ErrorUiState {
    data class NoInternet(val message: String) : ErrorUiState
    data class UnAuthException(val message: String) : ErrorUiState
    data class Server(val message: String) : ErrorUiState
    data class EmptyResponse(val message: String) : ErrorUiState
}