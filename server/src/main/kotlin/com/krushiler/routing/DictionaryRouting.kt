package com.krushiler.routing

import com.krushiler.domain.interactor.DictionaryInteractor
import com.krushiler.plugins.authenticateBearer
import com.krushiler.util.pagingData
import com.krushiler.util.userLogin
import data.request.CreateDictionaryRequest
import data.request.UpdateDictionaryRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.dictionaryRouting() = route("/dictionary") {
    val dictionaryInteractor: DictionaryInteractor by inject()
    authenticateBearer {
        get("/all") {
            try {
                call.respond(dictionaryInteractor.getDictionaries(call.pagingData))
            } catch (e: Exception) {
                call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
            }
        }
        get("/my") {
            try {
                call.respond(dictionaryInteractor.getUserDictionaries(call.userLogin, call.pagingData))
            } catch (e: Exception) {
                call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
            }
        }
        get("/{id}") {
            try {
                val dictionary = dictionaryInteractor.getDictionary(call.parameters["id"]!!)
                dictionary?.let {
                    call.respond(it)
                } ?: call.respondText("Dictionary not found", status = HttpStatusCode.NotFound)
            } catch (e: Exception) {
                call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
            }
        }
        post("/create") {
            try {
                val request = call.receive<CreateDictionaryRequest>()
                call.respond(dictionaryInteractor.createDictionary(call.userLogin, request.name, request.description))
            } catch (e: Exception) {
                call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
            }
        }
        post("/edit") {
            try {
                val request = call.receive<UpdateDictionaryRequest>()
                val dictionary = dictionaryInteractor.updateDictionary(
                    request.dictionaryId, call.userLogin, request.name, request.description, request.terms
                )
                dictionary?.let {
                    call.respond(it)
                } ?: call.respondText("Dictionary not found", status = HttpStatusCode.NotFound)
            } catch (e: Exception) {
                call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
            }
        }
    }
}