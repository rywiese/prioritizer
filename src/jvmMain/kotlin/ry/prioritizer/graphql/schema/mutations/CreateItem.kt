package ry.prioritizer.graphql.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import kotlinx.coroutines.runBlocking
import ry.prioritizer.PrioritizerApi
import ry.prioritizer.model.Item
import ry.prioritizer.graphql.schema.model.GraphQLItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateItem @Inject constructor(
    private val prioritizerApi: PrioritizerApi
) : Mutation {

    fun createItem(
        categoryId: String,
        name: String,
        price: Double,
        link: String
    ): GraphQLItem? =
        runBlocking {
            prioritizerApi
                .createItem(
                    categoryId = categoryId,
                    name = name,
                    price = price,
                    link = link
                )
                ?.let { item: Item ->
                    GraphQLItem.fromItem(item)
                }
        }

}
