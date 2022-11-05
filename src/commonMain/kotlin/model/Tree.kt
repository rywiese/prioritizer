package model

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

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

}
