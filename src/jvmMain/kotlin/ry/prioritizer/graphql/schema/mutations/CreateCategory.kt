package ry.prioritizer.graphql.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import kotlinx.coroutines.runBlocking
import ry.prioritizer.PrioritizerApi
import ry.prioritizer.model.Category
import ry.prioritizer.graphql.schema.model.GraphQLCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateCategory @Inject constructor(
    private val prioritizerApi: PrioritizerApi
) : Mutation {

    fun createCategory(
        parentId: String,
        name: String
    ): GraphQLCategory? =
        runBlocking {
            prioritizerApi
                .createCategory(
                    parentId,
                    name = name
                )
                ?.let { category: Category ->
                    GraphQLCategory.fromCategory(category)
                }
        }

}
