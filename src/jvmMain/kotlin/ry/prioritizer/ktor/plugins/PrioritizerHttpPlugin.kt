package ry.prioritizer.ktor.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.json.JsonElement
import ry.prioritizer.PrioritizerApi
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
                val response: JsonElement? = prioritizerApi.getRoot()?.toJson()
                if (response != null) {
                    call.respond(HttpStatusCode.OK, response)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            get("tree/{categoryId}") {
                val categoryId: String = call.parameters["categoryId"]!!
                val response: JsonElement? = prioritizerApi.getTree(categoryId)?.toJson()
                if (response != null) {
                    call.respond(HttpStatusCode.OK, response)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }

}
