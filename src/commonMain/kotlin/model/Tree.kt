package model

import protocol.Identifiable

data class Tree(
    val category: Category,
    val queue: List<Item>,
    val parentId: String?,
    val children: Set<Tree>,
) : Identifiable by category {

    fun subTree(
        childId: String
    ): Tree? =
        takeIf { this.id == childId }
            ?: children.firstNotNullOfOrNull { child: Tree ->
                child.subTree(childId)
            }

    fun limitDepth(
        depth: Int
    ): Tree =
        when {
            children.isEmpty() -> this
            depth <= 0 -> copy(children = emptySet())
            else -> copy(
                children = children
                    .map { child: Tree ->
                        child.limitDepth(depth - 1)
                    }
                    .toSet()
            )
        }

}
