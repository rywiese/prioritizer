package model

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

interface Category {

    val id: String

    val name: String

    fun toJson(): JsonElement = JsonObject(
        mapOf(
            "id" to JsonPrimitive(id),
            "name" to JsonPrimitive(name)
        )
    )

    companion object {

        fun fromJson(json: JsonElement): Category = fromJson(json.jsonObject)

        fun fromJson(json: JsonObject): Category = object : Category {
            override val id: String = json["id"]!!.jsonPrimitive.content
            override val name: String = json["name"]!!.jsonPrimitive.content
        }

    }

}
