package ry.prioritizer.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import ry.prioritizer.PrioritizerApi
import ry.prioritizer.schema.model.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateCategory @Inject constructor(
    private val prioritizerApi: PrioritizerApi
) : Mutation {

    fun createCategory(
        parentId: String,
        name: String
    ): Category? =
        prioritizerApi.createCategory(
            parentId,
            name = name
        )

}
