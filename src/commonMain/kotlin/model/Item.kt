package model

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

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

    companion object {

        fun fromJson(json: JsonElement): Item = fromJson(json.jsonObject)

        fun fromJson(json: JsonObject): Item = object : Item {
            override val id: String = json["id"]!!.jsonPrimitive.content
            override val name: String = json["name"]!!.jsonPrimitive.content
            override val price: Double = json["price"]!!.jsonPrimitive.double
            override val link: String = json["link"]!!.jsonPrimitive.content
        }

    }

}
