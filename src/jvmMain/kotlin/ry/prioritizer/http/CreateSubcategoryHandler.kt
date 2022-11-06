package ry.prioritizer.http

import api.PrioritizerApi
import http.CreateSubcategoryRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import model.Category
import ry.prioritizer.ktor.receiveJson
import ry.prioritizer.ktor.respondJson
import serialization.JsonSerializer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateSubcategoryHandler @Inject constructor(
    private val prioritizerApi: PrioritizerApi,
    private val categorySerializer: JsonSerializer<Category>,
    private val createSubcategoryRequestSerializer: JsonSerializer<CreateSubcategoryRequest>
) : KtorHandler() {

    override suspend fun PipelineContext<Unit, ApplicationCall>.handle() {
        prioritizerApi
            .createSubcategory(
                parentId = call.parameters["categoryId"]!!,
                name = call.receiveJson(createSubcategoryRequestSerializer).name
            )
            ?.let { createdCategory: Category ->
                call.respondJson(createdCategory, categorySerializer)
            }
            ?: call.respond(HttpStatusCode.NotFound)
    }

}
