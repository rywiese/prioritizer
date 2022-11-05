package model

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

interface Tree {

    val category: Category

    val queue: List<Item>

    // TODO: Make this a Set.
    val children: List<Tree>

    fun toJson(): JsonElement = JsonObject(
        mapOf(
            "category" to category.toJson(),
            "queue" to JsonArray(queue.map(Item::toJson)),
            "children" to JsonArray(children.map(Tree::toJson))
        )
    )

    companion object {

        fun fromJson(json: JsonElement): Tree = fromJson(json.jsonObject)

        fun fromJson(json: JsonObject): Tree = object : Tree {
            override val category: Category = Category.fromJson(json["category"]!!)
            override val queue: List<Item> = json["queue"]!!.jsonArray.map { json -> Item.fromJson(json) }
            override val children: List<Tree> = json["children"]!!.jsonArray.map { json -> Tree.fromJson(json) }
        }

    }

}
