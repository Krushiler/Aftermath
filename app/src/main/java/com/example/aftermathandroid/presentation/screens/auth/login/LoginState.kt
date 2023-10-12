package com.example.aftermathandroid.presentation.screens.auth.login

data class LoginState(
    val login: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
)