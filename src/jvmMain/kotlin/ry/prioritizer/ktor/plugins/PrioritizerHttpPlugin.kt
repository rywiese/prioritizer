package ry.prioritizer.ktor.plugins

import api.PrioritizerApi
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.json.JsonElement
import ry.prioritizer.ktor.KtorPlugin
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrioritizerHttpPlugin @Inject constructor(
    private val prioritizerApi: PrioritizerApi
) : KtorPlugin() {

    override fun Application.configure() {
        routing {
            get("tree") {
                val maxDepth: Int = call.request.queryParameters["maxDepth"]?.toInt()
                    ?: throw BadRequestException("maxDepth query parameter required")
                val response: JsonElement? = prioritizerApi.getRoot(maxDepth)?.toJson()
                if (response != null) {
                    call.respond(HttpStatusCode.OK, response)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            get("tree/{categoryId}") {
                val maxDepth: Int = call.request.queryParameters["maxDepth"]?.toInt()
                    ?: throw BadRequestException("maxDepth query parameter required")
                val categoryId: String = call.parameters["categoryId"]!!
                val response: JsonElement? = prioritizerApi.getTree(categoryId, maxDepth)?.toJson()
                if (response != null) {
                    call.respond(HttpStatusCode.OK, response)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }

}
