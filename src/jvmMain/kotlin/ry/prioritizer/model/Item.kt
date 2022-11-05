package ry.prioritizer.model

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

interface Item {

    val id: String

    val name: String

    val price: Double

    val link: String

    fun toJson(): JsonElement = JsonObject(
        mapOf(
            "id" to JsonPrimitive(id),
            "name" to JsonPrimitive(name),
            "price" to JsonPrimitive(price),
            "link" to JsonPrimitive(link)
        )
    )

}
