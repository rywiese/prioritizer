package ry.prioritizer.graphql.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import kotlinx.coroutines.runBlocking
import ry.prioritizer.PrioritizerApi
import ry.prioritizer.model.Item
import ry.prioritizer.graphql.schema.model.GraphQLItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopItem @Inject constructor(
    private val prioritizerApi: PrioritizerApi
) : Mutation {

    fun popItem(
        categoryId: String
    ): GraphQLItem? =
        runBlocking {
            prioritizerApi.popItem(categoryId)
                ?.let { item: Item ->
                    GraphQLItem.fromItem(item)
                }
        }

}
