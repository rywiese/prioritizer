package model

data class ShallowTree(
    override val id: String,
    override val name: String,
    override val queue: List<Item>,
    override val parentId: String?,
    override val childIds: Set<String>
) : Tree {

    override fun subTree(childId: String): Tree? = null

    override fun limitDepth(depth: Int): Tree = this

    override fun pop(): Pair<Tree, Item?> {
        val item: Item? = queue.firstOrNull()
        val tree: Tree = if (item == null) this else copy(
            queue = queue.drop(1)
        )
        return tree to item
    }

    override fun promote(childId: String): Tree = this

    constructor(
        tree: Tree
    ) : this(
        id = tree.id,
        name = tree.name,
        queue = tree.queue,
        parentId = tree.parentId,
        childIds = tree.childIds
    )

}
