package ry.prioritizer.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import ry.prioritizer.schema.model.Tree

object AddChildToTree : Mutation {

    fun addChildToTree(
        treeId: String,
        name: String
    ): Tree? =
        TODO()

}
