package ry.prioritizer.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import org.neo4j.driver.Driver
import org.neo4j.driver.Transaction
import ry.prioritizer.neo4j.Neo4JQueries.createItem
import ry.prioritizer.schema.model.Item
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateItem @Inject constructor(
    private val neo4jDriver: Driver
) : Mutation {

    fun createItem(
        categoryId: String,
        name: String,
        price: Double,
        link: String
    ): Item? =
        neo4jDriver.session().writeTransaction { transaction: Transaction ->
            transaction.createItem(
                categoryId = categoryId,
                name = name,
                price = price,
                link = link
            )
        }

}
