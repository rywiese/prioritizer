package ry.prioritizer.model

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory

@GraphQLIgnore
interface Item {

    val id: String

    val name: String

    val price: Double

    val link: String

    fun toJson(): JsonNode = JsonNodeFactory.instance.objectNode()
        .put("id", id)
        .put("name", name)
        .put("price", price)
        .put("link", link)

}
