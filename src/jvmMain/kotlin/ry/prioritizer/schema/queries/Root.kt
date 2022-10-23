package ry.prioritizer.schema.queries

import com.expediagroup.graphql.server.operations.Query
import kotlinx.coroutines.runBlocking
import ry.prioritizer.PrioritizerApi
import ry.prioritizer.schema.model.Tree
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Root @Inject constructor(
    private val prioritizerApi: PrioritizerApi
) : Query {

    fun root(): Tree? =
        runBlocking {
            prioritizerApi.getRoot()
        }

}
