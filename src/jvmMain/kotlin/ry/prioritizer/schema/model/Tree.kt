package ry.prioritizer.schema.model

data class Tree(
    val category: Category,
    val queue: List<Item>,
    val children: List<Tree>,
)
