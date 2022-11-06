package ktor

import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import serialization.JsonSerializer

suspend fun <T> HttpRequestBuilder.setJsonBody(
    body: T,
    serializer: JsonSerializer<T>
) {
    contentType(ContentType.Application.Json)
    setBody(serializer.toJson(body))
}

suspend fun <T> HttpResponse.bodyFromJson(
    serializer: JsonSerializer<T>
): T =
    serializer.fromJson(body())
