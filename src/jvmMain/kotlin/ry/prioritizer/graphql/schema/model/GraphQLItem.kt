package ry.prioritizer.graphql.schema.model

import com.expediagroup.graphql.generator.annotations.GraphQLName
import ry.prioritizer.model.Item

@GraphQLName("Item")
data class GraphQLItem(
    override val id: String,
    override val name: String,
    override val price: Double,
    override val link: String,
) : Item {

    companion object {

        fun fromItem(
            item: Item
        ): GraphQLItem =
            item as? GraphQLItem ?: GraphQLItem(
                id = item.id,
                name = item.name,
                price = item.price,
                link = item.link
            )

    }

}
