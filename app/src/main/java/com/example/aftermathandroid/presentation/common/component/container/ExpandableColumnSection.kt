package com.example.aftermathandroid.presentation.common.component.container

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun <T> ExpandableColumnSection(
    items: List<T>,
    expanded: Boolean,
    header: @Composable () -> Unit = {},
    itemBuilder: @Composable (item: T) -> Unit
) {

    Column {
        header()
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                items.forEach {
                    itemBuilder(it)
                }
            }
        }
    }
}