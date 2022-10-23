package ry.prioritizer.graphql.schema.queries

import com.expediagroup.graphql.server.operations.Query
import kotlinx.coroutines.runBlocking
import ry.prioritizer.PrioritizerApi
import ry.prioritizer.model.Tree
import ry.prioritizer.graphql.schema.model.GraphQLTree
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTree @Inject constructor(
    private val prioritizerApi: PrioritizerApi
) : Query {

    fun tree(
        categoryId: String
    ): GraphQLTree? =
        runBlocking {
            prioritizerApi.getTree(categoryId)
                ?.let { tree: Tree ->
                    GraphQLTree.fromTree(tree)
                }
        }

}
