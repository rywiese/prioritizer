package model

data class ShallowPriorityTree(
    override val id: String,
    override val node: Node,
    override val childIds: Set<String>
) : PriorityTree
