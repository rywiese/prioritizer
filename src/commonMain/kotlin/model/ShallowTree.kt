package model

data class ShallowTree(
    override val id: String,
    override val name: String,
    override val queue: List<Item>,
    override val childIds: Set<String>
) : Tree
