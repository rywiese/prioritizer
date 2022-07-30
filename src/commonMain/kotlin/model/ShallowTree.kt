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

}
