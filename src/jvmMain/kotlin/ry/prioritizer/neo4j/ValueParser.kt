package ry.prioritizer.neo4j

import org.neo4j.driver.Value
import org.neo4j.driver.types.Node

interface ValueParser<T> {

    fun parse(value: Value): T = parse(value.asNode())

    fun parse(node: Node): T

}
