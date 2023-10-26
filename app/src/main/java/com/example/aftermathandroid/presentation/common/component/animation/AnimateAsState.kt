package com.example.aftermathandroid.presentation.common.component.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable

@Composable
fun animateBoolAsFloatState(targetValue: Boolean, label: String = "BoolAsFloat") =
    animateFloatAsState(targetValue = if (targetValue) 1f else 0f, label = label)