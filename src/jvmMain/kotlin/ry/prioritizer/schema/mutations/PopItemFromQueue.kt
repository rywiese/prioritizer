package ry.prioritizer.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import ry.prioritizer.schema.model.Item

object PopItemFromQueue : Mutation {

    fun popItemFromQueue(
        treeId: String
    ): Item? =
        TODO()

}
