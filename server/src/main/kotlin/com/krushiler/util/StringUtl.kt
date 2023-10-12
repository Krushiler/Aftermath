package com.krushiler.util

import java.util.UUID

fun generateUUID(): String {
    return UUID.randomUUID().toString()
}