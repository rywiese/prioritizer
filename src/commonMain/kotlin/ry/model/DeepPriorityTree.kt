package ry.model

data class DeepPriorityTree(
    override val id: String,
    override val node: Node,
    val children: Set<PriorityTree>
) : PriorityTree {

    override val childIds: Set<String> =
        children.map(PriorityTree::id).toSet()

}
