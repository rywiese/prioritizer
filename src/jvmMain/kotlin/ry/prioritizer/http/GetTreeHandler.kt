package ry.prioritizer.http

import api.PrioritizerApi
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import model.Tree
import ry.prioritizer.ktor.queryParameterOrThrow
import ry.prioritizer.ktor.respondJson
import serialization.JsonSerializer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTreeHandler @Inject constructor(
    private val prioritizerApi: PrioritizerApi,
    private val treeSerializer: JsonSerializer<Tree>
) : KtorHandler() {

    override suspend fun PipelineContext<Unit, ApplicationCall>.handle() {
        prioritizerApi
            .getTree(
                categoryId = call.parameters["categoryId"]!!,
                maxDepth = call.queryParameterOrThrow("maxDepth").toInt()
            )
            ?.let { tree: Tree ->
                call.respondJson(tree, treeSerializer)
            }
            ?: call.respond(HttpStatusCode.NotFound)
    }

}