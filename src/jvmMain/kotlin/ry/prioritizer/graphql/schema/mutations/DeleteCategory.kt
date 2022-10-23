package ry.prioritizer.graphql.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import kotlinx.coroutines.runBlocking
import ry.prioritizer.PrioritizerApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteCategory @Inject constructor(
    private val prioritizerApi: PrioritizerApi
) : Mutation {

    fun deleteCategory(
        categoryId: String
    ): String? =
        runBlocking {
            prioritizerApi.deleteCategory(categoryId)
        }

}
