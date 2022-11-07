package ry.prioritizer.http

import api.PrioritizerApi
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import kotlinx.serialization.json.JsonObject
import model.Item
import ry.prioritizer.ktor.respondJson
import serialization.JsonSerializer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopItemHandler @Inject constructor(
    private val prioritizerApi: PrioritizerApi,
    private val itemSerializer: JsonSerializer<Item>
) : KtorHandler() {

    override suspend fun PipelineContext<Unit, ApplicationCall>.handle() {
        prioritizerApi
            .popItem(
                categoryId = call.parameters["categoryId"]!!
            )
            ?.let { item: Item ->
                call.respondJson(item, itemSerializer)
            }
            ?: call.respond(JsonObject(emptyMap()))
    }

}