package ry.prioritizer.neo4j.model

import ry.prioritizer.model.Category

data class Neo4JCategory(
    override val id: String,
    override val name: String
) : Category
