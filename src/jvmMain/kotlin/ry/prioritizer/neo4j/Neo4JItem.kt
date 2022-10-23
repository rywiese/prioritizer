package ry.prioritizer.neo4j

import ry.prioritizer.model.Item

internal data class Neo4JItem(
    override val id: String,
    override val name: String,
    override val price: Double,
    override val link: String
) : Item
