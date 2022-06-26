package model

data class DeepTree(
    override val id: String,
    override val node: Node,
    val children: Set<Tree>
) : Tree {

    override val childIds: Set<String> =
        children.map(Tree::id).toSet()

}
