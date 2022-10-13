package ry.prioritizer.neo4j

import org.neo4j.driver.types.Node
import ry.prioritizer.schema.model.Category

object CategoryParser : ValueParser<Category> {

    override fun parse(node: Node): Category = Category(
        id = node.id().toString(),
        name = node["name"].asString()
    )

}
