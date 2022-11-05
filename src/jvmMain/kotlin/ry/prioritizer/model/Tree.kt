package ry.prioritizer.model

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode

interface Tree {

    val category: Category

    val queue: List<Item>

    // TODO: Make this a Set.
    val children: List<Tree>

    fun toJson(): JsonNode = JsonNodeFactory.instance.objectNode()
        .set<ObjectNode>("category", category.toJson())
        .set<ObjectNode>("queue", JsonNodeFactory.instance.arrayNode().apply {
            queue.forEach { item: Item ->
                add(item.toJson())
            }
        })
        .set<ObjectNode>("children", JsonNodeFactory.instance.arrayNode().apply {
            children.forEach { child: Tree ->
                add(child.toJson())
            }
        })

}
