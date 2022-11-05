package ry.prioritizer.neo4j

import model.Category

internal data class Neo4JCategory(
    override val id: String,
    override val name: String
) : Category
