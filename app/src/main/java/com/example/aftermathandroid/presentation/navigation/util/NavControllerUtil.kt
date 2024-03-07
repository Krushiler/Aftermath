package com.example.aftermathandroid.presentation.navigation.util

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavOptionsBuilder.switch(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive = true
    }
}

fun NavOptionsBuilder.root() {
    popUpTo(0) {}
}