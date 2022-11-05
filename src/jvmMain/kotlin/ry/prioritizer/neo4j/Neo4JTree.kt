package ry.prioritizer.neo4j

import model.Tree

internal data class Neo4JTree(
    override val category: Neo4JCategory,
    override val queue: List<Neo4JItem>,
    override val children: Set<Neo4JTree>
) : Tree
