package ry.prioritizer.http

import api.PrioritizerApi
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import kotlinx.serialization.json.JsonElement
import ry.prioritizer.ktor.queryParameterOrThrow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRootHandler @Inject constructor(
    private val prioritizerApi: PrioritizerApi
) : KtorHandler() {

    override suspend fun PipelineContext<Unit, ApplicationCall>.handle() {
        prioritizerApi
            .getRoot(
                maxDepth = call.queryParameterOrThrow("maxDepth").toInt()
            )
            ?.toJson()
            ?.let { response: JsonElement ->
                call.respond(HttpStatusCode.OK, response)
            }
            ?: call.respond(HttpStatusCode.NotFound)
    }

}
