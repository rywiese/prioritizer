package ry.prioritizer.neo4j

import ry.prioritizer.model.Category

internal data class Neo4JCategory(
    override val id: String,
    override val name: String
) : Category
