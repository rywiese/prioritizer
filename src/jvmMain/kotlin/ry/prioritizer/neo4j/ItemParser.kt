package ry.prioritizer.neo4j

import org.neo4j.driver.types.Node
import ry.prioritizer.schema.model.Item

object ItemParser : ValueParser<Item> {

    override fun parse(node: Node): Item = Item(
        id = node.id().toString(),
        name = node["name"].asString(),
        price = node["price"].asDouble(),
        link = node["link"].asString()
    )

}
