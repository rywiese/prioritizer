package ry.prioritizer.ktor

import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.BadRequestException

fun ApplicationCall.queryParameterOrThrow(
    name: String
): String =
    request.queryParameters[name]
        ?: throw BadRequestException("$name query parameter required")
