package ry.prioritizer.model

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore

@GraphQLIgnore
interface Tree {

    val category: Category

    val queue: List<Item>

    // TODO: Make this a Set.
    val children: List<Tree>

}
