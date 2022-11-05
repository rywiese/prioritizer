package ry.prioritizer.model

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory

interface Category {

    val id: String

    val name: String

    fun toJson(): JsonNode = JsonNodeFactory.instance.objectNode()
        .put("id", id)
        .put("name", name)

}
