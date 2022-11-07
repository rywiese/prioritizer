package serialization

import http.CreateItemRequest
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive

object CreateItemRequestSerializer : JsonObjectSerializer<CreateItemRequest> {

    override fun toJsonObject(t: CreateItemRequest): JsonObject =
        JsonObject(
            mapOf(
                "name" to JsonPrimitive(t.name),
                "price" to JsonPrimitive(t.price),
                "link" to JsonPrimitive(t.link)
            )
        )

    override fun fromJsonObject(json: JsonObject): CreateItemRequest =
        CreateItemRequest(
            name = json["name"]!!.jsonPrimitive.content,
            price = json["price"]!!.jsonPrimitive.double,
            link = json["link"]!!.jsonPrimitive.content,
        )

}
