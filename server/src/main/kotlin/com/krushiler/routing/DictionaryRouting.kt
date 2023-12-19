package com.krushiler.routing

import com.krushiler.domain.interactor.DictionaryInteractor
import com.krushiler.plugins.authenticateBearer
import com.krushiler.util.collectionId
import com.krushiler.util.dictionarySearchData
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
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.dictionaryRouting() = route("/dictionary") {
    val dictionaryInteractor: DictionaryInteractor by inject()
    authenticateBearer {
        get("/all") {
            try {
                call.respond(
                    dictionaryInteractor.getDictionaries(
                        call.pagingData,
                        call.dictionarySearchData,
                        call.userLogin,
                        call.collectionId
                    )
                )
            } catch (e: Exception) {
                call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
            }
        }
        get("/collection/{id}") {
            try {
                val collection = dictionaryInteractor.getCollectionInfo(call.parameters["id"]!!)
                call.respond(collection ?: throw IllegalArgumentException("Collection not found"))
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
                val dictionary = dictionaryInteractor.getDictionary(call.parameters["id"]!!, call.userLogin)
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
        delete("/{id}") {
            try {
                val dictionaryId = call.parameters["id"]!!
                call.respond(dictionaryInteractor.deleteDictionary(dictionaryId, call.userLogin))
            } catch (e: Exception) {
                call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
            }
        }
        patch("/{id}") {
            try {
                val request = call.receive<UpdateDictionaryRequest>()
                val dictionaryId = call.parameters["id"]!!
                val dictionary = dictionaryInteractor.updateDictionary(
                    dictionaryId,
                    call.userLogin,
                    request.name,
                    request.description,
                    request.terms,
                    call.userLogin
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