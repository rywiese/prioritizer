package ry.prioritizer.ktor.plugins

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondBytes
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import ry.prioritizer.PrioritizerApi
import ry.prioritizer.ktor.KtorPlugin
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrioritizerHttpPlugin @Inject constructor(
    private val prioritizerApi: PrioritizerApi,
    private val objectMapper: ObjectMapper
) : KtorPlugin() {

    override fun Application.configure() {
        routing {
            get("tree") {
                val response: JsonNode? = prioritizerApi.getRoot()?.toJson()
                if (response != null) {
                    call.respondBytes(
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.OK
                    ) {
                        objectMapper.writeValueAsBytes(response)
                    }
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            get("tree/{categoryId}") {
                val categoryId: String = call.parameters["categoryId"]!!
                val response: JsonNode? = prioritizerApi.getTree(categoryId)?.toJson()
                if (response != null) {
                    call.respondBytes(
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.OK
                    ) {
                        objectMapper.writeValueAsBytes(response)
                    }
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }

}
