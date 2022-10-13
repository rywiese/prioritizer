package ry.prioritizer.schema.queries

import com.expediagroup.graphql.server.operations.Query
import org.neo4j.driver.Driver
import org.neo4j.driver.Transaction
import ry.prioritizer.neo4j.Neo4JQueries.getTree
import ry.prioritizer.schema.model.Tree
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTree @Inject constructor(
    private val neo4jDriver: Driver
) : Query {

    fun tree(categoryId: String): Tree? =
        neo4jDriver.session().readTransaction { transaction: Transaction ->
            transaction.getTree(categoryId)
        }

}