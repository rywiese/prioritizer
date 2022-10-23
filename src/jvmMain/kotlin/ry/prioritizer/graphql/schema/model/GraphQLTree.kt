package ry.prioritizer.graphql.schema.model

import com.expediagroup.graphql.generator.annotations.GraphQLName
import ry.prioritizer.model.Item
import ry.prioritizer.model.Tree

@GraphQLName("Tree")
data class GraphQLTree(
    override val category: GraphQLCategory,
    override val queue: List<GraphQLItem>,
    override val children: List<GraphQLTree>,
) : Tree {

    companion object {

        fun fromTree(
            tree: Tree
        ): GraphQLTree =
            tree as? GraphQLTree ?: GraphQLTree(
                category = GraphQLCategory.fromCategory(tree.category),
                queue = tree.queue.map { item: Item ->
                    GraphQLItem.fromItem(item)
                },
                children = tree.children.map { child: Tree ->
                    fromTree(child)
                }
            )

    }

}
