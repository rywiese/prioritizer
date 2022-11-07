package ry.prioritizer.http

import api.PrioritizerApi
import http.CreateItemRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import model.Item
import ry.prioritizer.ktor.receiveJson
import ry.prioritizer.ktor.respondJson
import serialization.JsonSerializer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateItemHandler @Inject constructor(
    private val prioritizerApi: PrioritizerApi,
    private val itemSerializer: JsonSerializer<Item>,
    private val createItemRequestSerializer: JsonSerializer<CreateItemRequest>
) : KtorHandler() {

    override suspend fun PipelineContext<Unit, ApplicationCall>.handle() {
        prioritizerApi
            .createItem(
                categoryId = call.parameters["categoryId"]!!,
                createItemRequest = call.receiveJson(createItemRequestSerializer)
            )
            ?.let { item: Item ->
                call.respondJson(item, itemSerializer)
            }
            ?: call.respond(HttpStatusCode.NotFound)
    }

}
