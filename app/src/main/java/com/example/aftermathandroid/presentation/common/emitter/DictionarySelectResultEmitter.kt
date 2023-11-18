package com.example.aftermathandroid.presentation.common.emitter

import com.example.aftermathandroid.presentation.common.emitter.base.ResultEmitter
import data.dto.DictionaryInfoDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionarySelectResultEmitter @Inject constructor() : ResultEmitter<DictionaryInfoDto>()