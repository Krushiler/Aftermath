package com.example.aftermathandroid.presentation.common.model

data class ErrorModel(
    val message: String,
) {
    companion object {
        fun fromException(e: Exception): ErrorModel = ErrorModel(e.localizedMessage ?: "Something went wrong")
    }
}