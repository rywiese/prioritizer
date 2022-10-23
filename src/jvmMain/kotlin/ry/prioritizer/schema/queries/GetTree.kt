package ry.prioritizer.schema.queries

import com.expediagroup.graphql.server.operations.Query
import ry.prioritizer.PrioritizerApi
import ry.prioritizer.schema.model.Tree
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTree @Inject constructor(
    private val prioritizerApi: PrioritizerApi
) : Query {

    fun tree(categoryId: String): Tree? = prioritizerApi.getTree(categoryId)

}
