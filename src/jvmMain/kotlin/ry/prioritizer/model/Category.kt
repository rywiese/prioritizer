package ry.prioritizer.model

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory

@GraphQLIgnore
interface Category {

    val id: String

    val name: String

    fun toJson(): JsonNode = JsonNodeFactory.instance.objectNode()
        .put("id", id)
        .put("name", name)

}
