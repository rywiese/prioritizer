package ktor

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.JsonElement
import serialization.JsonSerializer

suspend fun <T> HttpResponse.bodyFromJson(
    serializer: JsonSerializer<T>
): T =
    serializer.fromJson(body<JsonElement>())
