package ry.prioritizer.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import org.neo4j.driver.Driver
import org.neo4j.driver.Transaction
import ry.prioritizer.neo4j.Neo4JQueries.popItem
import ry.prioritizer.schema.model.Item
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopItem @Inject constructor(
    private val neo4jDriver: Driver
) : Mutation {

    fun popItem(
        categoryId: String
    ): Item? =
        neo4jDriver.session().writeTransaction { transaction: Transaction ->
            transaction.popItem(categoryId)
        }

}
