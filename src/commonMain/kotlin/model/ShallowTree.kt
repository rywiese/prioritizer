package model

data class ShallowTree(
    override val id: String,
    override val node: Node,
    override val queue: List<Item>,
    override val childIds: Set<String>
) : Tree
