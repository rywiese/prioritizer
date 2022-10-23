package ry.prioritizer.neo4j

import org.neo4j.driver.types.Node
import ry.prioritizer.neo4j.model.Neo4JItem

object ItemParser : ValueParser<Neo4JItem> {

    override fun parse(node: Node): Neo4JItem = Neo4JItem(
        id = node.id().toString(),
        name = node["name"].asString(),
        price = node["price"].asDouble(),
        link = node["link"].asString()
    )

}
