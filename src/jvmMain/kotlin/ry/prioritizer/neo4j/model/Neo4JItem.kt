package ry.prioritizer.neo4j.model

import ry.prioritizer.model.Item

data class Neo4JItem(
    override val id: String,
    override val name: String,
    override val price: Double,
    override val link: String
) : Item
