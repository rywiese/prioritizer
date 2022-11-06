package serialization

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import model.Category
import model.Item
import model.Tree

class TreeSerializer(
    private val categorySerializer: JsonSerializer<Category>,
    private val queueSerializer: JsonSerializer<List<Item>>
) : JsonObjectSerializer<Tree> {

    override fun toJsonObject(t: Tree): JsonObject =
        JsonObject(
            mapOf(
                "category" to categorySerializer.toJson(t.category),
                "queue" to queueSerializer.toJson(t.queue),
                "children" to t.children
                    .map { child: Tree ->
                        toJson(child)
                    }
                    .let(::JsonArray)
            )
        )

    override fun fromJsonObject(json: JsonObject): Tree =
        object : Tree {
            override val category: Category = categorySerializer.fromJson(json["category"]!!)
            override val queue: List<Item> = queueSerializer.fromJson(json["queue"]!!)
            override val children: Set<Tree> = json["children"]!!.jsonArray.map(::fromJson).toSet()
        }

}
