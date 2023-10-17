package com.krushiler.routing

import io.ktor.server.http.content.staticFiles
import io.ktor.server.routing.Routing
import java.io.File

fun Routing.staticRouting() {
    staticFiles("/static", File("static"))
}