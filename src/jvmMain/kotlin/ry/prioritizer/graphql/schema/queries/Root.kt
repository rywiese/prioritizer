package ry.prioritizer.graphql.schema.queries

import com.expediagroup.graphql.server.operations.Query
import kotlinx.coroutines.runBlocking
import ry.prioritizer.PrioritizerApi
import ry.prioritizer.graphql.schema.model.GraphQLTree
import ry.prioritizer.model.Tree
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Root @Inject constructor(
    private val prioritizerApi: PrioritizerApi
) : Query {

    fun root(): GraphQLTree? =
        runBlocking {
            prioritizerApi.getRoot()
                ?.let { tree: Tree ->
                    GraphQLTree.fromTree(tree)
                }
        }

}
