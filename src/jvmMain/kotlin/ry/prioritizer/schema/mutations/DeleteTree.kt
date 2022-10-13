package ry.prioritizer.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import org.neo4j.driver.Driver
import org.neo4j.driver.Transaction
import ry.prioritizer.neo4j.Neo4JQueries.deleteTree
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteTree @Inject constructor(
    private val neo4jDriver: Driver
) : Mutation {

    fun deleteTree(categoryId: String) =
        neo4jDriver.session().writeTransaction { transaction: Transaction ->
            transaction.deleteTree(categoryId).let { categoryId }
        }

}
