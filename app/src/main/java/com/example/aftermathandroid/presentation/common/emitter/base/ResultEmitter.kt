package com.example.aftermathandroid.presentation.common.emitter.base

import kotlinx.coroutines.flow.MutableStateFlow

abstract class ResultEmitter<T> {
    private val _resultFlow = MutableStateFlow<T?>(null)

    fun emit(result: T) {
        _resultFlow.value = result
    }

    suspend fun subscribe(onResult: (T) -> Unit): Nothing = _resultFlow.collect {
        if (it != null) {
            onResult(it)
            _resultFlow.value = null
        }
    }
}