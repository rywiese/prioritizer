package serialization

import http.CreateSubcategoryRequest
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

object CreateSubcategoryRequestSerializer : JsonObjectSerializer<CreateSubcategoryRequest> {

    override fun toJsonObject(t: CreateSubcategoryRequest): JsonObject =
        JsonObject(JsonObject(mapOf("name" to JsonPrimitive(t.name))))

    override fun fromJsonObject(json: JsonObject): CreateSubcategoryRequest =
        CreateSubcategoryRequest(name = json["name"]!!.jsonPrimitive.content)

}
