package serialization

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import model.Category

object CategorySerializer : JsonObjectSerializer<Category> {

    override fun toJsonObject(t: Category): JsonObject =
        JsonObject(
            mapOf(
                "id" to JsonPrimitive(t.id),
                "name" to JsonPrimitive(t.name)
            )
        )

    override fun fromJsonObject(json: JsonObject): Category =
        object : Category {
            override val id: String = json["id"]!!.jsonPrimitive.content
            override val name: String = json["name"]!!.jsonPrimitive.content
        }

}
