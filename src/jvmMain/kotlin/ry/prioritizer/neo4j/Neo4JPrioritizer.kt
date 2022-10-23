package ry.prioritizer.neo4j

import org.neo4j.driver.Driver
import org.neo4j.driver.Transaction
import ry.prioritizer.PrioritizerApi
import ry.prioritizer.neo4j.Neo4JQueries.createCategory
import ry.prioritizer.neo4j.Neo4JQueries.createItem
import ry.prioritizer.neo4j.Neo4JQueries.deleteCategory
import ry.prioritizer.neo4j.Neo4JQueries.getRoot
import ry.prioritizer.neo4j.Neo4JQueries.getTree
import ry.prioritizer.neo4j.Neo4JQueries.popItem
import ry.prioritizer.schema.model.Category
import ry.prioritizer.schema.model.Item
import ry.prioritizer.schema.model.Tree
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Neo4JPrioritizer @Inject constructor(
    private val neo4jDriver: Driver
) : PrioritizerApi {

    override suspend fun getRoot(): Tree? =
        neo4jDriver.session().readTransaction { transaction: Transaction ->
            transaction.getRoot()
        }

    override suspend fun getTree(
        categoryId: String
    ): Tree? =
        neo4jDriver.session().readTransaction { transaction: Transaction ->
            transaction.getTree(categoryId)
        }

    override suspend fun createCategory(
        parentId: String,
        name: String
    ): Category? =
        neo4jDriver.session().writeTransaction { transaction: Transaction ->
            transaction.createCategory(parentId, name)
        }

    override suspend fun deleteCategory(
        categoryId: String
    ): String =
        neo4jDriver.session().writeTransaction { transaction: Transaction ->
            // TODO: figure out how to return `null` if not found.
            transaction.deleteCategory(categoryId).let { categoryId }
        }

    override suspend fun createItem(
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

    override suspend fun popItem(
        categoryId: String
    ): Item? =
        neo4jDriver.session().writeTransaction { transaction: Transaction ->
            transaction.popItem(categoryId)
        }

}
