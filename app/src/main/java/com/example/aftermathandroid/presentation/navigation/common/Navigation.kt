package com.example.aftermathandroid.presentation.navigation.common

import androidx.navigation.NavHostController

abstract class Navigation(val navController: NavHostController) {
    fun back() = if (navController.previousBackStackEntry != null) navController.popBackStack() else false
}