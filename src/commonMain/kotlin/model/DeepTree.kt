package model

data class DeepTree(
    override val id: String,
    override val name: String,
    override val queue: List<Item>,
    override val parentId: String?,
    val children: Set<DeepTree>
) : Tree {

    override val childIds: Set<String> =
        children.map(Tree::id).toSet()

    fun subTree(id: String): DeepTree? =
        takeIf { this.id == id }
            ?: children.firstNotNullOfOrNull {
                it.subTree(id)
            }

}
