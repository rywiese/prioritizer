package ry.prioritizer.schema.queries

import com.expediagroup.graphql.server.operations.Query
import org.neo4j.driver.Driver
import org.neo4j.driver.Transaction
import ry.prioritizer.neo4j.Neo4JQueries.getRoot
import ry.prioritizer.schema.model.Tree
import javax.inject.Inject

class Root @Inject constructor(
    private val neo4jDriver: Driver
) : Query {

    fun root(): Tree? =
        neo4jDriver.session().readTransaction { transaction: Transaction ->
            transaction.getRoot()
        }

}
