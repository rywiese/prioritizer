package ry.prioritizer.http

import api.PrioritizerApi
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import model.Category
import ry.prioritizer.ktor.respondJson
import serialization.JsonSerializer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteCategoryHandler @Inject constructor(
    private val prioritizerApi: PrioritizerApi,
    private val categorySerializer: JsonSerializer<Category>,
) : KtorHandler() {

    override suspend fun PipelineContext<Unit, ApplicationCall>.handle() {
        prioritizerApi
            .deleteCategory(
                categoryId = call.parameters["categoryId"]!!
            )
            ?.let { deletedCategoryId: String ->
                call.respondJson(
                    object : Category {
                        override val id: String = deletedCategoryId
                        override val name: String = "TODO... figure out how to get the whole deleted category!"
                    },
                    categorySerializer
                )
            }
            ?: call.respond(HttpStatusCode.NotFound)
    }

}
