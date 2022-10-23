package ry.prioritizer.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import kotlinx.coroutines.runBlocking
import ry.prioritizer.PrioritizerApi
import ry.prioritizer.schema.model.Item
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
    ): Item? =
        runBlocking {
            prioritizerApi.createItem(
                categoryId = categoryId,
                name = name,
                price = price,
                link = link
            )
        }

}
