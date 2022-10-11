package ry.prioritizer.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import ry.prioritizer.schema.model.Item
import java.util.UUID

object AddItemToQueue : Mutation {

    fun addItemToQueue(
        treeId: String,
        name: String,
        price: Double,
        link: String
    ): Item? =
        Item(
            id = UUID.randomUUID().toString(),
            name = name,
            price = price,
            link = link
        ).takeIf { item: Item ->
            addItemToQueue(treeId, item)
        }

    private fun addItemToQueue(
        treeId: String,
        item: Item
    ): Boolean =
        TODO()

}
