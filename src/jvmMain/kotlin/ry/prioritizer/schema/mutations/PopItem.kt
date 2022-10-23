package ry.prioritizer.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import ry.prioritizer.PrioritizerApi
import ry.prioritizer.schema.model.Item
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopItem @Inject constructor(
    private val prioritizerApi: PrioritizerApi
) : Mutation {

    fun popItem(categoryId: String): Item? = prioritizerApi.popItem(categoryId)

}
