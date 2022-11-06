package ry.prioritizer.ktor

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.response.respond
import serialization.JsonSerializer

fun ApplicationCall.queryParameterOrThrow(
    name: String
): String =
    request.queryParameters[name]
        ?: throw BadRequestException("$name query parameter required")

suspend fun <T> ApplicationCall.respondJson(
    t: T,
    serializer: JsonSerializer<T>
) {
    respondJson(HttpStatusCode.OK, t, serializer)
}

suspend fun <T> ApplicationCall.respondJson(
    status: HttpStatusCode,
    t: T,
    serializer: JsonSerializer<T>
) {
    respond(status, serializer.toJson(t))
}
