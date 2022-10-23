package ry.prioritizer.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import ry.prioritizer.PrioritizerApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteCategory @Inject constructor(
    private val prioritizerApi: PrioritizerApi
) : Mutation {

    fun deleteCategory(categoryId: String) = prioritizerApi.deleteCategory(categoryId)

}
