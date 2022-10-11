package ry.prioritizer.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import org.neo4j.driver.Driver
import org.neo4j.driver.Transaction
import ry.prioritizer.neo4j.Neo4JQueries.deleteCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteCategory @Inject constructor(
    private val neo4jDriver: Driver
) : Mutation {

    fun deleteCategory(categoryId: String) =
        neo4jDriver.session().writeTransaction { transaction: Transaction ->
            transaction.deleteCategory(categoryId).let { categoryId }
        }

}
