package model

data class Depth1Tree(
    override val id: String,
    override val name: String,
    override val queue: List<Item>,
    override val parentId: String?,
    val children: Set<ShallowTree>
) : Tree by DeepTree(
    id = id,
    name = name,
    queue = queue,
    parentId = parentId,
    children = children
) {

    constructor(
        tree: DeepTree
    ) : this(
        id = tree.id,
        name = tree.name,
        queue = tree.queue,
        parentId = tree.parentId,
        children = tree.children.map(::ShallowTree).toSet()
    )

}
