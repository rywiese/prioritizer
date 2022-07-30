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

    fun toShallowTree(): ShallowTree =
        ShallowTree(
            id = id,
            name = name,
            queue = queue,
            parentId = parentId,
            childIds = childIds
        )

    /**
     * @return the subtree of `this` tree whose [Tree::id] is [childId],
     * or `null` if none is found.
     */
    override fun subTree(childId: String): Tree? =
        takeIf { this.id == childId }
            ?: children.firstNotNullOfOrNull { child: Tree ->
                child.subTree(childId)
            }

    override fun limitDepth(depth: Int): Tree =
        when {
            children.isEmpty() -> this
            depth <= 0 -> toShallowTree()
            else -> copy(
                children = children
                    .map { child: Tree ->
                        child.limitDepth(depth - 1)
                    }
                    .toSet()
            )
        }

}
