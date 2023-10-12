package com.example.aftermathandroid.presentation.common.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.aftermathandroid.presentation.theme.Dimens

object Gap {
    @Composable
    fun Xxs() = Spacer(modifier = Modifier.size(Dimens.xxs))

    @Composable
    fun Xs() = Spacer(modifier = Modifier.size(Dimens.xs))

    @Composable
    fun Sm() = Spacer(modifier = Modifier.size(Dimens.sm))

    @Composable
    fun Md() = Spacer(modifier = Modifier.size(Dimens.md))

    @Composable
    fun Lg() = Spacer(modifier = Modifier.size(Dimens.lg))

    @Composable
    fun Xl() = Spacer(modifier = Modifier.size(Dimens.xl))

    @Composable
    fun Xxl() = Spacer(modifier = Modifier.size(Dimens.xxl))
}