package ry.prioritizer.neo4j.model

import ry.prioritizer.model.Tree

data class Neo4JTree(
    override val category: Neo4JCategory,
    override val queue: List<Neo4JItem>,
    override val children: List<Neo4JTree>
) : Tree
