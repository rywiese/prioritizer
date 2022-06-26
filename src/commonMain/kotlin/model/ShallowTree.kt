package model

data class ShallowTree(
    override val id: String,
    override val node: Node,
    override val childIds: Set<String>
) : Tree
