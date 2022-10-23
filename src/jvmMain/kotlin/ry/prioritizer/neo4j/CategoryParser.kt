package ry.prioritizer.neo4j

import org.neo4j.driver.types.Node

internal object CategoryParser : ValueParser<Neo4JCategory> {

    override fun parse(node: Node): Neo4JCategory = Neo4JCategory(
        id = node.id().toString(),
        name = node["name"].asString()
    )

}
