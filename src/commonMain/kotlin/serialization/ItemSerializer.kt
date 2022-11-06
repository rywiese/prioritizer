package serialization

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive
import model.Item

object ItemSerializer : JsonObjectSerializer<Item> {

    override fun toJsonObject(t: Item): JsonObject =
        JsonObject(
            mapOf(
                "id" to JsonPrimitive(t.id),
                "name" to JsonPrimitive(t.name),
                "price" to JsonPrimitive(t.price),
                "link" to JsonPrimitive(t.link)
            )
        )

    override fun fromJsonObject(json: JsonObject): Item =
        object : Item {
            override val id: String = json["id"]!!.jsonPrimitive.content
            override val name: String = json["name"]!!.jsonPrimitive.content
            override val price: Double = json["price"]!!.jsonPrimitive.double
            override val link: String = json["link"]!!.jsonPrimitive.content
        }

}
