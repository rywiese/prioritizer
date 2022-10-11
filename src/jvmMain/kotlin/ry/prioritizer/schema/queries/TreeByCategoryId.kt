package ry.prioritizer.schema.queries

import com.expediagroup.graphql.server.operations.Query
import dagger.multibindings.IntoSet
import org.neo4j.driver.Driver
import org.neo4j.driver.Transaction
import ry.prioritizer.neo4j.Neo4JQueries.getTree
import ry.prioritizer.schema.model.Tree
import javax.inject.Inject

class TreeByCategoryId @Inject constructor(
    private val neo4JDriver: Driver
) : Query {

    fun tree(categoryId: String): Tree? =
        neo4JDriver.session().readTransaction { transaction: Transaction ->
            transaction.getTree(categoryId)
        }

}
