package ry.prioritizer.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import org.neo4j.driver.Driver
import org.neo4j.driver.Transaction
import ry.prioritizer.neo4j.Neo4JQueries.createCategory
import ry.prioritizer.schema.model.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateCategory @Inject constructor(
    private val neo4jDriver: Driver
) : Mutation {

    fun createCategory(
        parentId: String,
        name: String
    ): Category? =
        neo4jDriver.session().writeTransaction { transaction: Transaction ->
            transaction.createCategory(parentId, name)
        }

}
