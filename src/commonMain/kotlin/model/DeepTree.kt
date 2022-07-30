package model

data class DeepTree(
    override val id: String,
    override val name: String,
    override val queue: List<Item>,
    override val parentId: String?,
    val children: Set<Tree>
) : Tree {

    override val childIds: Set<String> =
        children.map(Tree::id).toSet()

    /**
     * @return the subtree of `this` tree whose [Tree::id] is [childId],
     * or `null` if none is found.
     */
    override fun subTree(
        childId: String
    ): Tree? =
        takeIf { this.id == childId }
            ?: children.firstNotNullOfOrNull { child: Tree ->
                child.subTree(childId)
            }

    override fun limitDepth(
        depth: Int
    ): Tree =
        when {
            children.isEmpty() -> this
            depth <= 0 -> ShallowTree(this)
            else -> copy(
                children = children
                    .map { child: Tree ->
                        child.limitDepth(depth - 1)
                    }
                    .toSet()
            )
        }

    override fun pop(): Pair<DeepTree, Item?> {
        val item: Item? = queue.firstOrNull()
        val tree: DeepTree = if (item == null) this else copy(
            queue = queue.drop(1)
        )
        return tree to item
    }

    override fun promote(
        childId: String
    ): Tree {
        val oldChild: Tree? = children.find { it.id == childId }
        return oldChild?.pop()
            ?.let { (newChild: Tree, item: Item?) ->
                if (item == null)
                    this
                else copy(
                    queue = queue + item,
                    children = children - oldChild + newChild
                )
            }
            ?: this
    }

}
